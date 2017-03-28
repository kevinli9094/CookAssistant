package kl.cookassistant.DisplayDishes;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kl.cookassistant.DataModel.Dish;
import cookingAssistant.kevin92.com.R;
import kl.cookassistant.DataModel.Tag;
import kl.cookassistant.DishEditor.DishEditorActivity;
import kl.cookassistant.GlobalVars;
import kl.cookassistant.Interfaces.DisplayDishesListPresenter;
import kl.cookassistant.MainMenu.MainMenuActivity;
import kl.cookassistant.TagsManagerAndSearch.TagsManagerActivity;

/**
 * Created by Li on 11/11/2016.
 */

public class DisPlayDishesActivity extends AppCompatActivity {
    private ListView dishList;
    private FloatingActionButton floatingAddButton;
    private DisplayDishesListPresenter presenter;


    public ListView getDishListListView(){
        return this.dishList;
    }
    public void navigateToDishEditorActivity(){
        startActivity(new Intent(DisPlayDishesActivity.this, DishEditorActivity.class));
        finish();
    }
    public void navigateToMainMenuActivity(){
        startActivity(new Intent(this, MainMenuActivity.class));
        finish();
    }
    public void navigateToTagsManagerAndSearchActivity(){
        startActivity(new Intent(this, TagsManagerActivity.class));
        finish();
    }
    public void hideFloatingAddButton(){
        floatingAddButton.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_dishes_list);
        dishList = (ListView) findViewById(R.id.listView);
        presenter = new DisplayDishesListPresenterImpl(this);
        floatingAddButton = (FloatingActionButton) findViewById(R.id.floatingAddButton);
        floatingAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onFloatingAddButtonClicked();
                }
        });
        presenter.isOnSearch();
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }


}
