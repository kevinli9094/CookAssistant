package kl.cookassistant.DisplayDishes;

import java.util.List;

import kl.cookassistant.DataModel.Dish;
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
    private List<Dish> dishes;

    public DisplayDishesListPresenterImpl(DisPlayDishesActivity context){
        this.context = context;
        this.mGV = GlobalVars.getInstance();
        this.user = mGV.getCurrentUser();
        this.model = new DisplayDishesModel(context,user);
        this.dishes = model.getDishes(mGV.getKnown());
    }

    public List<Dish> getDishes(){
        return dishes;
    }

}
