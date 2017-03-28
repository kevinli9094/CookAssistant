package kl.cookassistant.DisplayDishes;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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

import cookingAssistant.kevin92.com.R;
import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.DataModel.Tag;
import kl.cookassistant.DataModel.User;
import kl.cookassistant.GlobalVars;
import kl.cookassistant.Interfaces.DisplayDishesListPresenter;

/**
 * Created by Li on 11/11/2016.
 */

public class DisplayDishesListPresenterImpl implements DisplayDishesListPresenter {
    private DisPlayDishesActivity context;
    private DisplayDishesModel model;
    List<Dish> data;
    private User user;
    private GlobalVars mGV;
    boolean onSearch;
    dishListCustomAdapter mAdapter;

    public DisplayDishesListPresenterImpl(DisPlayDishesActivity context){
        this.context = context;
        this.mGV = GlobalVars.getInstance();
        this.user = mGV.getCurrentUser();
        this.model = new DisplayDishesModel(context,user);
        this.data = model.getDishes(mGV.getKnown());
        mAdapter = new dishListCustomAdapter(context, R.layout.listitem, data);
        context.getDishListListView().setAdapter(mAdapter);
    }

    public List<Dish> getDishes(){
        if(data == null){
            data = model.getDishes(mGV.getKnown());
        }
        return data;
    }

    public List<Dish> getSearchResult(List<Tag> ingredientList){
        if(data == null){
            data =  model.search(ingredientList, mGV.getKnown());
        }
        return data;
    }

    public boolean removeDish(int position){
        boolean rs = model.deleteDish(data.get(position));
        return rs;
    }

    public void isOnSearch(){
        onSearch = mGV.getOnSearch();
        mGV.setOnSearch(false);
        if(onSearch){
            List<Tag> ingredientList = mGV.getIngredientList();
            this.data = getSearchResult(ingredientList);
            context.hideFloatingAddButton();
        }
        else{
            this.data = getDishes();
        }
    }

    public void onFloatingAddButtonClicked(){
        boolean createMode = true;
        mGV.setCreateMode(createMode);
        context.navigateToDishEditorActivity();
    }

    public void onBackPressed(){
        if(onSearch){
            context.navigateToTagsManagerAndSearchActivity();
        }
        else{
            context.navigateToMainMenuActivity();
        }
    }

    public class dishListCustomAdapter extends ArrayAdapter<Dish> {
        DisPlayDishesActivity context;
        int layoutResourceId;
        List<Dish> data = new ArrayList<Dish>();
        ListView dishList;

        public dishListCustomAdapter(Context context, int layoutResourceId,
                                     List<Dish> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = (DisPlayDishesActivity)context;
            this.data = data;
            this.dishList = this.context.getDishListListView();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            DishHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = (context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new DishHolder();
                holder.DishName = (TextView) row.findViewById(R.id.itemText);
                holder.btnView = (Button) row.findViewById(R.id.itemViewButtom);
                holder.btnDelete = (Button) row.findViewById(R.id.itemDeleteButtom);
                row.setTag(holder);
            } else {
                holder = (DishHolder) row.getTag();
            }
            Dish singleDish = data.get(position);
            holder.DishName.setText(singleDish.getName());
            holder.btnView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    boolean createMode = false;
                    boolean viewMode = true;
                    int position = dishList.getPositionForView((View)v.getParent());
                    mGV.setCreateMode(createMode);
                    mGV.setIsView(viewMode);
                    mGV.setCurrentDish(data.get(position));
                    context.navigateToDishEditorActivity();
                }
            });
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final int position = dishList.getPositionForView((View)v.getParent());
                    String dishName = data.get(position).getName();
                    AlertDialog.Builder adb = new AlertDialog.Builder(context);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete dish: " + dishName + "?");
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            if(removeDish(position)){
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
            Button btnView;
            Button btnDelete;
        }
    }

}
