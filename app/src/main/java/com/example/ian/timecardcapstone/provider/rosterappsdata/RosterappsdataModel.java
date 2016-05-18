package com.example.ian.timecardcapstone.provider.rosterappsdata;

import com.example.ian.timecardcapstone.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Simple table for rosterapps data.
 */
public interface RosterappsdataModel extends BaseModel {

    /**
     * Get the {@code rosterapps_data} value.
     * Can be {@code null}.
     */
    @Nullable
    String getRosterappsData();

    /**
     * Get the {@code color_of_shifts} value.
     * Can be {@code null}.
     */
    @Nullable
    String getColorOfShifts();
}
