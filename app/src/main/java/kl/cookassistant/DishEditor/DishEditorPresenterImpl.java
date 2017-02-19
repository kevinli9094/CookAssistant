package kl.cookassistant.DishEditor;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cookingAssistant.kevin92.com.R;
import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.DataModel.Tag;
import kl.cookassistant.DataModel.TagsManagerMode;
import kl.cookassistant.DataModel.User;
import kl.cookassistant.DisplayDishes.DisPlayDishesActivity;
import kl.cookassistant.GlobalVars;
import kl.cookassistant.Interfaces.DishEditorPresenter;
import kl.cookassistant.TagsManagerAndSearch.TagsManagerActivity;

/**
 * Created by Li on 1/11/2017.
 */

public class DishEditorPresenterImpl implements DishEditorPresenter{
    private DishEditorModel model;
    private DishEditorActivity context;
    private GlobalVars mGV;
    private Dish currentDish;
    private List<Tag> ingredients;
    private boolean createMode;
    private DishIngredientsAdapter mAdapter;
    private ListView IngredientListView;

    public DishEditorPresenterImpl(DishEditorActivity context){
        mGV = GlobalVars.getInstance();
        this.context = context;
        this.model = new DishEditorModel(context, mGV.getCurrentUser());
        createMode = mGV.getCreateMode();
        if(createMode && !mGV.getBackFromTagManager()){
            ingredients = new ArrayList<>();
            currentDish = new Dish((long)0,"",ingredients,"",new Date(),mGV.getKnown());
        }
        else{
            currentDish = mGV.getCurrentDish();
            ingredients = currentDish.getIngredients();
        }
        mGV.setBackFromTagManager(false);
        mAdapter = new DishIngredientsAdapter(context, R.layout.ingridient_list_item, ingredients);
        IngredientListView = context.getListView();
        IngredientListView.setAdapter(mAdapter);
        context.setListViewHeight(ingredients.size());
        context.getNameTextEditor().setText(currentDish.getName());
        context.getDescriptionTextEditor().setText(currentDish.getDescription());
        context.getDateTextEditor().setText(currentDish.getTimeCreated().toString());
        context.getIsKnownCheckBox().setChecked(currentDish.getIsKnown());
    }

    private boolean updateDish(Dish newDish){
        return model.updateDish(newDish);
    }

    private boolean createDish(Dish newDish){
        return model.createDish(newDish);
    }

    public void onAddIngredientButtonClicked(){
        mGV.setMode(TagsManagerMode.Modify);
        long newId = currentDish.getID();
        String newName = context.getNameTextEditor().getText().toString();
        String newDes = context.getDescriptionTextEditor().getText().toString();
        String newDate = context.getDateTextEditor().getText().toString();
        boolean newIsKnown = context.getIsKnownCheckBox().isChecked();
        Dish newDish = new Dish(newId, newName,ingredients,newDes,newDate,newIsKnown);
        mGV.setCurrentDish(newDish);
        mGV.setIngredientList(ingredients);
        context.navigateToTagsManagerActivity();
    }

    public void onIngredientCancelButtonClicked(){
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        adb.setTitle("Cancel?");
        adb.setMessage("Are you sure you want discard all changes?");
        adb.setNegativeButton("No", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                context.navigateToDisplayDishesActivity();
            }
        });
        adb.show();
    }

    public void onIngredientConfirmButtonClicked(){
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        if(createMode){
            adb.setTitle("Create?");
            adb.setMessage("Are you sure you want create new recipe?");
            adb.setNegativeButton("Cancel", null);
            adb.setPositiveButton("Ok", new AlertDialog.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){
                    long newId = currentDish.getID();
                    String newName = context.getNameTextEditor().getText().toString();
                    String newDes = context.getDescriptionTextEditor().getText().toString();
                    String newDate = context.getDateTextEditor().getText().toString();
                    boolean newIsKnown = context.getIsKnownCheckBox().isChecked();
                    Dish newDish = new Dish(newId, newName,ingredients,newDes,newDate,newIsKnown);
                    createDish(newDish);
                    context.navigateToDisplayDishesActivity();
                }
            });
        }
        else{
            adb.setTitle("Update?");
            adb.setMessage("Are you sure you want update all changes?");
            adb.setNegativeButton("Cancel", null);
            adb.setPositiveButton("Ok", new AlertDialog.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){
                    long newId = currentDish.getID();
                    String newName = context.getNameTextEditor().getText().toString();
                    String newDes = context.getDescriptionTextEditor().getText().toString();
                    String newDate = context.getDateTextEditor().getText().toString();
                    boolean newIsKnown = context.getIsKnownCheckBox().isChecked();
                    Dish newDish = new Dish(newId, newName,ingredients,newDes,newDate,newIsKnown);
                    updateDish(newDish);
                    context.navigateToDisplayDishesActivity();
                }
            });
        }
        adb.show();
    }

    public class DishIngredientsAdapter extends ArrayAdapter<Tag> {
        DishEditorActivity context;
        List<Tag> data;
        int layoutResourceId;

        public DishIngredientsAdapter(DishEditorActivity context, int layoutResourceId,
                                      List<Tag> data){
            super(context,layoutResourceId, data);
            this.context=context;
            this.data = data;
            this.layoutResourceId = layoutResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            IngredientHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new IngredientHolder();
                holder.Name = (TextView) row.findViewById(R.id.ingredientTextView);
                holder.btnDelete = (Button) row.findViewById(R.id.IngridientDeleteButton);
                row.setTag(holder);
            } else {
                holder = (IngredientHolder) row.getTag();
            }
            Tag tag = data.get(position);
            holder.Name.setText(tag.getName());
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = IngredientListView.getPositionForView((View)v.getParent());
                    data.remove(position);
                    context.setListViewHeight(ingredients.size());
                    mAdapter.notifyDataSetChanged();
                }
            });
            return row;

        }

        class IngredientHolder {
            TextView Name;
            Button btnDelete;
        }
    }
}
