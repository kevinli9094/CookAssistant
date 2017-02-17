package kl.cookassistant.TagsManagerAndSearch;

import android.content.Context;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.DataModel.Tag;
import kl.cookassistant.DataModel.Type;
import kl.cookassistant.DataModel.User;
import kl.cookassistant.GlobalVars;

/**
 * Created by Li on 1/28/2017.
 */

public class TagsManagerPresenterImpl {
    private TagsManagerModel model;
    private Context context;
    private GlobalVars mGV;
    private User currentUser;

    public TagsManagerPresenterImpl(Context context){
        this.context = context;
        this.mGV = GlobalVars.getInstance();
        this.currentUser = mGV.getCurrentUser();
        this.model = new TagsManagerModel(context, currentUser);
    }

    public boolean updateDish(Dish dish){
        return model.updateDish(dish);
    }

    public boolean deleteTag(Tag tag){
        return model.deleteTag(tag);
    }

    public boolean combineTags(List<Tag> tags, String newTagName){
        return model.combineTags(tags, newTagName);
    }

    public LinkedHashMap<Type, List<Tag>> getExpandableData() {
        return model.getExpandableData();
    }
}
