package kl.cookassistant.ShoppingList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import cookingAssistant.kevin92.com.R;
import kl.cookassistant.DataModel.ShoppingListItem;
import kl.cookassistant.Interfaces.ShoppingListPresenter;
import kl.cookassistant.MainMenu.MainMenuActivity;

/**
 * Created by Li on 2/15/2017.
 */

public class ShoppingListAcrivity extends AppCompatActivity {
    private ListView shoppingListListView;
    private Button addToListButton;
    private EditText shoppingListEditText;
    private ShoppingListPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        this.addToListButton = (Button) findViewById(R.id.shoppingList_AddToListButton);
        this.shoppingListEditText = (EditText) findViewById(R.id.shoppingListEditText);
        this.shoppingListListView = (ListView) findViewById(R.id.shoppingListListView);
        this.presenter = new ShoppingListPresenterImpl(this);
        this.addToListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               presenter.onAddToListButton();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainMenuActivity.class));
        finish();
    }

    public ListView getShoppingListListView(){
        return this.shoppingListListView;
    }
    public EditText getShoppingListEditText(){
        return this.shoppingListEditText;
    }
}
