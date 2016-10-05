package kl.cookassistant.DataModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Li on 9/20/2016.
 */

public class DBhelper extends SQLiteOpenHelper{
    public final class Dishes{
        public static final String TABLE_NAME="Dishes";
        public static final String COLUMN_ID="ID";
        public static final String COLUMN_NAME="NAME";
        public static final String COLUMN_DESCRIPTION="DESCRIPTION";
        public static final String COLUMN_INGREDIENTS="INGREDIENTS";
        public static final String COLUMN_TIME_CREATED="TIME_CREATED";
        public static final String COLUMN_IS_KNOWN="IS_KNOWN";
    }
    public final class Users {
        public static final String TABLE_NAME="Users";
        public static final String COLUMN_ID="ID";
        public static final String COLUMN_PASSWORD="PASSWORD";
        public static final String COLUMN_NAME="NAME";
    }
    public final class Tags{
        public static final String TABLE_NAME="Tags";
        public static final String COLUMN_ID="ID";
        public static final String COLUMN_NAME="NAME";
    }
    public final class DishUserMap{
        public static final String TABLE_NAME="DISHUSERMAP";
        public static final String COLUMN_DISHID="DISHID";
        public static final String COLUMN_USERID="USRID";
    }
    public final class DishTagMap{
        public static final String TABLE_NAME="DISHTAGMAP";
        public static final String COLUMN_DISHID="DISHID";
        public static final String COLUMN_TAGID="TAGID";
    }
    public static final String DATABASE_NAME = "CookAssistant";
    public static final String TurnOnFK = "PRAGMA foreign_keys = ON";

    public DBhelper(Context context){
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createDishesStr = "CREATE TABLE IF NOT EXISTS " +
                Dishes.TABLE_NAME +
                " (" +
                Dishes.COLUMN_ID + " INTEGER PRIMARY KEY," +
                Dishes.COLUMN_NAME + " TEXT," +
                Dishes.COLUMN_DESCRIPTION + " TEXT," +
                Dishes.COLUMN_TIME_CREATED + " DATE," +
                Dishes.COLUMN_IS_KNOWN + " BOOLEAN)";
        String createUserStr = "CREATE TABLE IF NOT EXISTS " +
                Users.TABLE_NAME +
                " (" +
                Users.COLUMN_ID + " INTEGER PRIMARY KEY," +
                Users.COLUMN_PASSWORD + " TEXT NOT NULL," +
                Users.COLUMN_NAME + " TEXT NOT NULL," +
                "CONSTRAINT username_unique UNIQUE (" + Users.COLUMN_NAME + "))";
        String createTagStr = "CREATE TABLE IF NOT EXISTS " +
                Tags.TABLE_NAME +
                " (" +
                Tags.COLUMN_ID + " INTEGER PRIMARY KEY," +
                Tags.COLUMN_NAME + " TEXT," +
                "CONSTRAINT tagname_unique UNIQUE (" + Tags.COLUMN_NAME + "))";
        String createDishUserMapStr = "CREATE TABLE IF NOT EXISTS " +
                DishUserMap.TABLE_NAME +
                " (" +
                DishUserMap.COLUMN_DISHID + " INTEGER," +
                DishUserMap.COLUMN_USERID + " INTEGER," +
                "FOREIGN KEY(" + DishUserMap.COLUMN_DISHID + ") REFERENCES " + Dishes.TABLE_NAME + "( " + Dishes.COLUMN_ID + ") ON DELETE CASCADE," +
                "FOREIGN KEY(" + DishUserMap.COLUMN_USERID + ") REFERENCES " + Users.TABLE_NAME + "( " + Users.COLUMN_ID + ") ON DELETE CASCADE)";
        String createDishTagMapStr = "CREATE TABLE IF NOT EXISTS " +
                DishTagMap.TABLE_NAME +
                " (" +
                DishTagMap.COLUMN_DISHID + " INTEGER," +
                DishTagMap.COLUMN_TAGID + " INTEGER," +
                "FOREIGN KEY(" + DishTagMap.COLUMN_DISHID + ") REFERENCES " + Dishes.TABLE_NAME + "( " + Dishes.COLUMN_ID+ ") ON DELETE CASCADE," +
                "FOREIGN KEY(" + DishTagMap.COLUMN_TAGID + ") REFERENCES " + Tags.TABLE_NAME + "( " + Tags.COLUMN_ID + ") ON DELETE CASCADE)";
        db.execSQL(createDishesStr);
        db.execSQL(createUserStr);
        db.execSQL(createTagStr);
        db.execSQL(createDishUserMapStr);
        db.execSQL(createDishTagMapStr);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + Dishes.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Users.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Tags.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DishUserMap.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DishTagMap.TABLE_NAME);
        onCreate(db);
    }
    //users
    public Long insertNewUser(String userName, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inputs = new ContentValues();
        inputs.put(Users.COLUMN_NAME, userName);
        inputs.put(Users.COLUMN_PASSWORD, password);
        return db.insert(Users.TABLE_NAME, null, inputs);
    }//checked

    public boolean deleteUser(String userName, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(TurnOnFK);
        return db.delete(Users.TABLE_NAME, Users.COLUMN_NAME + "=? and " + Users.COLUMN_PASSWORD + "=?", new String[]{userName, password}) > 0;
    }//checked

    public Long getUserId(String userName, String password){
        Long Id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + Users.TABLE_NAME + " where " + Users.COLUMN_NAME + "=? and " + Users.COLUMN_PASSWORD + "=?", new String[]{userName, password});
        if(res ==null){
            return new Long(-1);
        }
        res.moveToFirst();
        Id = res.getLong(res.getColumnIndex(Users.COLUMN_ID));
        res.moveToNext();
        boolean isAfterLast = res.isAfterLast();
        res.close();
        return isAfterLast? Id: -2;
    }

    //tags
    public long insertNewTag(String tagName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inputs = new ContentValues();
        inputs.put(Tags.COLUMN_NAME, tagName);
        return db.insert(Tags.TABLE_NAME, null, inputs);
    }//checked

    public boolean deleteTag(String tagName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(TurnOnFK);
        return db.delete(Tags.TABLE_NAME, Tags.COLUMN_NAME + "=?",new String[]{tagName})>0;
    }//checked

    private boolean MapDishAndTags(Long DishId, List<String> tags){
        boolean result = true;
        SQLiteDatabase db = this.getWritableDatabase();
        for(int i = 0; i<tags.size(); i++){
            Cursor mCur = db.rawQuery("SELECT " + Tags.COLUMN_ID + " FROM " + Tags.TABLE_NAME + " where " + Tags.COLUMN_NAME + "=?", new String[]{tags.get(i)});
            long TagId;
            if(mCur == null){//record does not exist
                TagId = insertNewTag(tags.get(i));

            }
            else{
                mCur.moveToFirst();
                TagId = mCur.getLong(mCur.getColumnIndex(Tags.COLUMN_ID));

            }
            mCur.close();
            result = result && (insertNewDishTagMap(DishId, TagId) > 0);
        }
        return result;
    }
    //dishes
    public Long insertNewDish(String Name, List<String> Ingredients, String Description, Date TimeCreated, boolean IsKnown, User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inputs = new ContentValues();
        //parse Ingredients
        String IngredientsSTR = "";
        for(int i = 0; i<Ingredients.size(); i++){
            IngredientsSTR += Ingredients.get(i);
        }

        //parse date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(TimeCreated);

        inputs.put(Dishes.COLUMN_NAME, Name);
        inputs.put(Dishes.COLUMN_DESCRIPTION, Description);
        inputs.put(Dishes.COLUMN_INGREDIENTS, IngredientsSTR);
        inputs.put(Dishes.COLUMN_TIME_CREATED, date);
        inputs.put(Dishes.COLUMN_IS_KNOWN, IsKnown? 1 : 0);
        Long DishId = db.insert(Dishes.TABLE_NAME, null, inputs);
        boolean success = MapDishAndTags(DishId,Ingredients) && (insertNewDishUserMap(DishId, user.getID()) > 0);
        return success?DishId:-2;
    }//checked

    public boolean deleteDish(Long ID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(TurnOnFK);
        return db.delete(Dishes.TABLE_NAME, Dishes.COLUMN_ID + "=?", new String[]{Long.toString(ID)}) > 0;
    }//checked

    //dishUserMap
    public Long insertNewDishUserMap(Long DishId, Long UserId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inputs = new ContentValues();
        inputs.put(DishUserMap.COLUMN_DISHID, DishId);
        inputs.put(DishUserMap.COLUMN_USERID, UserId);
        return db.insert(DishUserMap.TABLE_NAME, null, inputs);
    }//checked

    public boolean removeDUMap(Long DishId, Long UserId){
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = DishUserMap.COLUMN_DISHID + "=? and " + DishUserMap.COLUMN_USERID + "=?";
        return db.delete(DishUserMap.TABLE_NAME, deleteQuery, new String[]{Long.toString(DishId), Long.toString(UserId)})>0;
    }
    //dishTagMap
    public Long insertNewDishTagMap(Long DishId, Long TagId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inputs = new ContentValues();
        inputs.put(DishTagMap.COLUMN_DISHID, DishId);
        inputs.put(DishTagMap.COLUMN_TAGID, TagId);
        return db.insert(DishTagMap.TABLE_NAME, null, inputs);
    }//checked

    public List<User> getAllUsers() {
        List<User> data = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Users.TABLE_NAME, null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            Long id = res.getLong(res.getColumnIndex(Users.COLUMN_ID));
            String password = res.getString(res.getColumnIndex(Users.COLUMN_PASSWORD));
            String name = res.getString(res.getColumnIndex(Users.COLUMN_NAME));
            User user = new User(id, password, name);
            data.add(user);
            res.moveToNext();
        }
        res.close();
        return data;
    }//checked

    public List<Tag> getAllTags() {
        List<Tag> data = new ArrayList<Tag>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Tags.TABLE_NAME, null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            Long id = res.getLong(res.getColumnIndex(Tags.COLUMN_ID));
            String name = res.getString(res.getColumnIndex(Tags.COLUMN_NAME));
            Tag tag = new Tag(id, name);
            data.add(tag);
            res.moveToNext();
        }
        res.close();
        return data;
    }//checked

    public List<Dish> getAllDishes() {
        List<Dish> data = new ArrayList<Dish>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Dishes.TABLE_NAME, null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            Long id = res.getLong(res.getColumnIndex(Dishes.COLUMN_ID));
            String des = res.getString(res.getColumnIndex(Dishes.COLUMN_DESCRIPTION));
            String ingredients = res.getString(res.getColumnIndex(Dishes.COLUMN_INGREDIENTS));
            String name = res.getString(res.getColumnIndex(Dishes.COLUMN_NAME));
            String date = res.getString(res.getColumnIndex(Dishes.COLUMN_TIME_CREATED));
            Boolean isKnown = res.getInt(res.getColumnIndex(Dishes.COLUMN_IS_KNOWN)) > 0;
            Dish dish = new Dish(id, name, ingredients, des, date, isKnown);
            data.add(dish);
            res.moveToNext();
        }
        res.close();
        return data;
    }//checked

    public List<DUMap> getAllDUMap(){
        List<DUMap> data = new ArrayList<DUMap>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DishUserMap.TABLE_NAME, null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            Long DishId = res.getLong(res.getColumnIndex(DishUserMap.COLUMN_DISHID));
            Long UserId = res.getLong(res.getColumnIndex(DishUserMap.COLUMN_USERID));
            DUMap duMap = new DUMap(DishId, UserId);
            data.add(duMap);
            res.moveToNext();
        }
        res.close();
        return data;
    }//checked

    public List<DTMap> getAllDTMap(){
        List<DTMap> data = new ArrayList<DTMap>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DishTagMap.TABLE_NAME, null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            Long DishId = res.getLong(res.getColumnIndex(DishTagMap.COLUMN_DISHID));
            Long TagId = res.getLong(res.getColumnIndex(DishTagMap.COLUMN_TAGID));
            DTMap dtMap = new DTMap(DishId, TagId);
            data.add(dtMap);
            res.moveToNext();
        }
        res.close();
        return data;
    }//checked

    public List<Dish> getAllDishes(User user) {
        List<Dish> data = new ArrayList<Dish>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT D.* FROM " + Dishes.TABLE_NAME + " D, " +
                DishUserMap.TABLE_NAME + " DU " +
                "WHERE DU." + DishUserMap.COLUMN_USERID + "=? " +
                "and DU." + DishUserMap.COLUMN_DISHID + "=D." + Dishes.COLUMN_ID
                + " GROUP BY D." + Dishes.COLUMN_ID;
        Cursor res = db.rawQuery(query, new String[]{Long.toString(user.getID())});
        res.moveToFirst();
        while(!res.isAfterLast()){
            Long id = res.getLong(res.getColumnIndex(Dishes.COLUMN_ID));
            String des = res.getString(res.getColumnIndex(Dishes.COLUMN_DESCRIPTION));
            String ingredients = res.getString(res.getColumnIndex(Dishes.COLUMN_INGREDIENTS));
            String name = res.getString(res.getColumnIndex(Dishes.COLUMN_NAME));
            String date = res.getString(res.getColumnIndex(Dishes.COLUMN_TIME_CREATED));
            Boolean isKnown = res.getInt(res.getColumnIndex(Dishes.COLUMN_IS_KNOWN)) > 0;
            Dish dish = new Dish(id, name, ingredients, des, date, isKnown);
            data.add(dish);
            res.moveToNext();
        }
        res.close();
        return data;
    }

    public List<Dish> getAllDishes(Tag tag) {
        List<Dish> data = new ArrayList<Dish>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT D.* FROM " + Dishes.TABLE_NAME + " D, " +
                DishTagMap.TABLE_NAME + " DT " +
                "WHERE DT." + DishTagMap.COLUMN_TAGID + "=? " +
                "and DT." + DishTagMap.COLUMN_DISHID + "=D." + Dishes.COLUMN_ID
                + " GROUP BY D." + Dishes.COLUMN_ID;
        Cursor res = db.rawQuery(query, new String[]{Long.toString(tag.getID())});
        res.moveToFirst();
        while(!res.isAfterLast()){
            Long id = res.getLong(res.getColumnIndex(Dishes.COLUMN_ID));
            String des = res.getString(res.getColumnIndex(Dishes.COLUMN_DESCRIPTION));
            String ingredients = res.getString(res.getColumnIndex(Dishes.COLUMN_INGREDIENTS));
            String name = res.getString(res.getColumnIndex(Dishes.COLUMN_NAME));
            String date = res.getString(res.getColumnIndex(Dishes.COLUMN_TIME_CREATED));
            Boolean isKnown = res.getInt(res.getColumnIndex(Dishes.COLUMN_IS_KNOWN)) > 0;
            Dish dish = new Dish(id, name, ingredients, des, date, isKnown);
            data.add(dish);
            res.moveToNext();
        }
        res.close();
        return data;
    }

    public List<Dish> getAllDishes(List<Tag> tags) {
        List<Dish> data = new ArrayList<Dish>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] inputSTRs = new String[tags.size()];

        String query = "SELECT D.* FROM " + Dishes.TABLE_NAME + " D, " +
                DishTagMap.TABLE_NAME + " DT " +
                "WHERE DT." + DishTagMap.COLUMN_DISHID + "=D." + Dishes.COLUMN_ID + " AND (";
        for(int i = 0; i<tags.size(); i++){
            inputSTRs[i] = Long.toString(tags.get(i).getID());
            query += i==0?"DT." + DishTagMap.COLUMN_TAGID + "=? ":"OR DT." + DishTagMap.COLUMN_TAGID + "=? ";
        }
        query += ") GROUP BY D." + Dishes.COLUMN_ID;
        Cursor res = db.rawQuery(query, inputSTRs);
        res.moveToFirst();
        while(!res.isAfterLast()){
            Long id = res.getLong(res.getColumnIndex(Dishes.COLUMN_ID));
            String des = res.getString(res.getColumnIndex(Dishes.COLUMN_DESCRIPTION));
            String ingredients = res.getString(res.getColumnIndex(Dishes.COLUMN_INGREDIENTS));
            String name = res.getString(res.getColumnIndex(Dishes.COLUMN_NAME));
            String date = res.getString(res.getColumnIndex(Dishes.COLUMN_TIME_CREATED));
            Boolean isKnown = res.getInt(res.getColumnIndex(Dishes.COLUMN_IS_KNOWN)) > 0;
            Dish dish = new Dish(id, name, ingredients, des, date, isKnown);
            data.add(dish);
            res.moveToNext();
        }
        res.close();
        return data;
    }


}
