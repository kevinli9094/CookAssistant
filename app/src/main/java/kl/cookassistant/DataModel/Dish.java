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
    private Long id;
    private String name;
    private String description;
    private List<Tag> ingredients;
    private Date timeCreated;
    private boolean isKnown;
    public Dish(Long ID, String Name, List<Tag> Ingredients, String Description, Date TimeCreated, boolean IsKnown){
        this.id = ID;
        this.name = Name;
        this.description = Description;
        this.isKnown = IsKnown;
        this.ingredients = Ingredients;
        this.timeCreated = TimeCreated;
    }
    public Dish(Long ID, String Name, List<Tag> Ingredients, String Description, String TimeCreated, boolean IsKnown){
        this.id = ID;
        this.name = Name;
        this.description = Description;
        this.isKnown = IsKnown;
        this.ingredients = Ingredients;
        Date d=new Date();
        DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            d = iso8601Format.parse(TimeCreated);
        } catch (ParseException e) {
            Log.e(TAG, "Parsing ISO8601 datetime failed", e);
        }
        this.timeCreated = d;
    }
    public Dish(Long ID, String Name, String IngredientsSTR,  String Description, String TimeCreated, boolean IsKnown){
        String temp = IngredientsSTR.replaceAll("\\s+","");
        List<String> ingredientStrs = Arrays.asList(temp.split(","));
        List<Tag> Ingredients = new ArrayList<>();
        for(int i =0; i< ingredientStrs.size();i++){
            Ingredients.add(new Tag((long)0, ingredientStrs.get(i)));
        }
        this.id = ID;
        this.name = Name;
        this.description = Description;
        this.isKnown = IsKnown;
        this.ingredients = Ingredients;
        Date d=new Date();
        DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            d = iso8601Format.parse(TimeCreated);
        } catch (ParseException e) {
            Log.e(TAG, "Parsing ISO8601 datetime failed", e);
        }
        this.timeCreated = d;

    }

    public Long getID(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public Date getTimeCreated(){
        return timeCreated;
    }
    public boolean getIsKnown(){
        return isKnown;
    }
    public List<Tag> getIngredients(){return ingredients;}
    public void setIngredients(List<Tag> newIngredients){
        this.ingredients = newIngredients;
    }


}
