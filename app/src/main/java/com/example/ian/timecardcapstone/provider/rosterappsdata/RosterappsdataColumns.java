package com.example.ian.timecardcapstone.provider.rosterappsdata;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.ian.timecardcapstone.provider.ShiftProvider;
import com.example.ian.timecardcapstone.provider.rosterappsdata.RosterappsdataColumns;
import com.example.ian.timecardcapstone.provider.shift.ShiftColumns;

/**
 * Simple table for rosterapps data.
 */
public class RosterappsdataColumns implements BaseColumns {
    public static final String TABLE_NAME = "rosterappsdata";
    public static final Uri CONTENT_URI = Uri.parse(ShiftProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String ROSTERAPPS_DATA = "rosterapps_data";

    public static final String COLOR_OF_SHIFTS = "color_of_shifts";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            ROSTERAPPS_DATA,
            COLOR_OF_SHIFTS
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(ROSTERAPPS_DATA) || c.contains("." + ROSTERAPPS_DATA)) return true;
            if (c.equals(COLOR_OF_SHIFTS) || c.contains("." + COLOR_OF_SHIFTS)) return true;
        }
        return false;
    }

}
