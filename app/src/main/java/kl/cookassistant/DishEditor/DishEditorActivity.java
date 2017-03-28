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
import kl.cookassistant.Interfaces.DishEditorPresenter;
import kl.cookassistant.TagsManagerAndSearch.TagsManagerActivity;

/**
 * Created by Li on 1/11/2017.
 */

public class DishEditorActivity extends AppCompatActivity {
    private EditText nameTextEditor;
    private EditText descriptionTextEditor;
    private EditText dateTextEditor;
    private CheckBox isKnownCheckBox;
    private Button editButton;
    private Button addIngredientButton;
    private Button ingredientCancelButton;
    private Button ingredientConfirmButton;
    private ListView IngredientListView;
    private DishEditorPresenter presenter;

    private int itemHeight = 200;

    public ListView getListView(){
        return IngredientListView;
    }
    public void setListViewHeight(int dataSize){
        ViewGroup.LayoutParams params = IngredientListView.getLayoutParams();
        params.height = itemHeight*dataSize;
        IngredientListView.setLayoutParams(params);
        IngredientListView.requestLayout();
    }

    public EditText getNameTextEditor(){
        return this.nameTextEditor;
    }
    public EditText getDescriptionTextEditor(){
        return this.descriptionTextEditor;
    }
    public EditText getDateTextEditor(){
        return this.dateTextEditor;
    }
    public CheckBox getIsKnownCheckBox(){
        return this.isKnownCheckBox;
    }
    public Button getEditButton(){return this.editButton;}
    public Button getAddIngredientButton(){return this.addIngredientButton;}
    public Button getIngredientCancelButton(){return this.ingredientCancelButton;}
    public Button getIngredientConfirmButton(){return this.ingredientConfirmButton;}

    public void navigateToTagsManagerActivity(){
        startActivity(new Intent(DishEditorActivity.this, TagsManagerActivity.class));
        finish();
    }
    public void navigateToDisplayDishesActivity(){
        startActivity(new Intent(DishEditorActivity.this, DisPlayDishesActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_editor);

        nameTextEditor = (EditText) findViewById(R.id.NameTextEditor);
        descriptionTextEditor = (EditText) findViewById(R.id.DescriptionTextEditor);
        dateTextEditor = (EditText) findViewById(R.id.DateTextEditor);
        dateTextEditor.setKeyListener(null);
        isKnownCheckBox = (CheckBox) findViewById(R.id.isKnownCheckBox);
        editButton = (Button) findViewById(R.id.dishEditorEditButton);
        addIngredientButton =(Button) findViewById(R.id.AddIngredientButton);
        ingredientCancelButton = (Button)findViewById(R.id.IngredientCancelButton);
        ingredientConfirmButton = (Button) findViewById(R.id.IngredientConfirmButton);

        IngredientListView = (ListView) findViewById(R.id.IngredientsList);

        presenter = new DishEditorPresenterImpl(this);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onEditButtonClicked();
            }
        });

        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddIngredientButtonClicked();
            }
        });
        ingredientCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onIngredientCancelButtonClicked();
            }
        });
        ingredientConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onIngredientConfirmButtonClicked();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, DisPlayDishesActivity.class));
        finish();
    }
}
