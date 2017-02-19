package kl.cookassistant.NewTag;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import cookingAssistant.kevin92.com.R;
import kl.cookassistant.Interfaces.NewTagPresenter;
import kl.cookassistant.TagsManagerAndSearch.TagsManagerActivity;

/**
 * Created by Li on 2/9/2017.
 */

public class NewTagActivity extends AppCompatActivity {
    private EditText newTagName;
    private Button addNewTypeButton;
    private Button okButton;
    private Button cancelButton;
    private ListView newTagListView;
    private NewTagPresenter presenter;
    private int itemHeight = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tag);

        newTagName = (EditText) findViewById(R.id.newTagName);
        addNewTypeButton = (Button) findViewById(R.id.addTypeButton);
        okButton = (Button) findViewById(R.id.newTypeOkbutton);
        cancelButton = (Button) findViewById(R.id.newTypeCancelbutton);
        newTagListView= (ListView) findViewById(R.id.newTagListView);

        presenter = new NewTagPresenterImpl(this);

        addNewTypeButton.setOnClickListener(new OnAddNewButtonClicked());
        okButton.setOnClickListener(new OnOkButtonClicked());
        cancelButton.setOnClickListener(new OnCancelButtonClicked());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, TagsManagerActivity.class));
        finish();
    }

    public class OnAddNewButtonClicked implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            presenter.onAddNewButtonClicked();
        }
    }
    public class OnOkButtonClicked implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            presenter.onOkButtonClicked();
        }
    }
    public class OnCancelButtonClicked implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            presenter.onCancelButtonClicked();
        }
    }

    public EditText getNewTagName(){
        return this.newTagName;
    }
    public ListView getNewTagListView(){
        return this.newTagListView;
    }

    public void setLisViewHeight(int size){
        ViewGroup.LayoutParams params = newTagListView.getLayoutParams();
        params.height = itemHeight*size;
        newTagListView.setLayoutParams(params);
        newTagListView.requestLayout();
    }

    public void navigateToTagsManagerActivity(){
        startActivity(new Intent(NewTagActivity.this , TagsManagerActivity.class));
        finish();
    }


}
