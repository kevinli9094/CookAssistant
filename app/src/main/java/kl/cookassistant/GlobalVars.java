package kl.cookassistant;

import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.DataModel.User;

/**
 * Created by Li on 1/7/2017.
 */
public class GlobalVars {
    private static GlobalVars ourInstance;
    private User currentUser;
    private Dish currentDish;
    private boolean isKnown;

    public static GlobalVars getInstance(User user) {
        if(ourInstance == null){
            ourInstance = new GlobalVars(user);
        }
        return ourInstance;
    }

    public static GlobalVars getInstance(){
        return ourInstance;
    }

    private GlobalVars(User user) {
        currentUser = user;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public Dish getCurrentDish(){
        return currentDish;
    }

    public void setCurrentDish(Dish dish){
        this.currentDish = dish;
    }

    public boolean getKnown(){
        return isKnown;
    }

    public void setKnown(boolean isKnown){
        this.isKnown = isKnown;
    }
}
