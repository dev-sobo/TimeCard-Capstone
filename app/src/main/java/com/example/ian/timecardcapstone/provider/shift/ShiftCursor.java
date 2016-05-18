package com.example.ian.timecardcapstone.provider.shift;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.ian.timecardcapstone.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code shift} table.
 */
public class ShiftCursor extends AbstractCursor implements ShiftModel {
    public ShiftCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(ShiftColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code start_time_hhmm} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getStartTimeHhmm() {
        String res = getStringOrNull(ShiftColumns.START_TIME_HHMM);
        if (res == null)
            throw new NullPointerException("The value of 'start_time_hhmm' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code end_time_hhmm} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getEndTimeHhmm() {
        String res = getStringOrNull(ShiftColumns.END_TIME_HHMM);
        return res;
    }

    /**
     * Get the {@code start_time_unix} value.
     */
    public int getStartTimeUnix() {
        Integer res = getIntegerOrNull(ShiftColumns.START_TIME_UNIX);
        if (res == null)
            throw new NullPointerException("The value of 'start_time_unix' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code end_time_unix} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getEndTimeUnix() {
        Integer res = getIntegerOrNull(ShiftColumns.END_TIME_UNIX);
        return res;
    }

    /**
     * Get the {@code hourly_pay} value.
     */
    public float getHourlyPay() {
        Float res = getFloatOrNull(ShiftColumns.HOURLY_PAY);
        if (res == null)
            throw new NullPointerException("The value of 'hourly_pay' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code day_of_week} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getDayOfWeek() {
        String res = getStringOrNull(ShiftColumns.DAY_OF_WEEK);
        if (res == null)
            throw new NullPointerException("The value of 'day_of_week' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code day_of_month} value.
     */
    public int getDayOfMonth() {
        Integer res = getIntegerOrNull(ShiftColumns.DAY_OF_MONTH);
        if (res == null)
            throw new NullPointerException("The value of 'day_of_month' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code month_name} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getMonthName() {
        String res = getStringOrNull(ShiftColumns.MONTH_NAME);
        if (res == null)
            throw new NullPointerException("The value of 'month_name' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code year} value.
     */
    public int getYear() {
        Integer res = getIntegerOrNull(ShiftColumns.YEAR);
        if (res == null)
            throw new NullPointerException("The value of 'year' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code num_hrs_shift} value.
     * Can be {@code null}.
     */
    @Nullable
    public Float getNumHrsShift() {
        Float res = getFloatOrNull(ShiftColumns.NUM_HRS_SHIFT);
        return res;
    }

    /**
     * Get the {@code gross_pay} value.
     * Can be {@code null}.
     */
    @Nullable
    public Float getGrossPay() {
        Float res = getFloatOrNull(ShiftColumns.GROSS_PAY);
        return res;
    }
}
