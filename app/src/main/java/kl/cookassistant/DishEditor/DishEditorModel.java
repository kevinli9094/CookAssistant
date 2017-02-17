package kl.cookassistant.DishEditor;

import android.content.Context;

import kl.cookassistant.DataModel.DBhelper;
import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.DataModel.User;

/**
 * Created by Li on 1/11/2017.
 */

public class DishEditorModel {
    private User currentUser;
    private DBhelper dBhelper;

    public DishEditorModel(Context appcontext, User currentUser){
        this.currentUser = currentUser;
        this.dBhelper = new kl.cookassistant.DataModel.DBhelper(appcontext);;
    }

    public boolean updateDish(Dish newDish){
        return dBhelper.updateDish(newDish);
    }

    public boolean createDish(Dish newDish){
        return dBhelper.insertNewDish(newDish, currentUser)>0;
    }
}
