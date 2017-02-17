package kl.cookassistant.MainMenu;

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
    private GlobalVars mGV;
    public MainMenuPresenterImpl(MainMenuActivity context){
        this.context = context;
        mGV = GlobalVars.getInstance();
    }

    public void onKnownDishesButtonClicked(){
        mGV.setKnown(true);
        context.navigateToDishList();
    }
    public void onUnknownDishesButtonClicked(){
        mGV.setKnown(false);
        context.navigateToDishList();
    }
    public void onSearchButtonClicked(){
        context.navigateToSearch();
    }
    public void onShoppingListButtonClicked(){
        context.navigateToShoppingList();
    }
    public void onTagManagerButtonClicked(){context.navigateToTagManager();}
    public void onLogOutButtonClicked(){
        context.navigateToLogin();
    }
    public void onQuitButtonClicked(){
        context.Quit();
    }

//    private void tempFunction(){
//        DBhelper tempDBhelper = new DBhelper(context);
//        User currentUser = mGV.getCurrentUser();
//        String name = "long ass name !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ABC                                     you suck";
//        List<String> strs = new ArrayList<String>(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l"));
//        String longDescription = "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
//        Date today = new Date();
//        boolean isKnown = true;
//        tempDBhelper.insertNewDish(name,strs,longDescription,today,isKnown,currentUser);
//    }
}
