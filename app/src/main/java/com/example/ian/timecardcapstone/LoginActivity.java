package com.example.ian.timecardcapstone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

import com.example.ian.timecardcapstone.provider.rosterappsdata.RosterappsdataColumns;
import com.example.ian.timecardcapstone.provider.rosterappsdata.RosterappsdataContentValues;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */

    /**
     * needed variables for rosterapps login
     */
    public final static String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0";
    public final static String rosterappsLogin = "https://jetblue.rosterapps.com/Login.aspx?ReturnUrl=/";
    public final static String rosterAppsCalender = "https://jetblue.rosterapps.com/calendar.month.aspx";
    public final static String eventTarget = "__EVENTTARGET";
    public final static String eventArgument = "__EVENTARGUMENT";
    public final static String lastFocus = "__LASTFOCUS";
    public final static String viewState = "__VIEWSTATE";
    public final static String viewStateGenerator = "__VIEWSTATEGENERATOR";
    public final static String txtUsername = "txtUsername";
    public final static String txtPassword = "txtPassword";
    public final static String btnLogin = "btnLogin";
    // public final static String LOG_TAG = RosterAppsLoginIntentService.class.getSimpleName();

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_LOGIN_ROSTERAPPS = "com.example.ian.timecardcapstone.data.action.FOO";
    private static final String ACTION_BAZ = "com.example.ian.timecardcapstone.data.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_USERNAME = "com.example.ian.timecardcapstone.data.extra.PARAM1";
    private static final String EXTRA_PASSWORD = "com.example.ian.timecardcapstone.data.extra.PARAM2";
    private static Context mContext;


    private static final int REQUEST_READ_CONTACTS = 0;

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupActionBar();
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_ACTION_DONE) {
                    Log.e(LOG_TAG, "ACTION_DONE RECEIVED");
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

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
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
//            getActionBar().setDisplayHomeAsUpEnabled(true);
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
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
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
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);

            // RosterAppsLoginIntentService.startActionLoginRosterapps(LoginActivity.this, email, password);

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return (email.contains("@") && email.endsWith(".com"));
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 1;
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
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Document returnedHTMLRosterAppsData = handleActionRosterappsLogin(mEmail, mPassword);
            if (returnedHTMLRosterAppsData != null) {
                String HTMLRosterAppsText = returnedHTMLRosterAppsData.text();
                if (HTMLRosterAppsText.contains("Forgot")) {
                    return false;
                }
                organizeShiftHTMLData(returnedHTMLRosterAppsData);
                return true;
            }
            return false;
        }

            /*for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }*/


        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError("wrong email or password");
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

        // TODO: Get the HTML string from logging into Rosterapps. Organize the HTML data, sorting out all of the shift data to be organized week by week

        /**
         * Log into Rosterapps with the provided user name and password, and get back the HTML data from the calendar
         */
        private Document handleActionRosterappsLogin(String username, String password) {
            try {
                Connection.Response loginResponse = Jsoup.connect(rosterappsLogin)
                        .userAgent(userAgent)
                        .data(eventTarget, "")
                        .data(eventArgument, "")
                        .data(lastFocus, "")
                        .data(viewState, "/wEPDwULLTE0NTU3NDM0MzlkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBRBjaGtQZXJzaXN0Q29va2llI+gcwdzIyCAjhj2bPon1rUHC+z8=")
                        .data(viewStateGenerator, "C2EE9ABB")
                        .data(txtUsername, username)
                        .data(txtPassword, password)
                        .data(btnLogin, "Login")
                        .method(Connection.Method.POST)
                        .execute();
                Map<String, String> loginCookie = loginResponse.cookies();
                Log.d(LOG_TAG, "COOKIES: " + loginCookie.toString());
                //  Log.i(LOG_TAG," LOGIN_RESPONSE: " + loginResponse.parse().html());
                Document calendarDocument = Jsoup.connect(rosterAppsCalender)
                        .userAgent(userAgent)
                        .cookies(loginCookie)
                        .execute().parse();
                // System.out.print(calendarDocument.html());

                return calendarDocument;
            } catch (IOException exception) {
                Log.e(LOG_TAG, "EXCEPTION ERROR: " + exception);
            }
            // TODO: Handle action Foo
            return null;
        }

        private void organizeShiftHTMLData(Document calenderHTML) {
            String shiftDay;
            ArrayList<String> listOfShiftTypes = new ArrayList<>();
            if (calenderHTML != null) {
                Elements entireMonth = calenderHTML.getElementsByClass("week");

                for (int weekIndex = 0; weekIndex < entireMonth.size(); weekIndex++) {
                    Element singleWeek = entireMonth.get(weekIndex);
                    //Log.i(LOG_TAG, singleWeek.toString());
                    Elements days = singleWeek.getElementsByTag("td");

                    for (int dayIndex = 0; dayIndex < days.size(); dayIndex++) {

                        Element singleDay = days.get(dayIndex);
                        Log.e(LOG_TAG, "ENTIRE DAY: " + singleDay.text());
                        String dayOfShifts = singleDay.text();
                        listOfShiftTypes.add(dayOfShifts);
                        // Log.i(LOG_TAG,  singleDay.toString());
                        //+ " TYPE OF SHIFT: " +  singleDay.getElementsByTag("div").attr("class")

                        Elements shiftsInDay = singleDay.getElementsByTag("div");
                        //Log.i(LOG_TAG, shiftsInDay.toString());
                        //Log.i(LOG_TAG, "SHIFTS IN DAY: " + shiftsInDay.text());
                        Log.i(LOG_TAG, listOfShiftTypes.toString());

                    /*for (int shiftIndex = 0; shiftIndex < shiftsInDay.size(); shiftIndex++) {
                        Element singleShift =  shiftsInDay.get(shiftIndex);

                        String typeOfShift = singleShift.attr("class");

                        listOfShiftTypes.add(typeOfShift);
                        Log.i(LOG_TAG, listOfShiftTypes.toString());
                    }*/


                    }
                }
                RosterappsdataContentValues rosterappsdataContentValues = new RosterappsdataContentValues();
                for (int thingInListIndex = 0; thingInListIndex < listOfShiftTypes.size(); thingInListIndex++) {
                    rosterappsdataContentValues.putRosterappsData(listOfShiftTypes.get(thingInListIndex));
                    mContext = getApplicationContext();
                    mContext.getContentResolver().insert(RosterappsdataColumns.CONTENT_URI, rosterappsdataContentValues.values());

                }
            }
        }

    }
}