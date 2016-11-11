package kl.cookassistant.MainMenu;
import kl.cookassistant.Login.LoginActivity;
import kl.cookassistant.Login.LoginPresenterImpl;
import kl.cookassistant.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private MainMenuPresenterImpl presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        // Set up the login form.
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

    public void navigateToKnowDishes(){}
    public void navigateToUnknowDishes(){}
    public void navigateToSearch(){}
    public void navigateToShoppingList(){}
    public void navigateToLogin(){
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

