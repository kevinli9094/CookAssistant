package kl.cookassistant.DishEditor;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cookingAssistant.kevin92.com.R;
import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.DataModel.Tag;
import kl.cookassistant.DataModel.TagsManagerMode;
import kl.cookassistant.DisplayDishes.DisPlayDishesActivity;
import kl.cookassistant.GlobalVars;
import kl.cookassistant.TagsManagerAndSearch.TagsManagerActivity;

/**
 * Created by Li on 1/11/2017.
 */

public class DishEditorActivity extends AppCompatActivity {
    private TextView nameTextEditor;
    private TextView descriptionTextEditor;
    private TextView dateTextEditor;
    private CheckBox isKnownCheckBox;
    private Button addIngredientButton;
    private Button ingredientCancelButton;
    private Button ingredientConfirmButton;
    private DishEditorPresenterImpl presenter;
    private Dish currentDish;
    private GlobalVars mGV;
    private List<Tag> ingredients;
    private ListView IngredientListView;
    private DishIngredientsAdapter mAdapter;
    private boolean createMode;

    private int itemHeight = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_editor);
        mGV = GlobalVars.getInstance();
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
        nameTextEditor = (TextView) findViewById(R.id.NameTextEditor);
        descriptionTextEditor = (TextView) findViewById(R.id.DescriptionTextEditor);
        dateTextEditor = (TextView) findViewById(R.id.DateTextEditor);
        dateTextEditor.setKeyListener(null);
        isKnownCheckBox = (CheckBox) findViewById(R.id.isKnownCheckBox);
        addIngredientButton =(Button) findViewById(R.id.AddIngredientButton);
        ingredientCancelButton = (Button)findViewById(R.id.IngredientCancelButton);
        ingredientConfirmButton = (Button) findViewById(R.id.IngredientConfirmButton);
        mAdapter = new DishIngredientsAdapter(DishEditorActivity.this, R.layout.ingridient_list_item, ingredients);
        IngredientListView = (ListView) findViewById(R.id.IngredientsList);
        ViewGroup.LayoutParams params = IngredientListView.getLayoutParams();
        params.height = itemHeight*ingredients.size();
        IngredientListView.setLayoutParams(params);
        IngredientListView.requestLayout();

        IngredientListView.setAdapter(mAdapter);
        nameTextEditor.setText(currentDish.getName());
        descriptionTextEditor.setText(currentDish.getDescription());
        dateTextEditor.setText(currentDish.getTimeCreated().toString());
        isKnownCheckBox.setChecked(currentDish.getIsKnown());

        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo: change this
                mGV.setMode(TagsManagerMode.Modify);
                long newId = currentDish.getID();
                String newName = nameTextEditor.getText().toString();
                String newDes = descriptionTextEditor.getText().toString();
                String newDate = dateTextEditor.getText().toString();
                boolean newIsKnown = isKnownCheckBox.isChecked();
                Dish newDish = new Dish(newId, newName,ingredients,newDes,newDate,newIsKnown);
                mGV.setCurrentDish(newDish);
                mGV.setIngredientList(ingredients);
                startActivity(new Intent(DishEditorActivity.this, TagsManagerActivity.class));
                finish();
            }
        });
        ingredientCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(DishEditorActivity.this);
                adb.setTitle("Cancel?");
                adb.setMessage("Are you sure you want discard all changes?");
                adb.setNegativeButton("No", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        startActivity(new Intent(DishEditorActivity.this, DisPlayDishesActivity.class));
                        finish();
                    }
                });
                adb.show();
            }
        });
        ingredientConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder adb = new AlertDialog.Builder(DishEditorActivity.this);
                if(createMode){
                    adb.setTitle("Create?");
                    adb.setMessage("Are you sure you want create new recipe?");
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            long newId = currentDish.getID();
                            String newName = nameTextEditor.getText().toString();
                            String newDes = descriptionTextEditor.getText().toString();
                            String newDate = dateTextEditor.getText().toString();
                            boolean newIsKnown = isKnownCheckBox.isChecked();
                            Dish newDish = new Dish(newId, newName,ingredients,newDes,newDate,newIsKnown);
                            presenter.createDish(newDish);
                            startActivity(new Intent(DishEditorActivity.this, DisPlayDishesActivity.class));
                            finish();
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
                            String newName = nameTextEditor.getText().toString();
                            String newDes = descriptionTextEditor.getText().toString();
                            String newDate = dateTextEditor.getText().toString();
                            boolean newIsKnown = isKnownCheckBox.isChecked();
                            Dish newDish = new Dish(newId, newName,ingredients,newDes,newDate,newIsKnown);
                            presenter.updateDish(newDish);
                            startActivity(new Intent(DishEditorActivity.this, DisPlayDishesActivity.class));
                            finish();
                        }
                    });
                }
                adb.show();
            }
        });

        presenter = new DishEditorPresenterImpl(this,mGV.getCurrentUser());

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, DisPlayDishesActivity.class));
        finish();
    }

    public class DishIngredientsAdapter extends ArrayAdapter<Tag>{
        Context context;
        List<Tag> data;
        int layoutResourceId;

        public DishIngredientsAdapter(Context context, int layoutResourceId,
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
                    ViewGroup.LayoutParams params = IngredientListView.getLayoutParams();
                    params.height = itemHeight*ingredients.size();
                    IngredientListView.setLayoutParams(params);
                    IngredientListView.requestLayout();
                    mAdapter.notifyDataSetChanged();
                }
            });
            return row;

        }

        public List<Tag> getAllIngredientsFromView(){
            return this.data;
        }

        class IngredientHolder {
            TextView Name;
            Button btnDelete;
        }
    }
}
