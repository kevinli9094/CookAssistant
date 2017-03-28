package kl.cookassistant.Login;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import kl.cookassistant.Interfaces.LoginPresenter;
import kl.cookassistant.MainMenu.MainMenuActivity;
import cookingAssistant.kevin92.com.R;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, GoogleApiClient.OnConnectionFailedListener {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;


    // UI references.
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private GoogleApiClient gac;
    private LoginPresenter presenter;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    public AutoCompleteTextView getmEmailView(){
        return (AutoCompleteTextView) findViewById(R.id.email);
    }
    public EditText getmPasswordView(){
        return (EditText) findViewById(R.id.password);
    }
    public String getIdString(int id){
        return getString(id);
    }
    public void navigateToMainMenu(){
        startActivity(new Intent(this, MainMenuActivity.class));
        finish();
    }
    public void saveLoginInfo(long id, String email, String encodedPassword){
        sharedPreferencesEditor.putString(getString(R.string.shared_preferences_email), email)
                .putString(getString(R.string.shared_preferences_password), encodedPassword)
                .putLong(getString(R.string.shared_preferences_id), id).commit();
    }
    public void rememberMeCheck(){
        CheckBox rememberMeCheckBox = (CheckBox) findViewById(R.id.rememberMeCheckBox);
        if(rememberMeCheckBox.isChecked()){
            sharedPreferencesEditor.putBoolean(getString(R.string.shared_preferences_is_login), true).commit();
        }else{
            sharedPreferencesEditor.putBoolean(getString(R.string.shared_preferences_is_login), false).commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        sharedpreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        sharedPreferencesEditor = sharedpreferences.edit();
        boolean isLogin = sharedpreferences.getBoolean(getString(R.string.shared_preferences_is_login),false);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gac = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        gac.connect();
        if(isLogin){
            if(sharedpreferences.getString(getString(R.string.sign_in_method), null) == getString(R.string.sign_in_method_google)){
                silentLogin();
            }
            else{
                navigateToMainMenu();
            }
        }
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    presenter.OnLoginButtonClick();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        SignInButton googleSignInButton = (SignInButton) findViewById(R.id.google_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferencesEditor.putString(getString(R.string.sign_in_method), getString(R.string.sign_in_method_email)).commit();
                presenter.OnLoginButtonClick();
            }
        });
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnRegisterButtonClick();
            }
        });
        googleSignInButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                sharedPreferencesEditor.putString(getString(R.string.sign_in_method), getString(R.string.sign_in_method_google)).commit();
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(gac);
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });
        presenter = new LoginPresenterImpl(this);
    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!handleSignInResult:" + result.getStatus().toString());
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String ID = acct.getId();
            long rowId = presenter.tryGoogleLogin(ID);
            saveLoginInfo(rowId,ID,getString(R.string.sign_in_method_google));
            rememberMeCheck();
            navigateToMainMenu();
        } else {
            // Signed out, show unauthenticated UI.
            Toast toast = Toast.makeText(this,"Fail to login", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    // [END handleSignInResult]

    public void silentLogin() {
        OptionalPendingResult<GoogleSignInResult> pendingResult = Auth.GoogleSignInApi.silentSignIn(gac);
        if (pendingResult != null) {
            handleGooglePendingResult(pendingResult);
        } else {
            //no result from silent login. Possibly display the login page again
        }
    }

    private void handleGooglePendingResult(OptionalPendingResult<GoogleSignInResult> pendingResult) {
        if (pendingResult.isDone()) {
            // There's immediate result available.
            GoogleSignInResult signInResult = pendingResult.get();
            onSilentSignInCompleted(signInResult);
        } else {
            // There's no immediate result ready,  waits for the async callback.
            pendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult signInResult) {
                    onSilentSignInCompleted(signInResult);
                }
            });
        }
    }

    private void onSilentSignInCompleted(GoogleSignInResult signInResult) {
        GoogleSignInAccount signInAccount = signInResult.getSignInAccount();
        if (signInAccount != null) {
            saveLoginInfo(sharedpreferences.getLong("id",0), sharedpreferences.getString("email",null),sharedpreferences.getString("password", null));
            navigateToMainMenu();
        }
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                                                                     .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }
}

