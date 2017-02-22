package kl.cookassistant.NewTag;

import android.content.Context;

import java.util.List;

import kl.cookassistant.DataModel.DBhelper;
import kl.cookassistant.DataModel.Tag;
import kl.cookassistant.DataModel.Type;
import kl.cookassistant.DataModel.User;

/**
 * Created by Li on 2/9/2017.
 */

public class NewTagModel {
    private DBhelper dBhelper;
    private User currentUser;
    private List<Type> data;

    public NewTagModel(Context appcontext, User user){
        this.dBhelper = new DBhelper(appcontext);
        this.currentUser = user;
    }

    public List<Type> getAllTypes(){
        if(this.data == null){
            this.data = dBhelper.getAllType(currentUser);
        }
        return this.data;
    }

    public Long createNewTag(String newTagName, Type type){
        //todo what happen if there are two item with same name;
        Long newTagId = dBhelper.insertNewTag(newTagName,currentUser);
        boolean rs = type == null? true: dBhelper.insertNewTagTypeMap(newTagId, type.getID())>0;
        return rs?newTagId:-1;
    }

    public Long inseartNewType(String typeName){
        Long newId =  dBhelper.insertNewType(typeName, currentUser);
        if(newId > 0){
            Type newType = new Type(newId, typeName);
            data.add(newType);
        }
        return newId;
    }

    public boolean combineTags(List<Tag> tags, Tag newTag){
        boolean rs = true;
        for(int i = 0;i<tags.size(); i++){
            rs = rs && dBhelper.replaceWithNewTag(tags.get(i), newTag,currentUser) && dBhelper.deleteTag(tags.get(i),currentUser);
        }
        return rs;
    }
}
