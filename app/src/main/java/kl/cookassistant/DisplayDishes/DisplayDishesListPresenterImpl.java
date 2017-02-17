package kl.cookassistant.DisplayDishes;

import java.util.List;

import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.DataModel.Tag;
import kl.cookassistant.DataModel.User;
import kl.cookassistant.GlobalVars;
import kl.cookassistant.Interfaces.DisplayDishesListPresenter;

/**
 * Created by Li on 11/11/2016.
 */

public class DisplayDishesListPresenterImpl implements DisplayDishesListPresenter {
    private DisPlayDishesActivity context;
    private DisplayDishesModel model;
    private User user;
    private GlobalVars mGV;

    public DisplayDishesListPresenterImpl(DisPlayDishesActivity context){
        this.context = context;
        this.mGV = GlobalVars.getInstance();
        this.user = mGV.getCurrentUser();
        this.model = new DisplayDishesModel(context,user);
    }

    public List<Dish> getDishes(){
        return model.getDishes(mGV.getKnown());
    }

    public List<Dish> getSearchResult(List<Tag> ingredientList){
        return model.search(ingredientList, mGV.getKnown());
    }

    public boolean removeDish(int position){
        return model.deleteDish(position);
    }

}
