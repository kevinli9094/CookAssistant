package kl.cookassistant.DataModel;

/**
 * Created by Li on 2/15/2017.
 */

public class ShoppingListItem {
    private String itemName;
    private Long id;
    private boolean isChecked;

    public ShoppingListItem(String itemName, Long id, boolean isChecked){
        this.itemName = itemName;
        this.id = id;
        this.isChecked = isChecked;
    }

    public String getItemName(){
        return this.itemName;
    }
    public Long getId(){
        return this.id;
    }
    public boolean getIsChecked(){
        return this. isChecked;
    }
    public void setIsChecked(boolean isChecked){
        this.isChecked = isChecked;
    }
}
