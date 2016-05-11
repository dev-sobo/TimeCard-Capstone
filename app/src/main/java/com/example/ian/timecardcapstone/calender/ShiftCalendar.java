package com.example.ian.timecardcapstone.calender;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.ian.timecardcapstone.R;
import com.roomorama.caldroid.CaldroidFragment;

import java.util.Calendar;

public class ShiftCalendar extends AppCompatActivity {
    private ShiftsFragment caldroidFragment;
    private static final String CALENDAR_SAVED_STATE = "CALDROID_SAVED_STATE";

    private static final String LOG_TAG = ShiftCalendar.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);


        caldroidFragment = new ShiftsFragment();

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
