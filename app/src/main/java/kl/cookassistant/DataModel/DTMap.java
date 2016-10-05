package kl.cookassistant.DataModel;

/**
 * Created by Li on 10/3/2016.
 */

public class DTMap {
    private Long DishId;
    private Long TagId;

    public DTMap(Long DishId, Long UserId){
        this.DishId = DishId;
        this.TagId = UserId;
    }

    public Long getDishId(){
        return this.DishId;
    }
    public Long getTagId(){
        return this.TagId;
    }
}
