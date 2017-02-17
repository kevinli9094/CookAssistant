package kl.cookassistant.DisplayDishes;

import android.content.Context;

import java.util.List;

import kl.cookassistant.DataModel.DBhelper;
import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.DataModel.Tag;
import kl.cookassistant.DataModel.User;

/**
 * Created by Li on 11/16/2016.
 */

public class DisplayDishesModel {
    private List<Dish> dishes;
    private kl.cookassistant.DataModel.User user;
    private kl.cookassistant.DataModel.DBhelper DBhelper;

    public DisplayDishesModel(Context appcontext, kl.cookassistant.DataModel.User user){
        this.DBhelper = new kl.cookassistant.DataModel.DBhelper(appcontext);
        this.user = user;
    }

    public List<Dish> getDishes(boolean isKnown){
        this.dishes = DBhelper.getAllDishes(user, isKnown);
        return this.dishes;
    }

    public List<Dish> search(List<Tag> ingredientList, boolean isKnown){
        this.dishes = DBhelper.getSearchResult(user, isKnown, ingredientList);
        return this.dishes;
    }

    public User getUser(){
        return user;
    }

    public boolean deleteDish(int position){
        boolean rs = this.DBhelper.deleteDish(dishes.get(position).getID());
        if(rs){
            dishes.remove(position);
        }
        return rs;
    }
}
