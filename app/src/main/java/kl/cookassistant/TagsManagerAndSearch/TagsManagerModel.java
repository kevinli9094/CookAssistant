package kl.cookassistant.TagsManagerAndSearch;

import android.content.Context;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import kl.cookassistant.DataModel.DBhelper;
import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.DataModel.Tag;
import kl.cookassistant.DataModel.Type;
import kl.cookassistant.DataModel.User;

/**
 * Created by Li on 1/28/2017.
 */

public class TagsManagerModel {
    private DBhelper dBhelper;
    private User currentUser;
    private LinkedHashMap<Type, List<Tag>> data;

    public TagsManagerModel(Context appcontext, User user){
        this.dBhelper = new DBhelper(appcontext);
        this.currentUser = user;
    }

    public boolean updateDish(Dish dish){
        return dBhelper.updateDish(dish);
    }

    public boolean deleteTag(Tag tag){
        return dBhelper.deleteTag(tag,currentUser);
    }

    public boolean combineTags(List<Tag> tags, String newTagName){
        Long newId = dBhelper.insertNewTag(newTagName,currentUser);
        Tag newTag = new Tag(newId, newTagName);
        boolean rs = true;
        for(int i = 0;i<tags.size(); i++){
            rs = rs && dBhelper.replaceWithNewTag(tags.get(i), newTag,currentUser) && dBhelper.deleteTag(tags.get(i),currentUser);
        }
        return rs;
    }

    public boolean isTagUsed(Tag tag){
        return dBhelper.isTagUsed(tag,currentUser);
    }

    public LinkedHashMap<Type, List<Tag>> getExpandableData() {
        data = new LinkedHashMap<>();
        //get all types
        List<Type> allTypes = dBhelper.getAllType(currentUser);

        //for each type, get all tags
        for(int i = 0; i< allTypes.size(); i++){
            List<Tag> newTags = dBhelper.getAllTags(allTypes.get(i),currentUser);
            data.put(allTypes.get(i), newTags);
        }
        //find all tags that does not have a type
        List<Tag> tagsWithoutType = dBhelper.getTagsWithoutType(currentUser);
        data.put(new Type( (long)0 ,"no type"), tagsWithoutType);

        return data;
    }


}
