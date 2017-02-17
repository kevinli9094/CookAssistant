package kl.cookassistant.Login;

import android.content.Context;

import java.util.Date;
import java.util.List;

import kl.cookassistant.DataModel.DBhelper;
import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.DataModel.Tag;
import kl.cookassistant.DataModel.User;

/**
 * Created by Li on 10/5/2016.
 */

public class LoginModel {
    private kl.cookassistant.DataModel.DBhelper DBhelper;

    public LoginModel(Context appcontext){
        this.DBhelper = new DBhelper(appcontext);
    }


    public Long tryLogin(String Name, String password){
        Long result = DBhelper.getUserId(Name,password);
        return (result>0)? result:0;
    }
    public boolean tryRegister(String Name, String password){
        return DBhelper.insertNewUser(Name, password)>0;
    }

}
