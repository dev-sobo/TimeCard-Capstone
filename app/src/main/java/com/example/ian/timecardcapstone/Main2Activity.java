package com.example.ian.timecardcapstone;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.ian.timecardcapstone.calendarrosterappsshifts.RosterAppsActivity;
import com.example.ian.timecardcapstone.calenderlocalshifts.LocalShiftCalendarActivity;
import com.example.ian.timecardcapstone.data.RosterAppsLoginIntentService;
import com.example.ian.timecardcapstone.provider.shift.ShiftColumns;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.TimeZone;

import hirondelle.date4j.DateTime;


/**
 * This class has the responsibility of managing a user's log in to RosterApps as well as their clocking in and out to the database
 * This should be broken up more.
 */
public class Main2Activity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>, NavigationView.OnNavigationItemSelectedListener {
    public static final Integer ROSTERAPPS_PASS_KEY = 10;
    public static final Integer ROSTERAPPS_EMAIL_KEY = 11;
    private static final String LOG_TAG = Main2Activity.class.getSimpleName();
    private static final int MAIN_ACT_LOADER_ID = 2;
    public static TimeCardAnalytics app;
    private static Uri mClockInRow;
    private DatabaseHandler databaseHandler;
    private Uri clockedInUri;
    private Tracker mTracker;
    private TextView currentShiftStart;
    private TextView currentShiftEnd;
    private TextView currentShiftDate;
    private TextView currentShiftHrsWrked;
    private TextView currentShiftGrossPay;
    private ArrayMap<Integer, String> emailPassMap = new ArrayMap<>();
    private Button loginPageButton;
    private static final String shiftBoolKey = "BOOL_KEY";
    private static final String uriParcelKey = "URI_KEY";
    private boolean shiftButtonBool;


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        clockedInUri = savedInstanceState.getParcelable(uriParcelKey);
        shiftButtonBool = savedInstanceState.getBoolean(shiftBoolKey);
        Log.i(LOG_TAG, "RESTORED CLOCKED IN URI: " + clockedInUri.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "SETTING SCREENANME");
        mTracker.setScreenName("MAIN_ACTIVITY");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(uriParcelKey, clockedInUri);
        outState.putBoolean(shiftBoolKey, shiftButtonBool);
        Log.i(LOG_TAG, "SAVED CLOCKED IN URI: " + clockedInUri);
    }

    // DateTime dateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AdView adView = (AdView) findViewById(R.id.bannerAd);
        AdRequest adRequest = new AdRequest.Builder().
                addTestDevice("89C1C45775C35F9B96807A0DD84FAA1D").build();
        if (adView != null) {
            adView.loadAd(adRequest);
        }

        // TODO: Put the login page button on the app drawer
        loginPageButton = (Button) findViewById(R.id.loginButton);
        final Intent intent = new Intent(Main2Activity.this, LoginActivity.class);
        loginPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);

            }
        });

        app = (TimeCardAnalytics) getApplication();
        mTracker = app.getDefaultTracker();

/*        EditText rosterAppsEmail = (EditText) findViewById(R.id.rosterAppsEmail);
        EditText rosterAppsPassword = (EditText) findViewById(R.id.rosterAppsPassword);
        if (rosterAppsEmail != null ) {
            rosterAppsEmail.setOnEditorActionListener(listener);
            rosterAppsPassword.setOnEditorActionListener(listener);
        }*/



        Button testLoginButton = (Button) findViewById(R.id.button);
        assert testLoginButton != null;
        testLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RosterAppsLoginIntentService.startActionLoginRosterapps(Main2Activity.this, "ian.sobocinski@jetblue.com",
                        "Thisisforthezoos.");
            }
        });
        currentShiftStart = (TextView) findViewById(R.id.currentShiftStart);
        currentShiftEnd = (TextView) findViewById(R.id.currentShiftEnd);
        currentShiftDate = (TextView) findViewById(R.id.currentShiftDate);
        currentShiftHrsWrked = (TextView) findViewById(R.id.currentShiftHrsWrked);
        currentShiftGrossPay = (TextView) findViewById(R.id.currentShiftGrossPay);

        ToggleButton clockInClockOutButton = (ToggleButton) findViewById(R.id.ClockInClockOutid);

        /* This is where clocking in and clocking out is handled.
            user clocks in, the system records the current time and date, inserts it into a row using the
            databasehandler class. it inserts:
         * Current day
         * Current Time in hh:mm format
         * Current Time in UNIX format
         * Current month
         * Current day of week
         * Current year
         * Hourly Pay
            into the row.
            Then, a row URI is returned. This URI is used to clock out the user, and inserts the following data into
            the same row:


        */

        /**
         *
         *
         */
        if (clockInClockOutButton != null) {
            clockInClockOutButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    shiftButtonBool = b;
                    if (shiftButtonBool) {
                        Log.e(LOG_TAG, "CLOCKED IN! \n");
                        ClockInOutService.startClockIn(getApplicationContext());

                        databaseHandler = new DatabaseHandler(getApplicationContext());


                        clockedInUri = databaseHandler.clockIn(DateTime.now(TimeZone.getDefault()));
                        getSupportLoaderManager().initLoader(MAIN_ACT_LOADER_ID, null, Main2Activity.this);

                    } else if (!shiftButtonBool) {

                        Log.e(LOG_TAG, "CLOCKED OUT! \n");
                        int numRowsUpdated = databaseHandler.clockOut(clockedInUri, DateTime.now(TimeZone.getDefault()));
                        Log.i(LOG_TAG, "NUMBER OF ROWS UPDATED: " + numRowsUpdated);

                        Cursor returnedUri = getContentResolver().query(clockedInUri, null, null, null, null);

                        if (returnedUri != null) {
                            returnedUri.moveToFirst();
                            int columnTotal = returnedUri.getColumnCount();
                            Log.i(LOG_TAG, "CLOCKED OUT CONTENTS OF MOST RECENT CLOCKED IN URI: " + DatabaseUtils.dumpCursorToString(returnedUri));
                        }

                        Cursor entireTable = getContentResolver().query(ShiftColumns.CONTENT_URI, null, null, null, null);

                        if (entireTable != null) {
                            entireTable.moveToFirst();
                            Log.i(LOG_TAG, "ENTIRE CLOCKED OUT TABLE CURSOR: " + DatabaseUtils.dumpCursorToString(entireTable));

                            entireTable.close();
                        }


                    }
                }
            });
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.setDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == MAIN_ACT_LOADER_ID) {
            CursorLoader shiftsLoader = new CursorLoader(this, ShiftColumns.CONTENT_URI, new String[]{ShiftColumns.START_TIME_HHMM,
                    ShiftColumns.END_TIME_HHMM, ShiftColumns.DAY_OF_WEEK,
                    ShiftColumns.DAY_OF_MONTH, ShiftColumns.MONTH_NAME, ShiftColumns.YEAR,
                    ShiftColumns.NUM_HRS_SHIFT, ShiftColumns.GROSS_PAY}, null, null, null);
            return shiftsLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToLast()) {
            data.moveToLast();
            currentShiftStart.setText(getString(R.string.clockInTimeText) + data.getString(data.getColumnIndex(ShiftColumns.START_TIME_HHMM)));
            currentShiftEnd.setText(getString(R.string.clockOutTimeText) + data.getString(data.getColumnIndex(ShiftColumns.END_TIME_HHMM)));
            int month = data.getInt(data.getColumnIndex(ShiftColumns.MONTH_NAME));
            int monthDay = data.getInt(data.getColumnIndex(ShiftColumns.DAY_OF_MONTH));
            int year = data.getInt(data.getColumnIndex(ShiftColumns.YEAR));
            String date = month + "/" + monthDay
                    + "/" + year;
            currentShiftDate.setText(date);

            currentShiftHrsWrked.setText(getString(R.string.numHrsWrkedText) + Float.toString(data.getFloat(data.getColumnIndex(ShiftColumns.NUM_HRS_SHIFT))));
            currentShiftGrossPay.setText(getString(R.string.grossPayText) + Float.toString(data.getFloat(data.getColumnIndex(ShiftColumns.GROSS_PAY))));
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    String getCurrentHoursAndMinutes() {

        DateTime currentTime = DateTime.now(TimeZone.getDefault());
        Integer currentHour = currentTime.getHour();
        Integer currentMinutes = currentTime.getMinute();
        String currentHoursAndMinutes = currentTime.format("hh:mm");
        Log.i(LOG_TAG, "CURRENT HOURS AND MINS: " + currentHoursAndMinutes);

        return currentHoursAndMinutes;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent startCalendarActivity = new Intent(this, LocalShiftCalendarActivity.class);
        Intent startRosterAppsActivity = new Intent(this, RosterAppsActivity.class);
        Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
        Intent startLoginActivity = new Intent(this, LoginActivity.class);
        if (id == R.id.rosterapps_calendar) {
            startActivity(startRosterAppsActivity);
            return true;
        } else if (id == R.id.nav_local_calendar) {
            startActivity(startCalendarActivity);
            return true;
        } else if (id == R.id.nav_settings) {
            startActivity(startSettingsActivity);
            return true;
        } else if (id == R.id.nav_login) {
            startActivity(startLoginActivity);
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

}
