package kl.cookassistant.Login;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import kl.cookassistant.Interfaces.LoginPresenter;
import kl.cookassistant.R;

/**
 * Created by Li on 10/5/2016.
 */

public class LoginPresenterImpl implements LoginPresenter{
    private LoginActivity context;
    private LoginModel model;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    public LoginPresenterImpl(LoginActivity context){
        this.context = context;
        this.model = new LoginModel(context);
        this.mEmailView = context.getmEmailView();
        this.mPasswordView = context.getmPasswordView();
    }

    public void OnLoginButtonClick(){
        if(mEmailView == null ){
            this.mEmailView = context.getmEmailView();
        }
        if(mPasswordView == null){
            this.mPasswordView = context.getmPasswordView();
        }
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(context.getIdString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(context.getIdString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(context.getIdString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            Long result = model.tryLogin(email, password);
            CharSequence text;
            int duration = Toast.LENGTH_SHORT;
            Toast toast;
            if(result>0){
                text = "Successfully login";
                context.navigateToMainMenu();
            }
            else{
                text = "Fail to login";
            }
            toast = Toast.makeText(context,text, duration);
            toast.show();
        }

    }

    public void OnRegisterButtonClick(){
        CharSequence text;
        int duration = Toast.LENGTH_SHORT;
        Toast toast;
        if(mEmailView == null ){
            this.mEmailView = context.getmEmailView();
        }
        if(mPasswordView == null){
            this.mPasswordView = context.getmPasswordView();
        }
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(context.getIdString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(context.getIdString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(context.getIdString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            text = "Invalid Input";
            toast = Toast.makeText(context,text, duration);
            toast.show();

        } else {
            if(model.tryRegister(email, password)){
                text = "Successfully registered";
            }
            else{
                text = "Fail to register";
            }
            toast = Toast.makeText(context,text, duration);
            toast.show();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


}
