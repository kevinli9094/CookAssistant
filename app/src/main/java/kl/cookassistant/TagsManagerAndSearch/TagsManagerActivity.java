package kl.cookassistant.TagsManagerAndSearch;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import cookingAssistant.kevin92.com.R;
import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.DataModel.Tag;
import kl.cookassistant.DataModel.TagsManagerMode;
import kl.cookassistant.DataModel.Type;
import kl.cookassistant.DishEditor.DishEditorActivity;
import kl.cookassistant.DisplayDishes.DisPlayDishesActivity;
import kl.cookassistant.GlobalVars;
import kl.cookassistant.MainMenu.MainMenuActivity;
import kl.cookassistant.NewTag.NewTagActivity;

/**
 * Created by Li on 1/28/2017.
 */

public class TagsManagerActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    CustomExpandableListAdapter mAdapter;
    Button button1;
    Button button2;
    Button button3;
    TagsManagerPresenterImpl presenter;
    GlobalVars mGV;
    TagsManagerMode mode;
    int buttonHeight = 45;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagsmanager_search);
        presenter = new TagsManagerPresenterImpl(this);
        mGV = GlobalVars.getInstance();
        mode = mGV.getMode();
        expandableListView = (ExpandableListView) findViewById(R.id.ingredientExpandableList);
        button1 = (Button) findViewById(R.id.tagManagerButton1);
        button2 = (Button) findViewById(R.id.tagManagerButton2);
        button3 = (Button) findViewById(R.id.tagManagerButton3);
        setMode(mode);
        mAdapter = new CustomExpandableListAdapter(this, new ArrayList<Type>(presenter.getExpandableData().keySet()), presenter.getExpandableData());
        expandableListView.setAdapter(mAdapter);
    }


    @Override
    public void onBackPressed() {
        if(mode == TagsManagerMode.Modify){
            startActivity(new Intent(this, DisPlayDishesActivity.class));
        }
        else{
            startActivity(new Intent(this, MainMenuActivity.class));
        }
        finish();
    }

    private void setMode(TagsManagerMode mode){
        if(mode == TagsManagerMode.Manage){
            button1.setText("add new ingredients");
            button1.setOnClickListener(new OnAddNewIngredientsClicked());
            button2.setText("delete selected");
            button2.setOnClickListener(new OnDeleteSelectedClicked());
            button3.setText("combine selected into one");
            button3.setOnClickListener(new OnCombineSelectedClicked());
        }
        else if(mode == TagsManagerMode.Modify){
            button1.setText("add new ingredients");
            button1.setOnClickListener(new OnAddNewIngredientsClicked());
            button2.setText("add selected to dish");
            button2.setOnClickListener(new OnAddToDishClicked());
            button3.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = expandableListView.getLayoutParams();
            params.height = params.height + buttonHeight;
            expandableListView.setLayoutParams(params);
            expandableListView.requestLayout();
        }
        else if(mode == TagsManagerMode.Search){
            button1.setText("search known");
            button1.setOnClickListener(new OnSearchKnownClicked());
            button2.setText("search unknown");
            button2.setOnClickListener(new OnSearchUnknownClicked());
            button3.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = expandableListView.getLayoutParams();
            params.height = params.height + buttonHeight;
            expandableListView.setLayoutParams(params);
            expandableListView.requestLayout();
        }
    }

    private class OnAddNewIngredientsClicked implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            mGV.setIngredientList(mAdapter.allSelectedTag());
            startActivity(new Intent(TagsManagerActivity.this, NewTagActivity.class));
            finish();
        }
    }

    private class OnDeleteSelectedClicked implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
            mAdapter.deleteSelectedTag();
        }
    }

    private class OnCombineSelectedClicked implements  View.OnClickListener{
        @Override
        public void onClick(View v) {

        }
    }
    private class OnAddToDishClicked implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
            mGV.setBackFromTagManager(true);
            Dish currentDish = mGV.getCurrentDish();
            currentDish.setIngredients(mAdapter.allSelectedTag());
            mGV.setCurrentDish(currentDish);
            startActivity(new Intent(TagsManagerActivity.this, DishEditorActivity.class));
            finish();
        }
    }

    private class OnSearchKnownClicked implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
            mGV.setKnown(true);
            mGV.setIngredientList(mAdapter.allSelectedTag());
            mGV.setOnSearch(true);
            startActivity(new Intent(TagsManagerActivity.this, DisPlayDishesActivity.class));
            finish();
        }
    }

    private class OnSearchUnknownClicked implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
            mGV.setKnown(false);
            mGV.setIngredientList(mAdapter.allSelectedTag());
            mGV.setOnSearch(true);
            startActivity(new Intent(TagsManagerActivity.this, DisPlayDishesActivity.class));
            finish();
        }
    }

    public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private List<Type> expandableListTitle;
        private LinkedHashMap<Type, List<Tag>> expandableListDetail;
        HashSet<Tag> currentSelectedIngredients;


        public CustomExpandableListAdapter(Context context, List<Type> expandableListTitle,
                                           LinkedHashMap<Type, List<Tag>> expandableListDetail) {
            this.context = context;
            this.expandableListTitle = expandableListTitle;
            this.expandableListDetail = expandableListDetail;
            currentSelectedIngredients = new HashSet<>(mGV.getIngredientList());
        }


        @Override
        public Object getChild(int listPosition, int expandedListPosition) {
            List<Tag> rs = new ArrayList<Tag>();
            List<Tag> allTagsWithSelectedType= this.expandableListDetail.get(this.expandableListTitle.get(listPosition));
            int length = allTagsWithSelectedType.size();
            if(expandedListPosition*3 < length){
                rs.add(allTagsWithSelectedType.get(expandedListPosition*3));
            }
            if(expandedListPosition*3 + 1 < length){
                rs.add(allTagsWithSelectedType.get(expandedListPosition*3 + 1));
            }
            if(expandedListPosition*3 + 2 < length){
                rs.add(allTagsWithSelectedType.get(expandedListPosition*3 + 2));
            }
            return rs;
        }

        @Override
        public long getChildId(int listPosition, int expandedListPosition) {
            return expandedListPosition;
        }

        @Override
        public View getChildView(int listPosition, final int expandedListPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            final List<Tag> tags = (List<Tag>) getChild(listPosition, expandedListPosition);
            final int mListPosition = listPosition;
            ItemHolder holder;
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.tags_list_item, parent,false);
                holder = new ItemHolder();
                holder.checkBox1 = (CheckBox) convertView
                        .findViewById(R.id.tagCheckBox1);
                holder.checkBox2 = (CheckBox) convertView
                        .findViewById(R.id.tagCheckBox2);
                holder.checkBox3 = (CheckBox) convertView
                        .findViewById(R.id.tagCheckBox3);
                convertView.setTag(holder);

            }else{
                holder = (ItemHolder) convertView.getTag();
            }
//checkbox1
            if(tags.size()>0){
                holder.checkBox1.setVisibility(View.VISIBLE);
                holder.checkBox1.setText(tags.get(0).getName());
                holder.checkBox1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(((CheckBox)v).isChecked()){
                            currentSelectedIngredients.add(expandableListDetail.get(expandableListTitle.get(mListPosition)).get(expandedListPosition*3));
                        }
                        else{
                            currentSelectedIngredients.remove(expandableListDetail.get(expandableListTitle.get(mListPosition)).get(expandedListPosition*3));
                        }
                    }
                });
                if(currentSelectedIngredients.contains(tags.get(0))){
                    holder.checkBox1.setChecked(true);
                }
                else{
                    holder.checkBox1.setChecked(false);
                }
            }
            else{
                holder.checkBox1.setVisibility(View.GONE);
            }
//checkbox2
            if(tags.size()>1){
                holder.checkBox2.setVisibility(View.VISIBLE);
                holder.checkBox2.setText(tags.get(1).getName());
                holder.checkBox2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(((CheckBox)v).isChecked()){
                            currentSelectedIngredients.add(expandableListDetail.get(expandableListTitle.get(mListPosition)).get(expandedListPosition*3+1));
                        }
                        else{
                            currentSelectedIngredients.remove(expandableListDetail.get(expandableListTitle.get(mListPosition)).get(expandedListPosition*3+1));
                        }
                    }
                });
                if(currentSelectedIngredients.contains(tags.get(1))){
                    holder.checkBox2.setChecked(true);
                }else{
                    holder.checkBox2.setChecked(false);
                }
            }
            else{
                holder.checkBox2.setVisibility(View.GONE);
            }
//checkbox3
            if(tags.size()>2){
                holder.checkBox3.setVisibility(View.VISIBLE);
                holder.checkBox3.setText(tags.get(2).getName());
                holder.checkBox3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(((CheckBox)v).isChecked()){
                            currentSelectedIngredients.add(expandableListDetail.get(expandableListTitle.get(mListPosition)).get(expandedListPosition*3+2));
                        }
                        else{
                            currentSelectedIngredients.remove(expandableListDetail.get(expandableListTitle.get(mListPosition)).get(expandedListPosition*3+2));
                        }
                    }
                });
                if(currentSelectedIngredients.contains(tags.get(2))){
                    holder.checkBox3.setChecked(true);
                }
                else{
                    holder.checkBox3.setChecked(false);
                }
            }
            else{
                holder.checkBox3.setVisibility(View.GONE);
            }
            return convertView;
        }

        @Override
        public int getChildrenCount(int listPosition) {
            return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                    .size();
        }

        @Override
        public Object getGroup(int listPosition) {
            return this.expandableListTitle.get(listPosition);
        }

        @Override
        public int getGroupCount() {
            return this.expandableListTitle.size();
        }

        @Override
        public long getGroupId(int listPosition) {
            return listPosition;
        }

        @Override
        public View getGroupView(int listPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            Type listType = (Type) getGroup(listPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.expandable_goup_item, null);
            }
            TextView listTitleTextView = (TextView) convertView
                    .findViewById(R.id.groupNameTextView);
            listTitleTextView.setText(listType.getName());
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int listPosition, int expandedListPosition) {
            return true;
        }

        public List<Tag> allSelectedTag(){
            List<Tag> selectedTags = new ArrayList<>();
            for (Tag tag : currentSelectedIngredients) {
                selectedTags.add(tag);
            }
            return selectedTags;
        }

        public void deleteSelectedTag(){
            Iterator<Tag> i = currentSelectedIngredients.iterator();
            Tag tag;
            while(i.hasNext()){
                tag = i.next();
                presenter.deleteTag(tag);
                i.remove();
            }
            this.expandableListDetail = presenter.getExpandableData();
            mAdapter.notifyDataSetChanged();
        }

        private class ItemHolder{
            CheckBox checkBox1;
            CheckBox checkBox2;
            CheckBox checkBox3;
        }
    }

}
