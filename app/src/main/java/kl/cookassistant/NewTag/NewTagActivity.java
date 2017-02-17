package kl.cookassistant.NewTag;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.List;

import cookingAssistant.kevin92.com.R;
import kl.cookassistant.DataModel.Type;
import kl.cookassistant.TagsManagerAndSearch.TagsManagerActivity;

/**
 * Created by Li on 2/9/2017.
 */

public class NewTagActivity extends AppCompatActivity {
    private NewTagPresenterImpl presenter;
    private EditText newTagName;
    private Button addNewTypeButton;
    private Button okButton;
    private Button cancelButton;
    private ListView newTagListView;
    private RadioButton currentRadioButton;
    private int currentPosition;
    private NewTagTypeListAdapter mAdapter;
    private int itemHeight = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tag);
        presenter = new NewTagPresenterImpl(this);
        newTagName = (EditText) findViewById(R.id.newTagName);
        addNewTypeButton = (Button) findViewById(R.id.addTypeButton);
        okButton = (Button) findViewById(R.id.newTypeOkbutton);
        cancelButton = (Button) findViewById(R.id.newTypeCancelbutton);
        newTagListView= (ListView) findViewById(R.id.newTagListView);
        addNewTypeButton.setOnClickListener(new OnAddNewButtonClicked());
        okButton.setOnClickListener(new OnOkButtonClicked());
        cancelButton.setOnClickListener(new OnCancelButtonClicked());
        mAdapter = new NewTagTypeListAdapter(this,R.layout.single_radio_item,presenter.getAllTypes());
        newTagListView.setAdapter(mAdapter);
        ViewGroup.LayoutParams params = newTagListView.getLayoutParams();
        params.height = itemHeight*presenter.getAllTypes().size();
        newTagListView.setLayoutParams(params);
        newTagListView.requestLayout();
        currentPosition = -1;
    }

    public void onBackPressed() {
        startActivity(new Intent(this, TagsManagerActivity.class));
        finish();
    }

    public class OnAddNewButtonClicked implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(NewTagActivity.this);
            builder.setTitle("new type");

            final EditText input = new EditText(NewTagActivity.this);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newTypeNameStr = input.getText().toString();
                    presenter.insertNewType(newTypeNameStr);
                    mAdapter.notifyDataSetChanged();
                    ViewGroup.LayoutParams params = newTagListView.getLayoutParams();
                    params.height = itemHeight*presenter.getAllTypes().size();
                    newTagListView.setLayoutParams(params);
                    newTagListView.requestLayout();
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
    }

    public class OnOkButtonClicked implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            presenter.createNewTag(newTagName.getText().toString(), currentPosition == -1? null:presenter.getAllTypes().get(currentPosition));
            returnToPreviousPage();
        }
    }

    public class OnCancelButtonClicked implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            returnToPreviousPage();
        }
    }

    private void returnToPreviousPage(){
        startActivity(new Intent(NewTagActivity.this , TagsManagerActivity.class));
        finish();
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
                    currentPosition = position;
                }
            });
            return row;

        }
    }
}
