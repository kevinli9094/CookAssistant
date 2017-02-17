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

public class DBhelper extends SQLiteOpenHelper {
    public final class Dishes {
        public static final String TABLE_NAME = "Dishes";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
        public static final String COLUMN_TIME_CREATED = "TIME_CREATED";
        public static final String COLUMN_IS_KNOWN = "IS_KNOWN";
    }

    public final class Users {
        public static final String TABLE_NAME = "Users";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_PASSWORD = "PASSWORD";
        public static final String COLUMN_NAME = "NAME";
    }

    public final class Tags {
        public static final String TABLE_NAME = "Tags";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_NAME = "NAME";
    }

    public final class Types {
        public static final String TABLE_NAME = "TYPES";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_NAME = "NAME";
    }

    public final class DishUserMap {
        public static final String TABLE_NAME = "DISHUSERMAP";
        public static final String COLUMN_DISHID = "DISHID";
        //todo it is not "USRID". it is "USERID"
        public static final String COLUMN_USERID = "USRID";
    }

    public final class DishTagMap {
        public static final String TABLE_NAME = "DISHTAGMAP";
        public static final String COLUMN_DISHID = "DISHID";
        public static final String COLUMN_TAGID = "TAGID";
    }

    public final class TagTypeMap {
        public static final String TABLE_NAME = "TAGTYPEMAP";
        public static final String COLUMN_TYPEID = "TYPEID";
        public static final String COLUMN_TAGID = "TAGID";
    }

    public final class TagUserMap {
        public static final String TABLE_NAME = "TAGUSERMAP";
        public static final String COLUMN_USERID = "USERID";
        public static final String COLUMN_TAGID = "TAGID";
    }

    public final class TypeUserMap {
        public static final String TABLE_NAME = "TYPEUSERMAP";
        public static final String COLUMN_USERID = "USERID";
        public static final String COLUMN_TYPEID = "TYPEID";
    }
    public final class ShoppingList{
        public static final String TABLE_NAME = "SHOPPINGLIST";
        public static final String COLUMN_ITEM = "ITEM";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_USERID = "USERID";
        public static final String Column_ISCHECKED = "ISCHECKED";
    }

    public static final String DATABASE_NAME = "CookAssistant";
    public static final String TurnOnFK = "PRAGMA foreign_keys = ON";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
        String createTypeStr = "CREATE TABLE IF NOT EXISTS " +
                Types.TABLE_NAME +
                " (" +
                Types.COLUMN_ID + " INTEGER PRIMARY KEY," +
                Types.COLUMN_NAME + " TEXT," +
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
                "FOREIGN KEY(" + DishTagMap.COLUMN_DISHID + ") REFERENCES " + Dishes.TABLE_NAME + "( " + Dishes.COLUMN_ID + ") ON DELETE CASCADE," +
                "FOREIGN KEY(" + DishTagMap.COLUMN_TAGID + ") REFERENCES " + Tags.TABLE_NAME + "( " + Tags.COLUMN_ID + ") ON DELETE CASCADE)";
        String createTagTypeMapStr = "CREATE TABLE IF NOT EXISTS " +
                TagTypeMap.TABLE_NAME +
                " (" +
                TagTypeMap.COLUMN_TYPEID + " INTEGER," +
                TagTypeMap.COLUMN_TAGID + " INTEGER," +
                "FOREIGN KEY(" + TagTypeMap.COLUMN_TYPEID + ") REFERENCES " + Types.TABLE_NAME + "( " + Types.COLUMN_ID + ") ON DELETE CASCADE," +
                "FOREIGN KEY(" + TagTypeMap.COLUMN_TAGID + ") REFERENCES " + Tags.TABLE_NAME + "( " + Tags.COLUMN_ID + ") ON DELETE CASCADE)";
        String createTagUserMapStr = "CREATE TABLE IF NOT EXISTS " +
                TagUserMap.TABLE_NAME +
                " (" +
                TagUserMap.COLUMN_USERID + " INTEGER," +
                TagUserMap.COLUMN_TAGID + " INTEGER," +
                "FOREIGN KEY(" + TagUserMap.COLUMN_USERID + ") REFERENCES " + Users.TABLE_NAME + "( " + Users.COLUMN_ID + ") ON DELETE CASCADE," +
                "FOREIGN KEY(" + TagUserMap.COLUMN_TAGID + ") REFERENCES " + Tags.TABLE_NAME + "( " + Tags.COLUMN_ID + ") ON DELETE CASCADE)";
        String createTypeUserMapStr = "CREATE TABLE IF NOT EXISTS " +
                TypeUserMap.TABLE_NAME +
                " (" +
                TypeUserMap.COLUMN_TYPEID + " INTEGER," +
                TypeUserMap.COLUMN_USERID + " INTEGER," +
                "FOREIGN KEY(" + TypeUserMap.COLUMN_TYPEID + ") REFERENCES " + Types.TABLE_NAME + "( " + Types.COLUMN_ID + ") ON DELETE CASCADE," +
                "FOREIGN KEY(" + TypeUserMap.COLUMN_USERID + ") REFERENCES " + Users.TABLE_NAME + "( " + Users.COLUMN_ID + ") ON DELETE CASCADE)";

        String createShoppingListStr = "CREATE TABLE IF NOT EXISTS " +
                ShoppingList.TABLE_NAME +
                " (" +
                ShoppingList.COLUMN_ITEM + " TEXT," +
                ShoppingList.COLUMN_ID + " INTEGER PRIMARY KEY," +
                ShoppingList.COLUMN_USERID + " INTEGER," +
                ShoppingList.Column_ISCHECKED + " INTEGER," +
                "FOREIGN KEY(" + ShoppingList.COLUMN_USERID + ") REFERENCES " + Users.TABLE_NAME + "( " + Users.COLUMN_ID + ") ON DELETE CASCADE)";
        db.execSQL(createDishesStr);
        db.execSQL(createUserStr);
        db.execSQL(createTagStr);
        db.execSQL(createTypeStr);
        db.execSQL(createDishUserMapStr);
        db.execSQL(createDishTagMapStr);
        db.execSQL(createTagTypeMapStr);
        db.execSQL(createTagUserMapStr);
        db.execSQL(createTypeUserMapStr);
        db.execSQL(createShoppingListStr);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Dishes.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Users.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Tags.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Types.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DishUserMap.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DishTagMap.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TagTypeMap.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TagUserMap.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TypeUserMap.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ShoppingList.TABLE_NAME);
        onCreate(db);
    }


    //region user
    public Long insertNewUser(String userName, String password) {
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
        if(res.getCount() <1){
            res.close();
            return new Long(-1);
        }
        res.moveToFirst();
        Id = res.getLong(res.getColumnIndex(Users.COLUMN_ID));
        res.moveToNext();
        boolean isAfterLast = res.isAfterLast();
        res.close();
        return isAfterLast? Id: -2;
    }

    public List<User> getAllUsers() {
        List<User> data = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Users.TABLE_NAME, null);
        if(res == null || res.getCount()<1){
            res.close();
            return data;
        }
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
    //endregion

    //region tags
    private boolean deleteTag(String tagName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(TurnOnFK);
        return db.delete(Tags.TABLE_NAME, Tags.COLUMN_NAME + "=?",new String[]{tagName})>0;
    }

    private boolean deleteTag(Tag tag){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(TurnOnFK);
        return db.delete(Tags.TABLE_NAME, Tags.COLUMN_ID + "=?",new String[]{tag.getID().toString()})>0;
    }//checked

    public long insertNewTag(String tagName, User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inputs = new ContentValues();
        inputs.put(Tags.COLUMN_NAME, tagName);
        Long rs = db.insert(Tags.TABLE_NAME, null, inputs);
        if(rs > 0){
            insertNewTagUserMap(rs,user.getID());
        }
        return rs;
    }//checked


    public boolean deleteTag(Tag tag, User user){
        return removeTagUserMap(tag.getID(), user.getID());
    }//checked

    public boolean isTagUsed(Tag tag, User user){
        return getAllDishes(tag, user).size()>0;
    }

    public boolean replaceWithNewTag(Tag oldTag, Tag newTag, User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inputs = new ContentValues();
        inputs.put(DishTagMap.COLUMN_TAGID, newTag.getID());
        String whereClause = DishTagMap.COLUMN_TAGID + "=? AND "
                + DishTagMap.COLUMN_DISHID + " IN (SELECT " + DishUserMap.COLUMN_DISHID + " FROM " + DishUserMap.TABLE_NAME + " WHERE " + DishUserMap.COLUMN_USERID + "=?)";
        return db.update(DishTagMap.TABLE_NAME,inputs, whereClause, new String[]{oldTag.getID().toString(), user.getID().toString()})>0;
    }

    public Boolean updateDishIngredients(Long dishId, List<Tag> tags){
        SQLiteDatabase db = this.getWritableDatabase();
        Boolean rs = db.delete(DishTagMap.TABLE_NAME, DishTagMap.COLUMN_DISHID + "=?", new String[]{dishId.toString()})>0;
        rs = rs && MapDishAndTags(dishId, tags);
        return rs;
    }

    private boolean MapDishAndTags(Long DishId, List<Tag> tags){
        boolean result = true;
        for(int i = 0; i<tags.size(); i++){
            long TagId;
            TagId = tags.get(i).getID();
            result = result && (insertNewDishTagMap(DishId, TagId) > 0);
        }
        return result;
    }

    public List<Tag> getAllTags() {
        List<Tag> data = new ArrayList<Tag>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Tags.TABLE_NAME, null);
        if(res == null || res.getCount()<1){
            res.close();
            return data;
        }
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

    public List<Tag> getAllTags(Type type, User user){
        List<Tag> data = new ArrayList<Tag>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Tags.TABLE_NAME + " WHERE " + Tags.COLUMN_ID
                + " IN (SELECT " + TagUserMap.TABLE_NAME + "." + TagUserMap.COLUMN_TAGID
                + " FROM " + TagUserMap.TABLE_NAME + " INNER JOIN " + TagTypeMap.TABLE_NAME + " ON "
                + TagTypeMap.TABLE_NAME + "." + TagTypeMap.COLUMN_TAGID + " = " + TagUserMap.TABLE_NAME + "." + TagUserMap.COLUMN_TAGID
                + " WHERE " + TagUserMap.COLUMN_USERID + "=? AND " + TagTypeMap.COLUMN_TYPEID + "=?)";
        Cursor res = db.rawQuery(query, new String[]{user.getID().toString(), type.getID().toString()});
        if(res == null || res.getCount()<1){
            res.close();
            return data;
        }
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
    }

    public List<Tag> getTagsWithoutType(User user){
        List<Tag> data = new ArrayList<Tag>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+ Tags.TABLE_NAME + ".* FROM "
                + Tags.TABLE_NAME + " INNER JOIN " + TagUserMap.TABLE_NAME + " ON " + Tags.COLUMN_ID + " = " + TagUserMap.COLUMN_TAGID
                +" WHERE " + TagUserMap.COLUMN_USERID + "=? AND "
                + Tags.COLUMN_ID + " NOT IN (SELECT " + Tags.COLUMN_ID + " From " + Tags.TABLE_NAME + " INNER JOIN " + TagTypeMap.TABLE_NAME + " ON " + Tags.COLUMN_ID + " = " + TagTypeMap.COLUMN_TAGID + ")";
        Cursor res = db.rawQuery(query, new String[]{user.getID().toString()});
        if(res == null || res.getCount()<1){
            res.close();
            return data;
        }
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
    }
    //endregion

    //region dishes
    public Long insertNewDish(String Name, List<Tag> Ingredients, String Description, Date TimeCreated, boolean IsKnown, User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inputs = new ContentValues();


        //parse date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(TimeCreated);

        inputs.put(Dishes.COLUMN_NAME, Name);
        inputs.put(Dishes.COLUMN_DESCRIPTION, Description);
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

    public boolean updateDish(Dish newDish){
        Long dishId = newDish.getID();
        List<Tag> Ingredients = newDish.getIngredients();
        Date TimeCreated = newDish.getTimeCreated();
        String Name = newDish.getName();
        String Description = newDish.getDescription();
        boolean IsKnown = newDish.getIsKnown();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inputs = new ContentValues();

        //parse date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(TimeCreated);

        inputs.put(Dishes.COLUMN_NAME, Name);
        inputs.put(Dishes.COLUMN_DESCRIPTION, Description);
        inputs.put(Dishes.COLUMN_TIME_CREATED, date);
        inputs.put(Dishes.COLUMN_IS_KNOWN, IsKnown? 1 : 0);
        String id = newDish.getID().toString();
        return db.update(Dishes.TABLE_NAME, inputs, Dishes.COLUMN_ID + "=?",new String[]{id})>0 && updateDishIngredients(dishId, Ingredients);
    }

    public Long insertNewDish(Dish newDish, User user){
        return insertNewDish(newDish.getName(),newDish.getIngredients(),newDish.getDescription(),newDish.getTimeCreated(),newDish.getIsKnown(), user);
    }

    public List<Tag> getDishTags(Long id){
        List<Tag> rs = new ArrayList<Tag>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Tags.TABLE_NAME + " WHERE " + Tags.COLUMN_ID + " IN (SELECT " + DishTagMap.COLUMN_TAGID + " FROM " + DishTagMap.TABLE_NAME + " WHERE " + DishTagMap.COLUMN_DISHID + "=?)";
        Cursor res = db.rawQuery(query, new String[]{id.toString()});
        if(res == null || res.getCount()<1){
            res.close();
            return rs;
        }
        res.moveToFirst();
        while(!res.isAfterLast()){
            Long tagID = res.getLong(res.getColumnIndex(Tags.COLUMN_ID));
            String name = res.getString(res.getColumnIndex(Tags.COLUMN_NAME));
            Tag tag = new Tag(tagID, name);
            rs.add(tag);
            res.moveToNext();
        }
        res.close();
        return rs;
    }

    public List<Dish> getAllDishes() {
        List<Dish> data = new ArrayList<Dish>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Dishes.TABLE_NAME, null);
        if(res == null || res.getCount()<1){
            res.close();
            return data;
        }
        res.moveToFirst();
        while(!res.isAfterLast()){
            Long id = res.getLong(res.getColumnIndex(Dishes.COLUMN_ID));
            String des = res.getString(res.getColumnIndex(Dishes.COLUMN_DESCRIPTION));
            String name = res.getString(res.getColumnIndex(Dishes.COLUMN_NAME));
            String date = res.getString(res.getColumnIndex(Dishes.COLUMN_TIME_CREATED));
            Boolean isKnown = res.getInt(res.getColumnIndex(Dishes.COLUMN_IS_KNOWN)) > 0;
            Dish dish = new Dish(id, name, getDishTags(id), des, date, isKnown);
            data.add(dish);
            res.moveToNext();
        }
        res.close();
        return data;
    }//checked

    public List<Dish> getAllDishes(User user, boolean isKnownDishes) {
        List<Dish> data = new ArrayList<Dish>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT D.* FROM " + Dishes.TABLE_NAME + " D, " +
                DishUserMap.TABLE_NAME + " DU " +
                "WHERE DU." + DishUserMap.COLUMN_USERID + "=? " +
                "and DU." + DishUserMap.COLUMN_DISHID + "=D." + Dishes.COLUMN_ID +
                " and D." + Dishes.COLUMN_IS_KNOWN + "=?"
                + " GROUP BY D." + Dishes.COLUMN_ID;
        Cursor res = db.rawQuery(query, new String[]{Long.toString(user.getID()), Integer.toString(isKnownDishes?1:0)});
        if(res == null || res.getCount()<1){
            res.close();
            return data;
        }
        res.moveToFirst();
        while(!res.isAfterLast()){
            Long id = res.getLong(res.getColumnIndex(Dishes.COLUMN_ID));
            String des = res.getString(res.getColumnIndex(Dishes.COLUMN_DESCRIPTION));
            String name = res.getString(res.getColumnIndex(Dishes.COLUMN_NAME));
            String date = res.getString(res.getColumnIndex(Dishes.COLUMN_TIME_CREATED));
            Boolean isKnown = res.getInt(res.getColumnIndex(Dishes.COLUMN_IS_KNOWN)) > 0;
            Dish dish = new Dish(id, name, getDishTags(id),des, date, isKnown);
            data.add(dish);
            res.moveToNext();
        }
        res.close();
        return data;
    }

    public List<Dish> getSearchResult(User user, boolean isKnownDishes, List<Tag> ingredientList){
        List<Dish> data = new ArrayList<Dish>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT *, (count(*)) AS score, (";
        for(int i =0; i< ingredientList.size(); i++){
            //
            query += "(" + DishTagMap.COLUMN_TAGID  + " IS " + ingredientList.get(i).getID().toString() + ") + ";
        }
        query = query.substring(0, query.length()-2);
        query += ") AS point FROM " +
                "(" + Dishes.TABLE_NAME + " INNER JOIN " + DishTagMap.TABLE_NAME + " ON " + Dishes.TABLE_NAME + "." + Dishes.COLUMN_ID + " = " + DishTagMap.TABLE_NAME + "." + DishTagMap.COLUMN_DISHID + ")"
                + " INNER JOIN " + DishUserMap.TABLE_NAME + " ON " + Dishes.TABLE_NAME + "." + Dishes.COLUMN_ID + " = " +  DishUserMap.TABLE_NAME + "." + DishUserMap.COLUMN_DISHID
                + " WHERE " + DishUserMap.COLUMN_USERID + "=?"
                + " and " + Dishes.COLUMN_IS_KNOWN + "=?"
                + " and point > 0"
                + " GROUP BY " + Dishes.TABLE_NAME + "." + Dishes.COLUMN_ID
                + " ORDER BY score desc";
        Cursor res = db.rawQuery(query, new String[]{Long.toString(user.getID()), Integer.toString(isKnownDishes?1:0)});
        if(res == null || res.getCount()<1){
            res.close();
            return data;
        }
        res.moveToFirst();
        while(!res.isAfterLast()){
            Long id = res.getLong(res.getColumnIndex(Dishes.COLUMN_ID));
            String des = res.getString(res.getColumnIndex(Dishes.COLUMN_DESCRIPTION));
            String name = res.getString(res.getColumnIndex(Dishes.COLUMN_NAME));
            String date = res.getString(res.getColumnIndex(Dishes.COLUMN_TIME_CREATED));
            Boolean isKnown = res.getInt(res.getColumnIndex(Dishes.COLUMN_IS_KNOWN)) > 0;
            Dish dish = new Dish(id, name, getDishTags(id), des, date, isKnown);
            data.add(dish);
            res.moveToNext();
        }
        res.close();
        return data;
    }

    public List<Dish> getAllDishes(Tag tag, User user) {
        List<Dish> data = new ArrayList<Dish>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT D.* FROM " + Dishes.TABLE_NAME + " D, " +
                DishTagMap.TABLE_NAME + " DT, " +
                DishUserMap.TABLE_NAME + "DU" +
                "WHERE DT." + DishTagMap.COLUMN_TAGID + "=? " +
                "and DT." + DishTagMap.COLUMN_DISHID + "=D." + Dishes.COLUMN_ID +
                "and DU." + DishUserMap.COLUMN_DISHID + "=D." + Dishes.COLUMN_ID +
                "and DU." + DishUserMap.COLUMN_USERID + "=?"
                + " GROUP BY D." + Dishes.COLUMN_ID;
        Cursor res = db.rawQuery(query, new String[]{tag.getID().toString(), user.getID().toString()});
        if(res == null || res.getCount()<1){
            res.close();
            return data;
        }
        res.moveToFirst();
        while(!res.isAfterLast()){
            Long id = res.getLong(res.getColumnIndex(Dishes.COLUMN_ID));
            String des = res.getString(res.getColumnIndex(Dishes.COLUMN_DESCRIPTION));
            String name = res.getString(res.getColumnIndex(Dishes.COLUMN_NAME));
            String date = res.getString(res.getColumnIndex(Dishes.COLUMN_TIME_CREATED));
            Boolean isKnown = res.getInt(res.getColumnIndex(Dishes.COLUMN_IS_KNOWN)) > 0;
            Dish dish = new Dish(id, name, getDishTags(id), des, date, isKnown);
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
        if(res == null || res.getCount()<1){
            res.close();
            return data;
        }
        res.moveToFirst();
        while(!res.isAfterLast()){
            Long id = res.getLong(res.getColumnIndex(Dishes.COLUMN_ID));
            String des = res.getString(res.getColumnIndex(Dishes.COLUMN_DESCRIPTION));
            String name = res.getString(res.getColumnIndex(Dishes.COLUMN_NAME));
            String date = res.getString(res.getColumnIndex(Dishes.COLUMN_TIME_CREATED));
            Boolean isKnown = res.getInt(res.getColumnIndex(Dishes.COLUMN_IS_KNOWN)) > 0;
            Dish dish = new Dish(id, name, getDishTags(id), des, date, isKnown);
            data.add(dish);
            res.moveToNext();
        }
        res.close();
        return data;
    }
    //endregion

    //region type
    public Long insertNewType(String typeName, User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inputs = new ContentValues();
        inputs.put(Types.COLUMN_NAME, typeName);
        Long rs = db.insert(Types.TABLE_NAME, null, inputs);
        if(rs>0){
            insertNewTypeUserMap(rs,user.getID());
        }
        return rs;
    }
    private boolean deleteType(Type type){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Types.TABLE_NAME, Types.COLUMN_ID + "=?", new String[]{type.getID().toString()})>0;
    }

    public boolean deleteType(Type type, User user){
        return removeTypeUserMap(type.getID(), user.getID());
    }

    private List<Type> getAllType(){
        List<Type> data = new ArrayList<Type>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Types.TABLE_NAME, null);
        if(res == null || res.getCount()<1){
            res.close();
            return data;
        }
        res.moveToFirst();
        while(!res.isAfterLast()){
            String name = res.getString(res.getColumnIndex(Types.COLUMN_NAME));
            Long id = res.getLong(res.getColumnIndex(Types.COLUMN_ID));
            Type type = new Type(id, name);
            data.add(type);
            res.moveToNext();
        }
        res.close();
        return data;
    }

    public List<Type> getAllType(User user){
        List<Type> data = new ArrayList<Type>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Types.TABLE_NAME + " WHERE " + Types.COLUMN_ID + " IN (SELECT " + TypeUserMap.COLUMN_TYPEID + " FROM " + TypeUserMap.TABLE_NAME + " WHERE " + TypeUserMap.COLUMN_USERID + "=?)";
        Cursor res = db.rawQuery(query, new String[]{user.getID().toString()});
        if(res == null || res.getCount()<1){
            res.close();
            return data;
        }
        res.moveToFirst();
        while(!res.isAfterLast()){
            String name = res.getString(res.getColumnIndex(Types.COLUMN_NAME));
            Long id = res.getLong(res.getColumnIndex(Types.COLUMN_ID));
            Type type = new Type(id, name);
            data.add(type);
            res.moveToNext();
        }
        res.close();
        return data;
    }


    //endregion

    //region dishUserMap
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

    public List<DUMap> getAllDUMap(){
        List<DUMap> data = new ArrayList<DUMap>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DishUserMap.TABLE_NAME, null);
        if(res == null || res.getCount()<1){
            res.close();
            return data;
        }
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
    //endregion

    //region dishTagMap
    public Long insertNewDishTagMap(Long DishId, Long TagId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inputs = new ContentValues();
        inputs.put(DishTagMap.COLUMN_DISHID, DishId);
        inputs.put(DishTagMap.COLUMN_TAGID, TagId);
        return db.insert(DishTagMap.TABLE_NAME, null, inputs);
    }//checked

    public List<DTMap> getAllDTMap(){
        List<DTMap> data = new ArrayList<DTMap>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DishTagMap.TABLE_NAME, null);
        if(res == null || res.getCount()<1){
            res.close();
            return data;
        }
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
    //endregion

    //region tagTypeMap
    public Long insertNewTagTypeMap(Long TagId, Long TypeId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inputs = new ContentValues();
        inputs.put(TagTypeMap.COLUMN_TAGID, TagId);
        inputs.put(TagTypeMap.COLUMN_TYPEID, TypeId);
        return db.insert(TagTypeMap.TABLE_NAME, null, inputs);
    }
    //endregion

    //region tagUserMap
    public Long insertNewTagUserMap(Long tagId, Long userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inputs = new ContentValues();
        inputs.put(TagUserMap.COLUMN_TAGID, tagId);
        inputs.put(TagUserMap.COLUMN_USERID, userId);
        return db.insert(TagUserMap.TABLE_NAME, null, inputs);
    }

    public boolean removeTagUserMap(Long tagId, Long userId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TagUserMap.TABLE_NAME, TagUserMap.COLUMN_TAGID + "=? and " + TagUserMap.COLUMN_USERID + "=?", new String[]{tagId.toString(), userId.toString()})>0;
    }
    //endregion

    //region typeUserMap
    public Long insertNewTypeUserMap(Long TypeId, Long UserId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inputs = new ContentValues();
        inputs.put(TypeUserMap.COLUMN_TYPEID, TypeId);
        inputs.put(TypeUserMap.COLUMN_USERID, UserId);
        return db.insert(TypeUserMap.TABLE_NAME, null, inputs);
    }

    public boolean removeTypeUserMap(Long typeId, Long userId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TypeUserMap.TABLE_NAME, TypeUserMap.COLUMN_TYPEID + "=? and " + TypeUserMap.COLUMN_USERID + "=?", new String[]{typeId.toString(), userId.toString()})>0;
    }
    //endregion

    //region shopping list
    public List<ShoppingListItem> getAllShoppingList(User user){
        List<ShoppingListItem> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ShoppingList.TABLE_NAME + " WHERE " + ShoppingList.COLUMN_USERID + " =?";
        Cursor res = db.rawQuery(query, new String[]{user.getID().toString()});
        if(res == null || res.getCount()<1){
            res.close();
            return data;
        }
        res.moveToFirst();
        while(!res.isAfterLast()){
            String item = res.getString(res.getColumnIndex(ShoppingList.COLUMN_ITEM));
            Long id = res.getLong(res.getColumnIndex(ShoppingList.COLUMN_ID));
            boolean isChecked = res.getInt(res.getColumnIndex(ShoppingList.Column_ISCHECKED))>0;
            ShoppingListItem SLItem = new ShoppingListItem(item, id, isChecked);
            data.add(SLItem);
            res.moveToNext();
        }
        res.close();
        return data;
    }

    public Long insertShoppingListItem(String itemName, User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inputs = new ContentValues();
        inputs.put(ShoppingList.COLUMN_ITEM, itemName);
        inputs.put(ShoppingList.COLUMN_USERID, user.getID());
        inputs.put(ShoppingList.Column_ISCHECKED, 0);
        return db.insert(ShoppingList.TABLE_NAME, null, inputs);
    }

    public boolean removeShoppingListItem(ShoppingListItem item){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ShoppingList.TABLE_NAME, ShoppingList.COLUMN_ID + "=?", new String[]{item.getId().toString()})>0;
    }

    public boolean upDateItem(ShoppingListItem item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inputs = new ContentValues();
        inputs.put(ShoppingList.COLUMN_ITEM,item.getItemName());
        inputs.put(ShoppingList.Column_ISCHECKED, item.getIsChecked());
        return db.update(ShoppingList.TABLE_NAME, inputs, ShoppingList.COLUMN_ID + " =?", new String[]{item.getId().toString()})>0;
    }
    //endregion

    public void cleanUpUnused(){
        //TODO delete all unused dishes, tags and types;
        //db.execSQL(TurnOnFK);
    }
}
