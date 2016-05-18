package com.example.ian.timecardcapstone.provider.rosterappsdata;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.ian.timecardcapstone.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code rosterappsdata} table.
 */
public class RosterappsdataCursor extends AbstractCursor implements RosterappsdataModel {
    public RosterappsdataCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(RosterappsdataColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code rosterapps_data} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getRosterappsData() {
        String res = getStringOrNull(RosterappsdataColumns.ROSTERAPPS_DATA);
        return res;
    }

    /**
     * Get the {@code color_of_shifts} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getColorOfShifts() {
        String res = getStringOrNull(RosterappsdataColumns.COLOR_OF_SHIFTS);
        return res;
    }
}
