package kl.cookassistant.NewTag;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.List;

import cookingAssistant.kevin92.com.R;
import kl.cookassistant.DataModel.Type;
import kl.cookassistant.DataModel.User;
import kl.cookassistant.GlobalVars;
import kl.cookassistant.Interfaces.NewTagPresenter;

/**
 * Created by Li on 2/9/2017.
 */

public class NewTagPresenterImpl implements NewTagPresenter{
    private NewTagActivity context;
    private NewTagModel model;
    private GlobalVars mGV;
    private User currentUser;
    private NewTagTypeListAdapter mAdapter;
    private ListView newTagListView;
    private int currentSelectedTypePosition;
    private RadioButton currentRadioButton;

    public NewTagPresenterImpl(NewTagActivity context){
        this.context = context;
        mGV = GlobalVars.getInstance();
        this.currentUser = mGV.getCurrentUser();
        this.model = new NewTagModel(context, currentUser);
        this.newTagListView = context.getNewTagListView();
        mAdapter = new NewTagTypeListAdapter(context,R.layout.single_radio_item,getAllTypes());
        newTagListView.setAdapter(mAdapter);
        context.setLisViewHeight(mAdapter.getDataSize());
        currentSelectedTypePosition = -1;
    }

    private List<Type> getAllTypes(){
        return model.getAllTypes();
    }

    private boolean createNewTag(String name, Type type){
        return model.createNewTag(name,type);
    }

    private Long insertNewType(String name){
        return model.inseartNewType(name);
    }

    public void onOkButtonClicked(){
        createNewTag(context.getNewTagName().getText().toString(), currentSelectedTypePosition == -1? null:getAllTypes().get(currentSelectedTypePosition));
        context.navigateToTagsManagerActivity();
    }

    public void onCancelButtonClicked(){
        context.navigateToTagsManagerActivity();
    }

    public void onAddNewButtonClicked(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("new type");

        final EditText input = new EditText(context);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newTypeNameStr = input.getText().toString();
                insertNewType(newTypeNameStr);
                mAdapter.notifyDataSetChanged();
                context.setLisViewHeight(mAdapter.getDataSize());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public class NewTagTypeListAdapter extends ArrayAdapter<Type> {
        Context context;
        List<Type> data;
        int layoutResourceId;

        public NewTagTypeListAdapter(Context context, int layoutResourceId,
                                     List<Type> data){
            super(context,layoutResourceId, data);
            this.context=context;
            this.data = data;
            this.layoutResourceId = layoutResourceId;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            RadioButton holder = null;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = (RadioButton) row.findViewById(R.id.typeNameRadioButton);
                row.setTag(holder);
            } else {
                holder = (RadioButton) row.getTag();
            }
            Type type = data.get(position);
            holder.setText(type.getName());
            holder.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(currentRadioButton != null){
                        currentRadioButton.setChecked(false);
                    }
                    currentRadioButton = (RadioButton) v;
                    currentSelectedTypePosition = position;
                }
            });
            return row;
        }
        public int getDataSize(){
            return data.size();
        }
    }
}
