package com.example.ian.timecardcapstone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.TimeZone;

import hirondelle.date4j.DateTime;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    /**
     *
     * @return Returns the current System Time, and stores it into the database for the ClockIn Time
     */
    private DateTime ClockIn() {

        DateTime clockedInTime = DateTime.now(TimeZone.getDefault());
        // TODO: Insert into database using the content provider
        return clockedInTime;
    }
}
