package kl.cookassistant.DataModel;

/**
 * Created by Li on 2/1/2017.
 */

public class Type {
    private Long ID;
    private String Name;
    private boolean checked;
    public Type(Long ID, String Name){
        this.ID = ID;
        this.Name = Name;
        this.checked = false;
    }
    public Long getID(){
        return ID;
    }
    public String getName(){
        return Name;
    }
    public void setChecked(boolean isChecked){
        this.checked = isChecked;
    }
    public boolean getChecked(){
        return this.checked;
    }

    @Override
    public boolean equals(Object d){
        return this.ID.equals(((Type)d).getID());
    }

    @Override
    public int hashCode() {
        return this.ID.intValue();
    }
}
