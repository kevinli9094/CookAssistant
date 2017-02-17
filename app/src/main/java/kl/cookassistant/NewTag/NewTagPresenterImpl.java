package kl.cookassistant.NewTag;
import java.util.List;

import kl.cookassistant.DataModel.Type;
import kl.cookassistant.DataModel.User;
import kl.cookassistant.GlobalVars;

/**
 * Created by Li on 2/9/2017.
 */

public class NewTagPresenterImpl {
    private NewTagActivity context;
    private NewTagModel model;
    private GlobalVars mGV;
    private User currentUser;

    public NewTagPresenterImpl(NewTagActivity context){
        this.context = context;
        mGV = GlobalVars.getInstance();
        this.currentUser = mGV.getCurrentUser();
        this.model = new NewTagModel(context, currentUser);
    }

    public List<Type> getAllTypes(){
        return model.getAllTypes();
    }

    public boolean createNewTag(String name, Type type){
        return model.createNewTag(name,type);
    }

    public Long insertNewType(String name){
        return model.inseartNewType(name);
    }
}
