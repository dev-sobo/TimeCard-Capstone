package com.example.ian.timecardcapstone.calendarrosterappsshifts;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ian.timecardcapstone.Main2Activity;
import com.example.ian.timecardcapstone.R;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;

public class RosterAppsActivity extends AppCompatActivity {
    private RosterappsFragment mRosterappsFragment;
    private static final String ROSTER_APPS_SAVED_STATE = "rosterapps_saved";
    private static final String LOG_TAG = RosterAppsActivity.class.getSimpleName();
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster_apps);

        mTracker = Main2Activity.app.getDefaultTracker();

        mRosterappsFragment = new RosterappsFragment();

        if (savedInstanceState != null) {
            mRosterappsFragment.restoreStatesFromKey(savedInstanceState, ROSTER_APPS_SAVED_STATE);
        } else {
            Bundle calArgs = new Bundle();
            Calendar calendar = Calendar.getInstance();
            calArgs.putInt(CaldroidFragment.MONTH, calendar.get(Calendar.MONTH) + 1);
            calArgs.putInt(CaldroidFragment.YEAR, calendar.get(Calendar.YEAR));
            calArgs.putBoolean(CaldroidFragment.ENABLE_SWIPE, false);
            calArgs.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
            calArgs.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, true);
            calArgs.putBoolean(CaldroidFragment.SHOW_NAVIGATION_ARROWS, false);

            mRosterappsFragment.setArguments(calArgs);
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.rosterAppsCalendar, mRosterappsFragment);
        fragmentTransaction.commit();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mRosterappsFragment.passScreenHeight(metrics.heightPixels);

        CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                TextView rosterAppsText = (TextView) view.findViewById(R.id.rosterAppsData);

                Toast.makeText(RosterAppsActivity.this, rosterAppsText.getText(), Toast.LENGTH_LONG).show();
            }
        };
        mRosterappsFragment.setCaldroidListener(listener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("ROSTER APPS CALENDAR ACTIVITY");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
