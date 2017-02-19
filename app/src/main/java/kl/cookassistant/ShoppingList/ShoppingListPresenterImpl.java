package kl.cookassistant.ShoppingList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cookingAssistant.kevin92.com.R;
import kl.cookassistant.DataModel.ShoppingListItem;
import kl.cookassistant.DataModel.User;
import kl.cookassistant.GlobalVars;
import kl.cookassistant.Interfaces.ShoppingListPresenter;
import kl.cookassistant.NewTag.NewTagActivity;
import kl.cookassistant.NewTag.NewTagModel;

/**
 * Created by Li on 2/15/2017.
 */

public class ShoppingListPresenterImpl implements ShoppingListPresenter{
    private ShoppingListAcrivity context;
    private ShoppingListModel model;
    private GlobalVars mGV;
    private ShoppingListAdapter mAdapter;
    private ListView shoppingListListView;

    public ShoppingListPresenterImpl(ShoppingListAcrivity context){
        this.context = context;
        mGV = GlobalVars.getInstance();
        model = new ShoppingListModel(context, mGV.getCurrentUser());
        mAdapter = new ShoppingListAdapter(context,R.layout.shopping_list_item,getData());
        this.shoppingListListView = context.getShoppingListListView();
        shoppingListListView.setAdapter(mAdapter);
    }

    private List<ShoppingListItem> getData(){
        return model.getData();
    }
    private Long insertNewItem(String itemName){
        return model.insertNewItem(itemName);
    }
    private boolean deleteItem(ShoppingListItem item){
        return model.deleteItem(item);
    }
    private boolean updateItem(ShoppingListItem item){
        return model.updateItem(item);
    }
    public void onAddToListButton(){
        String input = context.getShoppingListEditText().getText().toString();
        Long id = insertNewItem(input);
        if(id > 0){
            mAdapter.addToData(new ShoppingListItem(input,id,false));
        }
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
                    updateItem(data.get(currentPosition));
                }
            });
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(data.get(currentPosition));
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
