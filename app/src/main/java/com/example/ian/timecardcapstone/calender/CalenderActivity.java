package com.example.ian.timecardcapstone.calender;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.ian.timecardcapstone.R;
import com.roomorama.caldroid.CaldroidFragment;

import java.util.Calendar;

public class CalenderActivity extends AppCompatActivity {
    private CaldroidFragment caldroidFragment;
    private static final String CALENDAR_SAVED_STATE = "CALDROID_SAVED_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        caldroidFragment = new CaldroidFragment();

        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState, CALENDAR_SAVED_STATE);
        } else {
            Bundle calArgs = new Bundle();
            Calendar calendar = Calendar.getInstance();
            calArgs.putInt(CaldroidFragment.MONTH, calendar.get(Calendar.MONTH) + 1);
            calArgs.putInt(CaldroidFragment.YEAR, calendar.get(Calendar.YEAR));
            calArgs.putBoolean(CaldroidFragment.ENABLE_SWIPE, false);
            calArgs.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, false);

            caldroidFragment.setArguments(calArgs);
        }


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                .replace(R.id.calendar, caldroidFragment);
        ft.commit();


    }
}
