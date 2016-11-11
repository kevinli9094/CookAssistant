package kl.cookassistant.MainMenu;

import android.content.Context;

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
}
