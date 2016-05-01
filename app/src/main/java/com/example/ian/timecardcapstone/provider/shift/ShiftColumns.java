package com.example.ian.timecardcapstone.provider.shift;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.ian.timecardcapstone.provider.ShiftProvider;
import com.example.ian.timecardcapstone.provider.shift.ShiftColumns;

/**
 * This describes an individual shift, which includes it's start time, end time, hourly pay, day of week, day of month (the date), day of the week, Month Name, Year, the (calculated) number of hours worked for this shift (calculated from the start and end time), and the (calculated) gross pay for shift(calculated from from hourly pay and # of hrs)
 */
public class ShiftColumns implements BaseColumns {
    public static final String TABLE_NAME = "shift";
    public static final Uri CONTENT_URI = Uri.parse(ShiftProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String START_TIME_HHMM = "start_time_hhmm";

    public static final String END_TIME_HHMM = "end_time_hhmm";

    public static final String START_TIME_UNIX = "start_time_unix";

    public static final String END_TIME_UNIX = "end_time_unix";

    public static final String HOURLY_PAY = "hourly_pay";

    public static final String DAY_OF_WEEK = "day_of_week";

    public static final String DAY_OF_MONTH = "day_of_month";

    public static final String MONTH_NAME = "month_name";

    public static final String YEAR = "year";

    public static final String NUM_HRS_SHIFT = "num_hrs_shift";

    public static final String GROSS_PAY = "gross_pay";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            START_TIME_HHMM,
            END_TIME_HHMM,
            START_TIME_UNIX,
            END_TIME_UNIX,
            HOURLY_PAY,
            DAY_OF_WEEK,
            DAY_OF_MONTH,
            MONTH_NAME,
            YEAR,
            NUM_HRS_SHIFT,
            GROSS_PAY
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(START_TIME_HHMM) || c.contains("." + START_TIME_HHMM)) return true;
            if (c.equals(END_TIME_HHMM) || c.contains("." + END_TIME_HHMM)) return true;
            if (c.equals(START_TIME_UNIX) || c.contains("." + START_TIME_UNIX)) return true;
            if (c.equals(END_TIME_UNIX) || c.contains("." + END_TIME_UNIX)) return true;
            if (c.equals(HOURLY_PAY) || c.contains("." + HOURLY_PAY)) return true;
            if (c.equals(DAY_OF_WEEK) || c.contains("." + DAY_OF_WEEK)) return true;
            if (c.equals(DAY_OF_MONTH) || c.contains("." + DAY_OF_MONTH)) return true;
            if (c.equals(MONTH_NAME) || c.contains("." + MONTH_NAME)) return true;
            if (c.equals(YEAR) || c.contains("." + YEAR)) return true;
            if (c.equals(NUM_HRS_SHIFT) || c.contains("." + NUM_HRS_SHIFT)) return true;
            if (c.equals(GROSS_PAY) || c.contains("." + GROSS_PAY)) return true;
        }
        return false;
    }

}
