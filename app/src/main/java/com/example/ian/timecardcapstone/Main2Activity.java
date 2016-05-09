package com.example.ian.timecardcapstone;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.ian.timecardcapstone.data.MyIntentService;
import com.example.ian.timecardcapstone.provider.shift.ShiftColumns;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.TimeZone;

import hirondelle.date4j.DateTime;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private static final String LOG_TAG = Main2Activity.class.getSimpleName();
        private static  Uri mClockInRow;
        private DatabaseHandler databaseHandler;
        private Uri clockedInUri;
        private Tracker mTracker;

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "SETTING SCREENANME");
        mTracker.setScreenName("MAIN_ACTIVITY");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    // DateTime dateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AdView adView = (AdView) findViewById(R.id.bannerAd);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("AE6BAFB7E513807BF79058B193822770").build();
        adView.loadAd(adRequest);

        TimeCardAnalytics app = (TimeCardAnalytics) getApplication();
        mTracker = app.getDefaultTracker();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyIntentService.startActionkLoginRosterapps(Main2Activity.this, "ian.sobocinski@jetblue.com", "Thisisforthezoos.");
            }
        });

        ToggleButton clockInClockOutButton = (ToggleButton) findViewById(R.id.ClockInClockOutid);
        assert clockInClockOutButton != null;
        clockInClockOutButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    Log.e(LOG_TAG, "CLOCKED IN! \n");
                    databaseHandler = new DatabaseHandler(getApplicationContext());

                    clockedInUri = databaseHandler.clockIn(DateTime.now(TimeZone.getDefault()));
                    Cursor returnedClockedInCursor = getContentResolver().query(clockedInUri, null,
                            null, null, null);

                    if(returnedClockedInCursor != null) {
                        returnedClockedInCursor.moveToFirst();
                        /*String[] columnNames = returnedClockedInCursor.getColumnNames();
                        for(int i = 0; i < columnNames.length; i++) {
                            Log.i(LOG_TAG, "COLUMN NAME: " + columnNames[i]);
                        }
                        Log.i(LOG_TAG, "NUMBER OF ROWS REPORTED FROM RETURNED URI: " +  returnedClockedInCursor.getCount());
                        Log.i(LOG_TAG, "COLUMN INDEX FOR NAME: " + returnedClockedInCursor.getColumnIndex("start_time_unix"));
                        Log.i(LOG_TAG, "COLUMN INFORMATION: " + returnedClockedInCursor.getString(3));*/
                        Log.i(LOG_TAG, "INITAITALLY CLOCKED IN CURSOR: " + DatabaseUtils.dumpCursorToString(returnedClockedInCursor));


                        returnedClockedInCursor.close();
                    }

                    Cursor entireTableCursor = getContentResolver().query(ShiftColumns.CONTENT_URI,
                            null, null, null, null);

                    if (entireTableCursor != null) {
                        entireTableCursor.moveToFirst();
                        /*Log.i(LOG_TAG, "NUMBER OF ROWS REPORTED FOR THE ENTIRE TABLE: " + entireTableCursor.getCount());
                        String[] columnNames = entireTableCursor.getColumnNames();
                        Log.i(LOG_TAG, "COLUMN ARRAY LENGTH: " + columnNames.length);
                        for(int i = 0; i < entireTableCursor.getCount(); i++) {
                            Log.i(LOG_TAG, "Row number: " + i);
                            for (int k = 0; k < columnNames.length; k++) {
                                Log.i(LOG_TAG, "COLUMN INDEX IN FOR LOOP: " + entireTableCursor.getColumnName(k));
                               *//* Log.i(LOG_TAG, "start_time_hhmm COLUMN CONTENTS: " + entireTableCursor.getString(1));
                                Log.i(LOG_TAG, "end_time_hhmm COLUMN CONTENTS: " + entireTableCursor.getString(2));*//*
                            }
                            Log.i(LOG_TAG, "CLOCKING IN start_time_hhmm COLUMN CONTENTS: " + entireTableCursor.getString(1));
                            Log.i(LOG_TAG, "CLOCKING IN start_time_unix COLUMN CONTENTS: " + entireTableCursor.getInt(3));

                            entireTableCursor.moveToNext();
                        }

                        //Log.i(LOG_TAG, "COLUMN IN ENTIRE TABLE: " + entireTableCursor.getString(3));*/
                        Log.i(LOG_TAG, "ENTIRE TABLE CURSOR DUMP: " + DatabaseUtils.dumpCursorToString(entireTableCursor));
                        entireTableCursor.close();
                    }


                    /*Log.i(LOG_TAG, "IS CLOCKED IN");
                    // TODO: Record time clocked in, store into the database
                    DateTime nowDate = DateTime.now(TimeZone.getDefault());
                    Log.i(LOG_TAG, "CURRENT DAY : " +  nowDate.getDay() + " CURRENT MONTH: " + nowDate.getMonth()
                            + " CURRENT HOUR: " + nowDate.getHour() + " weekday: " + nowDate.getWeekDay() + " YEAR: " + nowDate.getYear());
                    mClockInRow = clockIn(nowDate);*/


                } else if (b == false) {

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
                        /*String[] columnNames = entireTable.getColumnNames();
                        for (int i = 0; i < entireTable.getCount(); i++) {
                            Log.i(LOG_TAG, "ROW NUMBER: " + i + "CLOCKING OUT END TIME HHMM: " + entireTable.getString(2));
                            Log.i(LOG_TAG, "CLOCKING OUT END TIME UNIX: " + entireTable.getInt(4));
                            entireTable.moveToNext();
                        }*/
                        entireTable.close();
                    }




                    /*// TODO: Record time clocked out, store into the database
                    ShiftSelection selection = new ShiftSelection();
                    selection.dayOfMonth(27);
                    Cursor cursor = getContentResolver().query(ShiftColumns.CONTENT_URI, null,
                           null, null, null);
                    if (cursor != null) {
                        cursor.moveToLast();
                        ShiftCursor clockedInCursor = new ShiftCursor(cursor);
                        Integer dayOfMonth = clockedInCursor.getDayOfMonth();
                        Log.i(LOG_TAG, "dayofmonth: " + dayOfMonth);

                        Log.i(LOG_TAG, "Number of rows in cursor: " + clockedInCursor.getCount());

                        Integer startTimeUnix = clockedInCursor.getStartTimeUnix();
                        Log.i(LOG_TAG, "CLOCKED OUT UNIX time: " + startTimeUnix);
                        //  Log.i(LOG_TAG, "CURRENT TIME: " +  getCurrentHoursAndMinutes());

                        cursor.close();

                    }
                    Uri clockedOutUri =  clockOut(mClockInRow);
                    Cursor clockedOutCursor = getContentResolver().query(clockedOutUri, null, null,
                            null, null);
                    if (clockedOutCursor != null) {
                        clockedOutCursor.moveToLast();
                        ShiftCursor clockedOut = new ShiftCursor(clockedOutCursor);
                        Integer startTimeUnix = clockedOut.getEndTimeUnix();
                        Log.i(LOG_TAG, "START TIME UNIX, CLOCKED OUT: " + startTimeUnix);

                        Integer endTimeUnix = clockedOut.getEndTimeUnix();
                        Log.i(LOG_TAG, "END TIME UNIX, CLOCKED OUT: " + endTimeUnix);

                    }
                    clockOut(mClockInRow);*/


                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    /*Uri clockIn(DateTime nowDate) {
        ShiftContentValues shiftValues = new ShiftContentValues();
        Integer dayOfMonth = nowDate.getDay();
        shiftValues.putDayOfMonth(dayOfMonth);

        char[] stringArray = new char[2];
        String nowString = nowDate.format("hh:mm");
        shiftValues.putStartTimeHhmm(nowString);

        Date date = new Date();
        long unixTime =  (date.getTime() / 1000);
        shiftValues.putStartTimeUnix((int) unixTime);
        Log.i(LOG_TAG, "RAW UNIX TIME: " + unixTime);


        float hourlyPay = (float) 12.66;
        shiftValues.putHourlyPay(hourlyPay);

        String dayOfWeek = String.valueOf(nowDate.getWeekDay());
        shiftValues.putDayOfWeek(dayOfWeek);

        String monthNum = String.valueOf(nowDate.getMonth());
        shiftValues.putMonthName(monthNum);

        Integer yearNum = nowDate.getYear();
        shiftValues.putYear(yearNum);

                   *//* // TODO: NEEDS TO BE NULLABLE
                    shiftValues.putGrossPay((float) 12.66);*//*

        return getContentResolver().insert(ShiftColumns.CONTENT_URI, shiftValues.values());

    }*/

    /**
     * This method handles clocking out, that is, putting in the ending time, both Unix Time and hhmm format
     * Calculates the number of hours being worked by subtracting the end unix time by start unix time, and converting that to hours
     * Then uses that to calculate the gross pay 
     *
     * @return
     */
    /*void clockOut(Uri clockInRow) {
        ShiftContentValues shiftContentValues = new ShiftContentValues();

        Date dateForUnixTime = new Date();
        long unixTime = (dateForUnixTime.getTime() / 1000);
        shiftContentValues.putEndTimeUnix((int) unixTime);

        shiftContentValues.putEndTimeHhmm(getCurrentHoursAndMinutes());

         getContentResolver().update(clockInRow, shiftContentValues.values(),null, null);



    }*/

    String getCurrentHoursAndMinutes() {
        DateTime currentTime = DateTime.now(TimeZone.getDefault());
        Integer currentHour = currentTime.getHour();
        Integer currentMinutes = currentTime.getMinute();
        String currentHoursAndMinutes = currentTime.format("hh:mm");
        Log.i(LOG_TAG, "CURRENT HOURS AND MINS: " + currentHoursAndMinutes);

        return  currentHoursAndMinutes;
    }
 /*   // TODO: 4/28/2016 Calculate Gross Pay based on the start time hours and minutes, and the end time hours and minutes
    Double calcGrossPay(DateTime startTime, DateTime endTime) {
        // convert both to UNIX time if possible, subtract the two, get the user's hourly pay that was inputted in the settings



    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = new Intent(this, SettingsActivity.class);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
