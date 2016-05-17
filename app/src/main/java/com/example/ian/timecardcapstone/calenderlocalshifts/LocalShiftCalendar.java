package com.example.ian.timecardcapstone.calenderlocalshifts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ian.timecardcapstone.Main2Activity;
import com.example.ian.timecardcapstone.R;
import com.example.ian.timecardcapstone.SettingsActivity;
import com.example.ian.timecardcapstone.calendarrosterappsshifts.RosterAppsActivity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.roomorama.caldroid.CaldroidFragment;

import java.util.Calendar;

public class LocalShiftCalendar extends AppCompatActivity {
    private LocalShiftsFragment caldroidFragment;
    private static final String CALENDAR_SAVED_STATE = "CALDROID_SAVED_STATE";

    private static final String LOG_TAG = LocalShiftCalendar.class.getSimpleName();
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Tracker mTracker;

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("LOCAL CALENDAR ACTIVITY");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        Toolbar toolbar = (Toolbar) findViewById(R.id.local_calendar_toolbar);
        setSupportActionBar(toolbar);

        mTracker = Main2Activity.app.getDefaultTracker();

        navigationView = (NavigationView) findViewById(R.id.local_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Intent startCalendarActivity = new Intent(LocalShiftCalendar.this, LocalShiftCalendar.class);
                Intent startRosterAppsActivity = new Intent(LocalShiftCalendar.this, RosterAppsActivity.class);
                Intent startSettingsActivity = new Intent(LocalShiftCalendar.this, SettingsActivity.class);
                Intent startHomeActivity = new Intent(LocalShiftCalendar.this, Main2Activity.class);

                if (menuItem.isChecked()) menuItem.setCheckable(false);
                else menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.rosterapps_calendar:
                        startActivity(startRosterAppsActivity);
                        return true;
                    case R.id.nav_local_calendar:
                        startActivity(startCalendarActivity);
                        return true;
                    case R.id.nav_settings:
                        startActivity(startSettingsActivity);
                        return true;
                    case R.id.home_activity:
                        startActivity(startHomeActivity);
                        return true;
                    default:
                        return false;
                }
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.local_calendar_drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        caldroidFragment = new LocalShiftsFragment();

        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState, CALENDAR_SAVED_STATE);
        } else {
            Bundle calArgs = new Bundle();
            Calendar calendar = Calendar.getInstance();
            calArgs.putInt(CaldroidFragment.MONTH, calendar.get(Calendar.MONTH) + 1);
            calArgs.putInt(CaldroidFragment.YEAR, calendar.get(Calendar.YEAR));
            calArgs.putBoolean(CaldroidFragment.ENABLE_SWIPE, false);
            calArgs.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
            calArgs.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

            caldroidFragment.setArguments(calArgs);
        }


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                .replace(R.id.calendar, caldroidFragment);
        ft.commit();
/*

        LinearLayout linearCalendar = (LinearLayout)findViewById(R.id.calendar);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable
        int height = size.y;
        int width = size.x;

        // Retrieves the current parameters of the layout and storing them in variable params

        ViewGroup.LayoutParams params = linearCalendar.getLayoutParams();

        // Re-setting the height parameter to .51 the max screen height

        params.height =  (int)Math.round(height*.51);
        params.width = (int)Math.round(width*.80);
*/


    }


}
