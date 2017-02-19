package kl.cookassistant.TagsManagerAndSearch;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import cookingAssistant.kevin92.com.R;
import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.DataModel.Tag;
import kl.cookassistant.DataModel.TagsManagerMode;
import kl.cookassistant.DataModel.Type;
import kl.cookassistant.DishEditor.DishEditorActivity;
import kl.cookassistant.DisplayDishes.DisPlayDishesActivity;
import kl.cookassistant.GlobalVars;
import kl.cookassistant.Interfaces.TagsManagerPresenter;
import kl.cookassistant.MainMenu.MainMenuActivity;
import kl.cookassistant.NewTag.NewTagActivity;

/**
 * Created by Li on 1/28/2017.
 */

public class TagsManagerActivity extends AppCompatActivity {
    Button button1;
    Button button2;
    Button button3;
    ExpandableListView expandableListView;
    TagsManagerPresenter presenter;
    int buttonHeight = 45;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagsmanager_search);

        expandableListView = (ExpandableListView) findViewById(R.id.ingredientExpandableList);
        button1 = (Button) findViewById(R.id.tagManagerButton1);
        button2 = (Button) findViewById(R.id.tagManagerButton2);
        button3 = (Button) findViewById(R.id.tagManagerButton3);

        presenter = new TagsManagerPresenterImpl(this);
    }


    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    public void setMode(TagsManagerMode mode){
        if(mode == TagsManagerMode.Manage){
            button1.setText("add new ingredients");
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onAddNewIngredientsClicked();
                }
            });
            button2.setText("delete selected");
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onDeleteSelectedClicked();
                }
            });
            button3.setText("combine selected into one");
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onCombineSelectedClicked();
                }
            });
        }
        else if(mode == TagsManagerMode.Modify){
            button1.setText("add new ingredients");
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onAddNewIngredientsClicked();
                }
            });
            button2.setText("add selected to dish");
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onAddToDishClicked();
                }
            });
            button3.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = expandableListView.getLayoutParams();
            params.height = params.height + buttonHeight;
            expandableListView.setLayoutParams(params);
            expandableListView.requestLayout();
        }
        else if(mode == TagsManagerMode.Search){
            button1.setText("search known");
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onSearchKnownClicked();
                }
            });
            button2.setText("search unknown");
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onSearchUnknownClicked();
                }
            });
            button3.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = expandableListView.getLayoutParams();
            params.height = params.height + buttonHeight;
            expandableListView.setLayoutParams(params);
            expandableListView.requestLayout();
        }
    }

    public ExpandableListView getExpandableListView(){
        return this.expandableListView;
    }

    public void navigateToDishEditorActivity(){
        startActivity(new Intent(this, DishEditorActivity.class));
        finish();
    }
    public void navigateToMainMenuActivity(){
        startActivity(new Intent(this, MainMenuActivity.class));
        finish();
    }
    public void navigateToNewTagActivity(){
        startActivity(new Intent(TagsManagerActivity.this, NewTagActivity.class));
        finish();
    }
    public void navigateToDisplayDishesActivity(){
        startActivity(new Intent(TagsManagerActivity.this, DisPlayDishesActivity.class));
        finish();
    }



}
