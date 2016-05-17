package com.example.ian.timecardcapstone.calenderlocalshifts;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.ian.timecardcapstone.Main2Activity;
import com.example.ian.timecardcapstone.R;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.roomorama.caldroid.CaldroidFragment;

import java.util.Calendar;

public class LocalShiftCalendar extends AppCompatActivity {
    private LocalShiftsFragment caldroidFragment;
    private static final String CALENDAR_SAVED_STATE = "CALDROID_SAVED_STATE";

    private static final String LOG_TAG = LocalShiftCalendar.class.getSimpleName();
    private Tracker mTracker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        Toolbar toolbar = (Toolbar) findViewById(R.id.local_calendar_toolbar);
        setSupportActionBar(toolbar);

        mTracker = Main2Activity.app.getDefaultTracker();


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

    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("LOCAL CALENDAR ACTIVITY");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


}
