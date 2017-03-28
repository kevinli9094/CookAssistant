package kl.cookassistant.MainMenu;
import kl.cookassistant.DataModel.Tag;
import kl.cookassistant.DataModel.TagsManagerMode;
import kl.cookassistant.DataModel.User;
import kl.cookassistant.DisplayDishes.DisPlayDishesActivity;
import kl.cookassistant.GlobalVars;
import kl.cookassistant.Interfaces.MainMenuPresenter;
import kl.cookassistant.Login.LoginActivity;
import cookingAssistant.kevin92.com.R;
import kl.cookassistant.ShoppingList.ShoppingListAcrivity;
import kl.cookassistant.TagsManagerAndSearch.TagsManagerActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Li on 11/1/2016.
 */

public class MainMenuActivity extends AppCompatActivity {
    private Button knownDishesButton;
    private Button unknownDishesButton;
    private Button searchButton;
    private Button shoppingListButton;
    private Button tagManagerButton;
    private Button logOutButton;
    private Button quitButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private MainMenuPresenter presenter;
    private GlobalVars mGV;

    public void navigateToDishList(){
        startActivity(new Intent(this, DisPlayDishesActivity.class));
        finish();
    }
    public void navigateToSearch(){
        mGV.setIngredientList(new ArrayList<Tag>());
        mGV.setMode(TagsManagerMode.Search);
        startActivity(new Intent(this, TagsManagerActivity.class));
        finish();
    }
    public void navigateToShoppingList(){
        startActivity(new Intent(this, ShoppingListAcrivity.class));
        finish();
    }
    public void navigateToTagManager(){
        mGV.setIngredientList(new ArrayList<Tag>());
        mGV.setMode(TagsManagerMode.Manage);
        startActivity(new Intent(this, TagsManagerActivity.class));
        finish();
    }
    public void navigateToLogin(){
        sharedPreferencesEditor.putBoolean(getString(R.string.shared_preferences_is_login), false).commit();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
    public void Quit(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        Long id = sharedPreferences.getLong(getString(R.string.shared_preferences_id), 0);
        String encodedPassword = sharedPreferences.getString(getString(R.string.shared_preferences_password), null);
        String email = sharedPreferences.getString(getString(R.string.shared_preferences_email), null);
        User currentUser = new User(id,encodedPassword, email);
        mGV = GlobalVars.getInstance(currentUser);

        knownDishesButton = (Button) findViewById(R.id.Known_Dishes_button);
        unknownDishesButton = (Button) findViewById(R.id.Unknown_Dishes_Button);
        searchButton = (Button) findViewById(R.id.Search_Button);
        shoppingListButton = (Button) findViewById(R.id.Shopping_List_Button);
        tagManagerButton = (Button) findViewById(R.id.tagManagerButton);
        logOutButton = (Button) findViewById(R.id.Log_Out_Button);
        quitButton = (Button) findViewById(R.id.Quit_Button);

        knownDishesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                presenter.onKnownDishesButtonClicked();
            }
        });
        unknownDishesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                presenter.onUnknownDishesButtonClicked();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                presenter.onSearchButtonClicked();
            }
        });
        shoppingListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                presenter.onShoppingListButtonClicked();
            }
        });
        tagManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onTagManagerButtonClicked();
            }
        });
        logOutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                presenter.onLogOutButtonClicked();
            }
        });
        quitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                presenter.onQuitButtonClicked();
            }
        });

        presenter = new MainMenuPresenterImpl(this);
    }
}

