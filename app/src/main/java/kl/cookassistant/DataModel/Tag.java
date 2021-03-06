package kl.cookassistant.DataModel;

/**
 * Created by Li on 9/20/2016.
 */

public class Tag {
    private Long ID;
    private String Name;
    public Tag(Long ID, String Name){
        this.ID = ID;
        this.Name = Name;
    }
    public Long getID(){
        return ID;
    }
    public String getName(){
        return Name;
    }

    @Override
    public boolean equals(Object d){
        return this.ID.equals(((Tag)d).getID());
    }

    @Override
    public int hashCode() {
        return this.ID.intValue();
    }
}
