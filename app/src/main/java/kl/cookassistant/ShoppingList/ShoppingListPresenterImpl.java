package kl.cookassistant.ShoppingList;

import java.util.List;

import kl.cookassistant.DataModel.ShoppingListItem;
import kl.cookassistant.DataModel.User;
import kl.cookassistant.GlobalVars;
import kl.cookassistant.NewTag.NewTagActivity;
import kl.cookassistant.NewTag.NewTagModel;

/**
 * Created by Li on 2/15/2017.
 */

public class ShoppingListPresenterImpl {
    private ShoppingListAcrivity context;
    private ShoppingListModel model;
    private GlobalVars mGV;

    public ShoppingListPresenterImpl(ShoppingListAcrivity context){
        this.context = context;
        mGV = GlobalVars.getInstance();
        model = new ShoppingListModel(context, mGV.getCurrentUser());
    }

    public List<ShoppingListItem> getData(){
        return model.getData();
    }
    public Long insertNewItem(String itemName){
        return model.insertNewItem(itemName);
    }
    public boolean deleteItem(ShoppingListItem item){
        return model.deleteItem(item);
    }
    public boolean updateItem(ShoppingListItem item){
        return model.updateItem(item);
    }
}
