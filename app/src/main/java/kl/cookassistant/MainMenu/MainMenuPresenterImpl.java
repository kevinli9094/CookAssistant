package kl.cookassistant.MainMenu;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import kl.cookassistant.DataModel.DBhelper;
import kl.cookassistant.DataModel.User;
import kl.cookassistant.GlobalVars;
import kl.cookassistant.Interfaces.MainMenuPresenter;

/**
 * Created by Li on 11/1/2016.
 */

public class MainMenuPresenterImpl implements MainMenuPresenter{
    private MainMenuActivity context;
    public MainMenuPresenterImpl(MainMenuActivity context){
        this.context = context;
    }

    public void onKnownDishesButtonClicked(){
        context.navigateToKnowDishes();
    }
    public void onUnknownDishesButtonClicked(){
        context.navigateToUnknowDishes();
    }
    public void onSearchButtonClicked(){
        //// TODO: 1/11/2017 remove the next line!!!!
        tempFunction();
        context.navigateToSearch();
    }
    public void onShoppingListButtonClicked(){
        context.navigateToShoppingList();
    }
    public void onLogOutButtonClicked(){
        context.navigateToLogin();
    }
    public void onQuitButtonClicked(){
        context.Quit();
    }

    private void tempFunction(){
        DBhelper tempDBhelper = new DBhelper(context);
        GlobalVars mGV = GlobalVars.getInstance();
        User currentUser = mGV.getCurrentUser();
        String name = "test";
        List<String> strs = new ArrayList<String>(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l"));
        String longDescription = "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
        Date today = new Date();
        boolean isKnown = true;
        tempDBhelper.insertNewDish(name,strs,longDescription,today,isKnown,currentUser);
    }
}
