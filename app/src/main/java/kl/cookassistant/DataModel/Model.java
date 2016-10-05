package kl.cookassistant.DataModel;

import android.content.Context;

import java.util.Date;
import java.util.List;

/**
 * Created by Li on 10/5/2016.
 */

public class Model {
    private List<Dish> Dishes;
    private User User;
    private DBhelper DBhelper;

    public Model(Context appcontext){
        this.DBhelper = new DBhelper(appcontext);
    }

    public List<Dish> getDishes(){return Dishes;}
    public User getUser(){return User;}
    public Long TryLogin(String Name, String password){
        Long result = DBhelper.getUserId(Name,password);
        if (result > 0){
            this.User = new User(result, Name, password);
            this.Dishes = DBhelper.getAllDishes(this.User);
        }
        return (result>0)? result:0;
    }
    public boolean tryRegister(String Name, String password){
        return DBhelper.insertNewUser(Name, password)>0;
    }
    public boolean addDish(String Name, List<String> Ingredients, String Description, boolean IsKnown){
        Date now = new Date();
        Long ID =  DBhelper.insertNewDish(Name, Ingredients, Description, now, IsKnown, this.User);
        if(ID > 0){
            this.Dishes.add(new Dish(ID,Name,Ingredients,Description,now,IsKnown));
            return true;
        }
        return false;
    }
    public boolean removeDish(Long DishId){
        return DBhelper.removeDUMap(DishId,this.User.getID());
    }
    public List<Tag> getAllTags(){
        return DBhelper.getAllTags();
    }
    public List<Dish> getAllDishes(Tag tag){
        return DBhelper.getAllDishes(tag);
    }
    public List<Dish> getAllDishes(List<Tag> tags){
        return DBhelper.getAllDishes(tags);
    }
}
