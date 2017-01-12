package kl.cookassistant.MainMenu;
import kl.cookassistant.DisplayDishes.DisPlayDishesActivity;
import kl.cookassistant.Login.LoginActivity;
import cookingAssistant.kevin92.com.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Li on 11/1/2016.
 */

public class MainMenuActivity extends AppCompatActivity {
    private Button knownDishesButton;
    private Button unknownDishesButton;
    private Button searchButton;
    private Button shoppingListButton;
    private Button logOutButton;
    private Button quitButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private MainMenuPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        sharedPreferences = getSharedPreferences("CookingAssistant", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        knownDishesButton = (Button) findViewById(R.id.Known_Dishes_button);
        unknownDishesButton = (Button) findViewById(R.id.Unknown_Dishes_Button);
        searchButton = (Button) findViewById(R.id.Search_Button);
        shoppingListButton = (Button) findViewById(R.id.Shopping_List_Button);
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

    public void navigateToKnowDishes(){
        startActivity(new Intent(this, DisPlayDishesActivity.class));
        finish();
    }
    public void navigateToUnknowDishes(){}
    public void navigateToSearch(){}
    public void navigateToShoppingList(){}
    public void navigateToLogin(){
        sharedPreferencesEditor.putBoolean("isLogin", false).commit();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
    public void Quit(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

