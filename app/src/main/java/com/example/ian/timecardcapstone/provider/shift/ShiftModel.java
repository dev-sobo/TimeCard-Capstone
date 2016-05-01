package com.example.ian.timecardcapstone.provider.shift;

import com.example.ian.timecardcapstone.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * This describes an individual shift, which includes it's start time, end time, hourly pay, day of week, day of month (the date), day of the week, Month Name, Year, the (calculated) number of hours worked for this shift (calculated from the start and end time), and the (calculated) gross pay for shift(calculated from from hourly pay and # of hrs)
 */
public interface ShiftModel extends BaseModel {

    /**
     * Get the {@code start_time_hhmm} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getStartTimeHhmm();

    /**
     * Get the {@code end_time_hhmm} value.
     * Can be {@code null}.
     */
    @Nullable
    String getEndTimeHhmm();

    /**
     * Get the {@code start_time_unix} value.
     */
    int getStartTimeUnix();

    /**
     * Get the {@code end_time_unix} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getEndTimeUnix();

    /**
     * Get the {@code hourly_pay} value.
     */
    float getHourlyPay();

    /**
     * Get the {@code day_of_week} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getDayOfWeek();

    /**
     * Get the {@code day_of_month} value.
     */
    int getDayOfMonth();

    /**
     * Get the {@code month_name} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getMonthName();

    /**
     * Get the {@code year} value.
     */
    int getYear();

    /**
     * Get the {@code num_hrs_shift} value.
     * Can be {@code null}.
     */
    @Nullable
    Float getNumHrsShift();

    /**
     * Get the {@code gross_pay} value.
     * Can be {@code null}.
     */
    @Nullable
    Float getGrossPay();
}
