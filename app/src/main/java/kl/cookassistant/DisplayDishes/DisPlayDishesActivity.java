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
import kl.cookassistant.MainMenu.MainMenuActivity;

/**
 * Created by Li on 11/11/2016.
 */

public class DisPlayDishesActivity extends AppCompatActivity {
    private ListView dishList;
    private DisplayDishesListPresenterImpl presenter;
    List<Dish> data;
    dishListCustomAdapter mAdapter;
    FloatingActionButton floatingAddButton;
    GlobalVars mGV;
    boolean onSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_dishes_list);
        presenter = new DisplayDishesListPresenterImpl(this);
        mGV = GlobalVars.getInstance();
        onSearch = mGV.getOnSearch();
        mGV.setOnSearch(false);
        floatingAddButton = (FloatingActionButton) findViewById(R.id.floatingAddButton);
        if(onSearch){
            List<Tag> ingredientList = mGV.getIngredientList();
            this.data = presenter.getSearchResult(ingredientList);
            floatingAddButton.setVisibility(View.GONE);
        }
        else{
            this.data = presenter.getDishes();
            floatingAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean createMode = true;
                    GlobalVars mGV = GlobalVars.getInstance();
                    mGV.setCreateMode(createMode);
                    startActivity(new Intent(DisPlayDishesActivity.this, DishEditorActivity.class));
                    finish();
                }
            });
        }
        mAdapter = new dishListCustomAdapter(DisPlayDishesActivity.this, R.layout.listitem, data);
        dishList = (ListView) findViewById(R.id.listView);
        dishList.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainMenuActivity.class));
        finish();
    }

    public class dishListCustomAdapter extends ArrayAdapter<Dish> {
        Context context;
        int layoutResourceId;
        List<Dish> data = new ArrayList<Dish>();

        public dishListCustomAdapter(Context context, int layoutResourceId,
                                     List<Dish> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            DishHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new DishHolder();
                holder.DishName = (TextView) row.findViewById(R.id.itemText);
                holder.btnEdit = (Button) row.findViewById(R.id.itemViewButtom);
                holder.btnDelete = (Button) row.findViewById(R.id.itemDeleteButtom);
                row.setTag(holder);
            } else {
                holder = (DishHolder) row.getTag();
            }
            Dish singleDish = data.get(position);
            holder.DishName.setText(singleDish.getName());
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    boolean createMode = false;
                    int position = dishList.getPositionForView((View)v.getParent());
                    mGV.setCreateMode(createMode);
                    mGV.setCurrentDish(data.get(position));
                    startActivity(new Intent(DisPlayDishesActivity.this, DishEditorActivity.class));
                    finish();
                }
            });
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final int position = dishList.getPositionForView((View)v.getParent());
                    String dishName = data.get(position).getName();
                    AlertDialog.Builder adb = new AlertDialog.Builder(DisPlayDishesActivity.this);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete dish: " + dishName + "?");
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            //Todo：redo delete!!!!!! this is not the right way to delete a dish from a user(it is fine for now)
                            if(presenter.removeDish(position)){
                                data.remove(position);
                                mAdapter.notifyDataSetChanged();
                            }else{
                                Toast.makeText(context, "Fail to delete selected dish.",
                                        Toast.LENGTH_LONG).show();
                            }
                       }
                    });
                    adb.show();
                }
            });
            return row;

        }

        class DishHolder {
            TextView DishName;
            Button btnEdit;
            Button btnDelete;
        }
    }
}
