package com.example.ian.timecardcapstone.provider.rosterappsdata;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.ian.timecardcapstone.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code rosterappsdata} table.
 */
public class RosterappsdataContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return RosterappsdataColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable RosterappsdataSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable RosterappsdataSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public RosterappsdataContentValues putRosterappsData(@Nullable String value) {
        mContentValues.put(RosterappsdataColumns.ROSTERAPPS_DATA, value);
        return this;
    }

    public RosterappsdataContentValues putRosterappsDataNull() {
        mContentValues.putNull(RosterappsdataColumns.ROSTERAPPS_DATA);
        return this;
    }

    public RosterappsdataContentValues putColorOfShifts(@Nullable String value) {
        mContentValues.put(RosterappsdataColumns.COLOR_OF_SHIFTS, value);
        return this;
    }

    public RosterappsdataContentValues putColorOfShiftsNull() {
        mContentValues.putNull(RosterappsdataColumns.COLOR_OF_SHIFTS);
        return this;
    }
}
