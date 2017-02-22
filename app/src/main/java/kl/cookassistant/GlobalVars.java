package kl.cookassistant;

import java.util.List;

import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.DataModel.Tag;
import kl.cookassistant.DataModel.TagsManagerMode;
import kl.cookassistant.DataModel.User;

/**
 * Created by Li on 1/7/2017.
 */
public class GlobalVars {
    private static GlobalVars ourInstance;
    private User currentUser;
    private Dish currentDish;
    private boolean isKnown;
    private boolean createMode;
    private boolean onSearch;
    private boolean backFromTagManager = false;
    private boolean onCombineTag;
    private TagsManagerMode mode;
    private List<Tag> ingredientList;

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

    public void setCreateMode(boolean isCreateMode){
        this.createMode = isCreateMode;
    }
    public boolean getCreateMode(){
        return this.createMode;
    }

    public void setOnSearch(boolean searching){
        this.onSearch = searching;
    }
    public boolean getOnSearch(){
        return this.onSearch;
    }

    public void setOnCombineTag(boolean comnine){
        this.onCombineTag = comnine;
    }
    public boolean getOnCombineTag(){
        return this.onCombineTag;
    }

    public void setIngredientList(List<Tag> strList){
        this.ingredientList = strList;
    }
    public List<Tag> getIngredientList(){
        return this.ingredientList;
    }

    public void setMode(TagsManagerMode mode){
        this.mode =  mode;
    }
    public TagsManagerMode getMode(){
        return this.mode;
    }

    public void setBackFromTagManager(boolean bool){
        this.backFromTagManager = bool;
    }
    public boolean getBackFromTagManager() {
        return this.backFromTagManager;
    }
}
