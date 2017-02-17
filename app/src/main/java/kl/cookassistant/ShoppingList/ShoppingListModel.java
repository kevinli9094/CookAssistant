package kl.cookassistant.ShoppingList;

import android.content.Context;

import java.util.List;

import kl.cookassistant.DataModel.DBhelper;
import kl.cookassistant.DataModel.ShoppingListItem;
import kl.cookassistant.DataModel.User;

/**
 * Created by Li on 2/15/2017.
 */

public class ShoppingListModel {
    private DBhelper dBhelper;
    private User currentUser;
    private List<ShoppingListItem> data;

    public ShoppingListModel(Context appcontext, User user){
        this.dBhelper = new DBhelper(appcontext);
        this.currentUser = user;
        this.data = dBhelper.getAllShoppingList(user);
    }

    public List<ShoppingListItem> getData(){
        return this.data;
    }
    public Long insertNewItem(String itemName){
        return dBhelper.insertShoppingListItem(itemName, this.currentUser);
    }
    public boolean deleteItem(ShoppingListItem item){
        return dBhelper.removeShoppingListItem(item);
    }

    public boolean updateItem(ShoppingListItem item){
        return dBhelper.upDateItem(item);
    }
}
