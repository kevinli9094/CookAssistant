package kl.cookassistant.DisplayDishes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

import kl.cookassistant.DataModel.Dish;
import cookingAssistant.kevin92.com.R;

/**
 * Created by Li on 11/11/2016.
 */

public class DisPlayDishesActivity extends AppCompatActivity {
    private ListView dishList;
    private DisplayDishesListPresenterImpl presenter;
    ArrayList<Dish> data;
    dishCustomAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaydisheslist);
        presenter = new DisplayDishesListPresenterImpl(this);
        this.data = new ArrayList<>(presenter.getDishes());
        mAdapter = new dishCustomAdapter(DisPlayDishesActivity.this, R.layout.listitem, data);
    }

    public class dishCustomAdapter extends ArrayAdapter<Dish> {
        Context context;
        int layoutResourceId;
        ArrayList<Dish> data = new ArrayList<Dish>();

        public dishCustomAdapter(Context context, int layoutResourceId,
                                 ArrayList<Dish> data) {
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
                    Toast.makeText(context, "Edit button Clicked",
                            Toast.LENGTH_LONG).show();
                }
            });
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Toast.makeText(context, "Delete button Clicked",
                            Toast.LENGTH_LONG).show();
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
