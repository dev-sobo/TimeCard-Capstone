package com.example.ian.timecardcapstone.provider.shift;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.ian.timecardcapstone.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code shift} table.
 */
public class ShiftContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ShiftColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable ShiftSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable ShiftSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public ShiftContentValues putStartTimeHhmm(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("startTimeHhmm must not be null");
        mContentValues.put(ShiftColumns.START_TIME_HHMM, value);
        return this;
    }


    public ShiftContentValues putEndTimeHhmm(@Nullable String value) {
        mContentValues.put(ShiftColumns.END_TIME_HHMM, value);
        return this;
    }

    public ShiftContentValues putEndTimeHhmmNull() {
        mContentValues.putNull(ShiftColumns.END_TIME_HHMM);
        return this;
    }

    public ShiftContentValues putStartTimeUnix(int value) {
        mContentValues.put(ShiftColumns.START_TIME_UNIX, value);
        return this;
    }


    public ShiftContentValues putEndTimeUnix(@Nullable Integer value) {
        mContentValues.put(ShiftColumns.END_TIME_UNIX, value);
        return this;
    }

    public ShiftContentValues putEndTimeUnixNull() {
        mContentValues.putNull(ShiftColumns.END_TIME_UNIX);
        return this;
    }

    public ShiftContentValues putHourlyPay(float value) {
        mContentValues.put(ShiftColumns.HOURLY_PAY, value);
        return this;
    }


    public ShiftContentValues putDayOfWeek(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("dayOfWeek must not be null");
        mContentValues.put(ShiftColumns.DAY_OF_WEEK, value);
        return this;
    }


    public ShiftContentValues putDayOfMonth(int value) {
        mContentValues.put(ShiftColumns.DAY_OF_MONTH, value);
        return this;
    }


    public ShiftContentValues putMonthName(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("monthName must not be null");
        mContentValues.put(ShiftColumns.MONTH_NAME, value);
        return this;
    }


    public ShiftContentValues putYear(int value) {
        mContentValues.put(ShiftColumns.YEAR, value);
        return this;
    }


    public ShiftContentValues putNumHrsShift(@Nullable Float value) {
        mContentValues.put(ShiftColumns.NUM_HRS_SHIFT, value);
        return this;
    }

    public ShiftContentValues putNumHrsShiftNull() {
        mContentValues.putNull(ShiftColumns.NUM_HRS_SHIFT);
        return this;
    }

    public ShiftContentValues putGrossPay(@Nullable Float value) {
        mContentValues.put(ShiftColumns.GROSS_PAY, value);
        return this;
    }

    public ShiftContentValues putGrossPayNull() {
        mContentValues.putNull(ShiftColumns.GROSS_PAY);
        return this;
    }
}
