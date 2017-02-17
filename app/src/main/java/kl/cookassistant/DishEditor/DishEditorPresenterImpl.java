package kl.cookassistant.DishEditor;

import android.content.Context;

import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.DataModel.User;
import kl.cookassistant.GlobalVars;

/**
 * Created by Li on 1/11/2017.
 */

public class DishEditorPresenterImpl {
    private DishEditorModel model;
    private Context context;

    public DishEditorPresenterImpl(DishEditorActivity context, User currentUser){
        this.context = context;
        this.model = new DishEditorModel(context, currentUser);

    }

    public boolean updateDish(Dish newDish){
        return model.updateDish(newDish);
    }

    public boolean createDish(Dish newDish){
        return model.createDish(newDish);
    }
}
