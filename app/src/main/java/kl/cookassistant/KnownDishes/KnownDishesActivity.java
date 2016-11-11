package kl.cookassistant.KnownDishes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.Interfaces.KnownDishesListPresenter;
import kl.cookassistant.R;

/**
 * Created by Li on 11/11/2016.
 */

public class KnownDishesActivity extends AppCompatActivity {
    private KnownDishesListPresenter presenter;
    List<Dish> data = null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
