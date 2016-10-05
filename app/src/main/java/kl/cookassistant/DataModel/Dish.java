package kl.cookassistant.DataModel;


import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Li on 9/20/2016.
 */

public class Dish {
    private Long ID;
    private String Name;
    private String Description;
    private List<String> Ingredients;
    private Date TimeCreated;
    private boolean IsKnown;
    public Dish(Long ID, String Name, List<String> Ingredients, String Description, Date TimeCreated, boolean IsKnown){
        this.ID = ID;
        this.Name = Name;
        this.Description = Description;
        this.IsKnown = IsKnown;
        this.Ingredients = Ingredients;
        this.TimeCreated = TimeCreated;
    }
    public Dish(Long ID, String Name, List<String> Ingredients, String Description, String TimeCreated, boolean IsKnown){
        this.ID = ID;
        this.Name = Name;
        this.Description = Description;
        this.IsKnown = IsKnown;
        this.Ingredients = Ingredients;
        Date d=new Date();
        DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            d = iso8601Format.parse(TimeCreated);
        } catch (ParseException e) {
            Log.e(TAG, "Parsing ISO8601 datetime failed", e);
        }
        this.TimeCreated = d;
    }
    public Dish(Long ID, String Name, String Description, String IngredientsSTR, String TimeCreated, boolean IsKnown){
        String temp = IngredientsSTR.replaceAll("\\s+","");
        List<String> Ingredients = new ArrayList<>(Arrays.asList(temp.split(",")));
        this.ID = ID;
        this.Name = Name;
        this.Description = Description;
        this.IsKnown = IsKnown;
        this.Ingredients = Ingredients;
        Date d=new Date();
        DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            d = iso8601Format.parse(TimeCreated);
        } catch (ParseException e) {
            Log.e(TAG, "Parsing ISO8601 datetime failed", e);
        }
        this.TimeCreated = d;

    }

    public Long getID(){
        return ID;
    }
    public String getName(){
        return Name;
    }
    public String getDescription(){
        return Description;
    }
    public Date getTimeCreated(){
        return TimeCreated;
    }
    public boolean getIsKnown(){
        return IsKnown;
    }
    public List<String> getIngredients(){return Ingredients;}


}
