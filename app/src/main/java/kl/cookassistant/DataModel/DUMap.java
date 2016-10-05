package kl.cookassistant.DataModel;

/**
 * Created by Li on 10/3/2016.
 */

public class DUMap {
    private Long DishId;
    private Long UserId;

    public DUMap(Long DishId, Long UserId){
        this.DishId = DishId;
        this.UserId = UserId;
    }

    public Long getDishId(){
        return this.DishId;
    }
    public Long getUserId(){
        return this.UserId;
    }
}

