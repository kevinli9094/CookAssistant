package kl.cookassistant.TagsManagerAndSearch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import cookingAssistant.kevin92.com.R;
import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.DataModel.Tag;
import kl.cookassistant.DataModel.TagsManagerMode;
import kl.cookassistant.DataModel.Type;
import kl.cookassistant.DataModel.User;
import kl.cookassistant.DishEditor.DishEditorActivity;
import kl.cookassistant.DisplayDishes.DisPlayDishesActivity;
import kl.cookassistant.GlobalVars;
import kl.cookassistant.Interfaces.TagsManagerPresenter;
import kl.cookassistant.NewTag.NewTagActivity;

/**
 * Created by Li on 1/28/2017.
 */

public class TagsManagerPresenterImpl implements TagsManagerPresenter{
    private TagsManagerModel model;
    private TagsManagerActivity context;
    private TagsManagerAndSearchListAdapter mAdapter;
    private ExpandableListView expandableListView;
    private GlobalVars mGV;
    private User currentUser;
    private TagsManagerMode mode;

    public TagsManagerPresenterImpl(TagsManagerActivity context){
        this.context = context;
        this.mGV = GlobalVars.getInstance();
        this.currentUser = mGV.getCurrentUser();
        this.model = new TagsManagerModel(context, currentUser);
        this.mode = mGV.getMode();
        context.setMode(mode);
        this.expandableListView = context.getExpandableListView();
        mAdapter = new TagsManagerAndSearchListAdapter(context, new ArrayList<Type>(getExpandableData().keySet()), getExpandableData());
        this.expandableListView.setAdapter(mAdapter);
    }

    private boolean deleteTag(Tag tag){
        return model.deleteTag(tag);
    }

    private LinkedHashMap<Type, List<Tag>> getExpandableData() {
        return model.getExpandableData();
    }

    public void onBackPressed(){
        if(mode == TagsManagerMode.Modify){
            context.navigateToDishEditorActivity();
        }
        else{
            context.navigateToMainMenuActivity();
        }
    }

    public void onAddNewIngredientsClicked(){
        mGV.setIngredientList(mAdapter.allSelectedTag());
        context.navigateToNewTagActivity();
    }
    public void onDeleteSelectedClicked(){
        mAdapter.deleteSelectedTag();
    }
    public void onCombineSelectedClicked(){
        //TODO:combine selected tag
        mGV.setIngredientList(mAdapter.allSelectedTag());
        mGV.setOnCombineTag(true);
        context.navigateToNewTagActivity();

    }
    public void onAddToDishClicked(){
        mGV.setBackFromTagManager(true);
        Dish currentDish = mGV.getCurrentDish();
        currentDish.setIngredients(mAdapter.allSelectedTag());
        mGV.setCurrentDish(currentDish);
        context.navigateToDishEditorActivity();
    }
    public void onSearchKnownClicked(){
        mGV.setKnown(true);
        mGV.setIngredientList(mAdapter.allSelectedTag());
        mGV.setOnSearch(true);
        context.navigateToDisplayDishesActivity();
    }
    public void onSearchUnknownClicked(){
        mGV.setKnown(false);
        mGV.setIngredientList(mAdapter.allSelectedTag());
        mGV.setOnSearch(true);
        context.navigateToDisplayDishesActivity();
    }

    public class TagsManagerAndSearchListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private List<Type> expandableListTitle;
        private LinkedHashMap<Type, List<Tag>> expandableListDetail;
        HashSet<Tag> currentSelectedIngredients;


        public TagsManagerAndSearchListAdapter(Context context, List<Type> expandableListTitle,
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
                deleteTag(tag);
                i.remove();
            }
            this.expandableListDetail = getExpandableData();
            mAdapter.notifyDataSetChanged();
        }

        private class ItemHolder{
            CheckBox checkBox1;
            CheckBox checkBox2;
            CheckBox checkBox3;
        }
    }
}
