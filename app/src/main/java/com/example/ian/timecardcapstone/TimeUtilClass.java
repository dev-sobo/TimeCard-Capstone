package com.example.ian.timecardcapstone;

import android.database.Cursor;

/**
 * This class will be in charge of all the time calculations that will be necessary for a given shift:
 *      calculating number of hours worked in a shift
 *      calculating gross pay based on user-provided hourly rate
 *      overtime pay calculations
 *
 */
public class TimeUtilClass {
    Cursor mShiftCursor;
    TimeUtilClass(Cursor shiftCursor) {
        mShiftCursor = shiftCursor;
    }




}
