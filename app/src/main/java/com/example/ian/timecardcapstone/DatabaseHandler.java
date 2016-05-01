package com.example.ian.timecardcapstone;

import android.content.Context;
import android.net.Uri;

import com.example.ian.timecardcapstone.provider.shift.ShiftColumns;
import com.example.ian.timecardcapstone.provider.shift.ShiftContentValues;

import hirondelle.date4j.DateTime;

/**
 * Created by ian on 4/28/2016.
 */
public class DatabaseHandler {
    private DateTime mNowDate;
    private Context mContext;
    DatabaseHandler (Context context){
        mContext = context;
    }

    /**
     * Inserts the following information for clocking in:
     * Current day
     * Current Time in hh:mm format
     * Current Time in UNIX format
     * Current month
     * Current day of week
     * Current year
     * Hourly Pay
     *
     * @param clockInTime The time in which the user clocked in at
     * @return The URI at which the clocked in data was inserted in
     */
    public Uri clockIn (DateTime clockInTime) {
        ShiftContentValues clockInValues = new ShiftContentValues();

        clockInValues.putDayOfMonth(clockInTime.getDay());
        clockInValues.putStartTimeHhmm(clockInTime.format("hh:mm"));
        clockInValues.putDayOfWeek(clockInTime.getWeekDay().toString());
        clockInValues.putMonthName(clockInTime.getMonth().toString());
        clockInValues.putYear(clockInTime.getYear());
        // TODO: Implement a settings menu so the user can put in their hourly pay
        clockInValues.putHourlyPay(12.66f);
        long unixTime = (System.currentTimeMillis() / 1000);
        clockInValues.putStartTimeUnix((int) unixTime);



        return mContext.getContentResolver().insert(ShiftColumns.CONTENT_URI, clockInValues.values());
    }

    public int clockOut(Uri clockInUri, DateTime clockOutTime) {
        ShiftContentValues clockOutValues = new ShiftContentValues();

        clockOutValues.putEndTimeHhmm(clockOutTime.format("hh:mm"));
        long unixTime = (System.currentTimeMillis() / 1000);
        clockOutValues.putEndTimeUnix((int) unixTime);
        // TODO: calculate number of hours worked and gross pay based on the hourly pay

       return mContext.getContentResolver().update(clockInUri, clockOutValues.values(),null, null);
    }



  /*  public void clockInTest (Uri clockInUri) {
        Cursor clockedInCursor = mContext.getContentResolver().query(clockInUri, null, null, null, null);

        if (clockedInCursor != null) {
            clockedInCursor.moveToFirst();


            clockedInCursor.close();
        }
    }*/

}
