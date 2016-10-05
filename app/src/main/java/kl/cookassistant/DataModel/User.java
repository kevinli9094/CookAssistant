package kl.cookassistant.DataModel;

/**
 * Created by Li on 9/20/2016.
 */

public class User {
    private Long ID;
    private String Password;
    private String Name;
    public User(Long ID, String Password, String Name){
        this.ID = ID;
        this.Password = Password;
        this.Name = Name;
    }
    public Long getID(){
        return ID;
    }
    public String getPassword(){
        return Password;
    }
    public String getName(){
        return Name;
    }
}
