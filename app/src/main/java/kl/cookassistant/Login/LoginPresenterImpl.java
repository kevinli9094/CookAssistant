package kl.cookassistant.Login;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import kl.cookassistant.Interfaces.Presenter;
import kl.cookassistant.R;

/**
 * Created by Li on 10/5/2016.
 */

public class LoginPresenterImpl implements Presenter{
    private LoginActivity context;
    private LoginModel model;
    private UserLoginTask mAuthTask = null;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    public LoginPresenterImpl(LoginActivity context){
        this.context = context;
        this.model = new LoginModel(context);
        this.mEmailView = context.getmEmailView();
        this.mPasswordView = context.getmPasswordView();
    }

    public void OnLoginButtonClick(){
        if (mAuthTask != null) {
            return;
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
            context.setShowProgress(true);
            mAuthTask = new LoginPresenterImpl.UserLoginTask(email, password, true);
            mAuthTask.execute((Void) null);
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

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private boolean login;

        UserLoginTask(String email, String password, boolean login) {
            mEmail = email;
            mPassword = password;
            this.login = login;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            if(login){
                Long result = model.tryLogin(mEmail, mPassword);
                return result > 0;
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            context.setShowProgress(false);

            if (success) {
                context.setFinish();
            } else {
                mPasswordView.setError(context.getIdString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            context.setShowProgress(false);
        }
    }
}
