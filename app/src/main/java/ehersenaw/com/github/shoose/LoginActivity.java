package ehersenaw.com.github.shoose;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via id/password.
 * NAVER login API reference: https://developers.naver.com/docs/login/android/
 *
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final String TAG = "network_post check";
    private static String signUpResult = "";
    public static Activity _Login_Activity; /*To terminate this activity after moves to other activity*/
    public static final int tab_sig = 1001; /*Signal code for launching "TabActivity"*/
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo1234:hello", "bar5678:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    // Customized UI.
    private AutoCompleteTextView mIDView;

    // For testing server connection.
    private TextView tv_outPut;

    // Naver OAuth
    private static OAuthLogin mOAuthLoginInstance;
    private OAuthLoginButton mOAuthLoginButton;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        _Login_Activity = LoginActivity.this; /*To terminate this activity, finish() with this value*/

        // Set up the login form.
        initData();


        setContentView(R.layout.activity_login);

        /**
         * For testing server connection.
         */
        /*
        // Reference of widget
        tv_outPut = (TextView) findViewById(R.id.tv_outPut);
        // Set URL
        String url = "http://13.125.41.85:3000/api/usr/signup";

        // Do HttpURLConnection with AsyncTask.
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();
        */
        /**
         * continue from below /* part.
         */

        // mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mIDView = (AutoCompleteTextView) findViewById(R.id.ID);
        // populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        // mEmailSignInButton.setOnClickListener(new OnClickListener() {
        Button mIDSignInButton = (Button) findViewById(R.id.id_sign_in_button);
        mIDSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        
        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        // mOAuthLoginButton.setBgResourceId(R.drawable.img_loginbtn_usercustom);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void initData() {
        mOAuthLoginInstance = OAuthLogin.getInstance();

        mOAuthLoginInstance.showDevelopersLog(true);
        /*
        * 배포 버전에서는 false로 바꿔 민감한 개인정보 유출 방지해야 함.
        * */
        mOAuthLoginInstance.init(
                mContext
                ,getString(R.string.OAUTH_CLIENT_ID)
                ,getString(R.string.OAUTH_CLIENT_SECRET)
                ,getString(R.string.OAUTH_CLIENT_NAME)
        );
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


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mIDView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String ID = mIDView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid ID.
        if (TextUtils.isEmpty(ID)) {
            mIDView.setError(getString(R.string.error_field_required));
            focusView = mIDView;
            cancel = true;
        } else if (!isIDValid(ID)) {
            mIDView.setError(getString(R.string.error_invalid_id));
            focusView = mIDView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(ID, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isIDValid(String ID) {
        return ID.length() > 3;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, String> {
        private final String mID;
        private final String mPassword;
        private ContentValues values;

        UserLoginTask(String ID, String password) {
            mID = ID;
            mPassword = password;
        }

        @Override
        protected String doInBackground(Void... params) {
            // Set URL
            String url = "http://13.125.41.85:3000/api/usr/signup";

            // Do HttpURLConnection with AsyncTask.
            values = new ContentValues();
            values.clear();
            values.put("password",mPassword);
            values.put("ID", mID);

            String message;
            JSONObject result_json_obj;
            RequestHTTPURLConnection requestHTTPURLConnection = new RequestHTTPURLConnection();

            try {
                return requestHTTPURLConnection.request(url, values).get("message").toString(); // Get result message from corresponding URL
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            /**
             * continue from below /* part.
             */
        }

        @Override
        protected void onPostExecute(final String message) {
            mAuthTask = null;
            showProgress(false);

            if (message.equals("success")) {
                /* Switch to TabActivity */
                // Set Intent
                Intent intent = new Intent(getApplicationContext(), TabActivity.class);
                intent.putExtra("hasNAVEROAuth", false);
                startActivity(intent);
                //finish();
                /* */
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    /*
    * OAuthLoginButton에 등록하여 인증이 종료되는 걸 알 수 있다.
    * */

    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                /* 로그인 성공 */
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);
                /* */
                // Show a progress spinner, and kick off a background task to
                // perform the user login attempt.
                Log.i("accessToken", accessToken);
                showProgress(true);
                mAuthTask = new UserLoginTask(accessToken.substring(0,7), "temp_password");
                mAuthTask.execute((Void) null);

                /* Switch to TabActivity */
                // Set Intent
                /*
                Intent intent = new Intent(getApplicationContext(), TabActivity.class);
                // Set informations to send to TabActivity
                intent.putExtra("hasNAVEROAuth", true);
                intent.putExtra("accessToken", accessToken);
                intent.putExtra("refreshToken", refreshToken);
                intent.putExtra("expiresAt", expiresAt);
                intent.putExtra("tokenType", tokenType);

                startActivity(intent);
                */
                /* */
            } else {
                /* 로그인 실패 */
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                /* */
            }
        }

    };


    public void onButtonClick(View v) throws Throwable {

        switch (v.getId()) {
            case R.id.buttonOAuthLoginImg: {
                mOAuthLoginInstance.startOauthLoginActivity(LoginActivity.this, mOAuthLoginHandler);
                break;
            }
            /*
            case R.id.buttonOAuthLogout: {
                mOAuthLoginInstance.logout(mContext);
                updateView();
                break;
            }

            case R.id.buttonOAuthDeleteToken: {
                new DeleteTokenTask().execute();
                break;
            }
            */
            default:
                break;
        }
    }

    private class DeleteTokenTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            boolean isSuccessDeleteToken = mOAuthLoginInstance.logoutAndDeleteToken(mContext);

            if (!isSuccessDeleteToken) {
                // 서버에서 token 삭제에 실패했어도 클라이언트에 있는 token 은 삭제되어 로그아웃된 상태이다
                // 실패했어도 클라이언트 상에 token 정보가 없기 때문에 추가적으로 해줄 수 있는 것은 없음
                // Log.d(TAG, "errorCode:" + mOAuthLoginInstance.getLastErrorCode(mContext));
                // Log.d(TAG, "errorDesc:" + mOAuthLoginInstance.getLastErrorDesc(mContext));
            }

            return null;
        }
    }


    /**
     * Server connection (HTTP) checking code ...
     */
    /*
    public class NetworkTask {
        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        String doInBackground(Void... params) {
            String message;
            String result; // Variable to save requested result.
            JSONObject result_json_obj;
            RequestHTTPURLConnection requestHTTPURLConnection = new RequestHTTPURLConnection();
            //Log.i("R_HTTP_URL_CONN", requestHTTPURLConnection.toString());
            //Log.i("url", url);
            result_json_obj = requestHTTPURLConnection.request(url, values); // Get result message from corresponding URL
            try {
                message = (String) result_json_obj.get("message");
                result = (String) result_json_obj.get("result");
                return message;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        void onPostExecute(Void... params) {
            // doInBackground()'s return value would delivered by meta-variable of onPostExecute, print s
            //Log.i("validation result_init", "validation result_init"+signUpResult);
            signUpResult = doInBackground();
            Log.i("validation result", "validation result "+signUpResult);
        }
    }
    */
}

