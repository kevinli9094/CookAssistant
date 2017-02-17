package kl.cookassistant.ShoppingList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import cookingAssistant.kevin92.com.R;
import kl.cookassistant.DataModel.ShoppingListItem;
import kl.cookassistant.DataModel.Type;
import kl.cookassistant.Interfaces.ShoppingListPresenter;
import kl.cookassistant.MainMenu.MainMenuActivity;
import kl.cookassistant.TagsManagerAndSearch.TagsManagerActivity;

/**
 * Created by Li on 2/15/2017.
 */

public class ShoppingListAcrivity extends AppCompatActivity {
    private ListView shoppingListListView;
    private Button AddToListButton;
    private EditText shoppingListEditText;
    private ShoppingListPresenterImpl presenter;
    private ShoppingListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        this.AddToListButton = (Button) findViewById(R.id.shoppingList_AddToListButton);
        this.shoppingListEditText = (EditText) findViewById(R.id.shoppingListEditText);
        this.presenter = new ShoppingListPresenterImpl(this);
        this.shoppingListListView = (ListView) findViewById(R.id.shoppingListListView);
        mAdapter = new ShoppingListAdapter(this,R.layout.shopping_list_item,presenter.getData());
        this.shoppingListListView.setAdapter(mAdapter);

        this.AddToListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = shoppingListEditText.getText().toString();
                Long id = presenter.insertNewItem(input);
                if(id > 0){
                    mAdapter.addToData(new ShoppingListItem(input,id,false));
                }
            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainMenuActivity.class));
        finish();
    }

    public class ShoppingListAdapter extends ArrayAdapter<ShoppingListItem> {
        Context context;
        List<ShoppingListItem> data;
        int layoutResourceId;

        public ShoppingListAdapter(Context context, int layoutResourceId,
                                     List<ShoppingListItem> data){
            super(context,layoutResourceId, data);
            this.context=context;
            this.data = data;
            this.layoutResourceId = layoutResourceId;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ItemHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new ItemHolder();
                holder.itemName = (TextView) row.findViewById(R.id.shoppingListTextView);
                holder.deleteButton = (Button) row.findViewById(R.id.shoppingListDeleteButton);
                holder.checkBox = (CheckBox) row.findViewById(R.id.shoppingListCheckBox);
                row.setTag(holder);
            } else {
                holder = (ItemHolder) row.getTag();
            }
            ShoppingListItem item = data.get(position);
            final int currentPosition = position;
            holder.itemName.setText(item.getItemName());
            holder.checkBox.setChecked(item.getIsChecked());
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    data.get(currentPosition).setIsChecked(isChecked);
                    presenter.updateItem(data.get(currentPosition));
                }
            });
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.deleteItem(data.get(currentPosition));
                    data.remove(currentPosition);
                    mAdapter.notifyDataSetChanged();
                }
            });
            return row;

        }

        public void addToData(ShoppingListItem newItem){
            data.add(newItem);
            mAdapter.notifyDataSetChanged();
        }
        private class ItemHolder {
            Button deleteButton;
            TextView itemName;
            CheckBox checkBox;
        }
    }
}
