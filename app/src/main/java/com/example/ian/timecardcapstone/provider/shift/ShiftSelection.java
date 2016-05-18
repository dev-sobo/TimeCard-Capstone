package com.example.ian.timecardcapstone.provider.shift;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.example.ian.timecardcapstone.provider.base.AbstractSelection;

/**
 * Selection for the {@code shift} table.
 */
public class ShiftSelection extends AbstractSelection<ShiftSelection> {
    @Override
    protected Uri baseUri() {
        return ShiftColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ShiftCursor} object, which is positioned before the first entry, or null.
     */
    public ShiftCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ShiftCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public ShiftCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ShiftCursor} object, which is positioned before the first entry, or null.
     */
    public ShiftCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ShiftCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public ShiftCursor query(Context context) {
        return query(context, null);
    }


    public ShiftSelection id(long... value) {
        addEquals("shift." + ShiftColumns._ID, toObjectArray(value));
        return this;
    }

    public ShiftSelection idNot(long... value) {
        addNotEquals("shift." + ShiftColumns._ID, toObjectArray(value));
        return this;
    }

    public ShiftSelection orderById(boolean desc) {
        orderBy("shift." + ShiftColumns._ID, desc);
        return this;
    }

    public ShiftSelection orderById() {
        return orderById(false);
    }

    public ShiftSelection startTimeHhmm(String... value) {
        addEquals(ShiftColumns.START_TIME_HHMM, value);
        return this;
    }

    public ShiftSelection startTimeHhmmNot(String... value) {
        addNotEquals(ShiftColumns.START_TIME_HHMM, value);
        return this;
    }

    public ShiftSelection startTimeHhmmLike(String... value) {
        addLike(ShiftColumns.START_TIME_HHMM, value);
        return this;
    }

    public ShiftSelection startTimeHhmmContains(String... value) {
        addContains(ShiftColumns.START_TIME_HHMM, value);
        return this;
    }

    public ShiftSelection startTimeHhmmStartsWith(String... value) {
        addStartsWith(ShiftColumns.START_TIME_HHMM, value);
        return this;
    }

    public ShiftSelection startTimeHhmmEndsWith(String... value) {
        addEndsWith(ShiftColumns.START_TIME_HHMM, value);
        return this;
    }

    public ShiftSelection orderByStartTimeHhmm(boolean desc) {
        orderBy(ShiftColumns.START_TIME_HHMM, desc);
        return this;
    }

    public ShiftSelection orderByStartTimeHhmm() {
        orderBy(ShiftColumns.START_TIME_HHMM, false);
        return this;
    }

    public ShiftSelection endTimeHhmm(String... value) {
        addEquals(ShiftColumns.END_TIME_HHMM, value);
        return this;
    }

    public ShiftSelection endTimeHhmmNot(String... value) {
        addNotEquals(ShiftColumns.END_TIME_HHMM, value);
        return this;
    }

    public ShiftSelection endTimeHhmmLike(String... value) {
        addLike(ShiftColumns.END_TIME_HHMM, value);
        return this;
    }

    public ShiftSelection endTimeHhmmContains(String... value) {
        addContains(ShiftColumns.END_TIME_HHMM, value);
        return this;
    }

    public ShiftSelection endTimeHhmmStartsWith(String... value) {
        addStartsWith(ShiftColumns.END_TIME_HHMM, value);
        return this;
    }

    public ShiftSelection endTimeHhmmEndsWith(String... value) {
        addEndsWith(ShiftColumns.END_TIME_HHMM, value);
        return this;
    }

    public ShiftSelection orderByEndTimeHhmm(boolean desc) {
        orderBy(ShiftColumns.END_TIME_HHMM, desc);
        return this;
    }

    public ShiftSelection orderByEndTimeHhmm() {
        orderBy(ShiftColumns.END_TIME_HHMM, false);
        return this;
    }

    public ShiftSelection startTimeUnix(int... value) {
        addEquals(ShiftColumns.START_TIME_UNIX, toObjectArray(value));
        return this;
    }

    public ShiftSelection startTimeUnixNot(int... value) {
        addNotEquals(ShiftColumns.START_TIME_UNIX, toObjectArray(value));
        return this;
    }

    public ShiftSelection startTimeUnixGt(int value) {
        addGreaterThan(ShiftColumns.START_TIME_UNIX, value);
        return this;
    }

    public ShiftSelection startTimeUnixGtEq(int value) {
        addGreaterThanOrEquals(ShiftColumns.START_TIME_UNIX, value);
        return this;
    }

    public ShiftSelection startTimeUnixLt(int value) {
        addLessThan(ShiftColumns.START_TIME_UNIX, value);
        return this;
    }

    public ShiftSelection startTimeUnixLtEq(int value) {
        addLessThanOrEquals(ShiftColumns.START_TIME_UNIX, value);
        return this;
    }

    public ShiftSelection orderByStartTimeUnix(boolean desc) {
        orderBy(ShiftColumns.START_TIME_UNIX, desc);
        return this;
    }

    public ShiftSelection orderByStartTimeUnix() {
        orderBy(ShiftColumns.START_TIME_UNIX, false);
        return this;
    }

    public ShiftSelection endTimeUnix(Integer... value) {
        addEquals(ShiftColumns.END_TIME_UNIX, value);
        return this;
    }

    public ShiftSelection endTimeUnixNot(Integer... value) {
        addNotEquals(ShiftColumns.END_TIME_UNIX, value);
        return this;
    }

    public ShiftSelection endTimeUnixGt(int value) {
        addGreaterThan(ShiftColumns.END_TIME_UNIX, value);
        return this;
    }

    public ShiftSelection endTimeUnixGtEq(int value) {
        addGreaterThanOrEquals(ShiftColumns.END_TIME_UNIX, value);
        return this;
    }

    public ShiftSelection endTimeUnixLt(int value) {
        addLessThan(ShiftColumns.END_TIME_UNIX, value);
        return this;
    }

    public ShiftSelection endTimeUnixLtEq(int value) {
        addLessThanOrEquals(ShiftColumns.END_TIME_UNIX, value);
        return this;
    }

    public ShiftSelection orderByEndTimeUnix(boolean desc) {
        orderBy(ShiftColumns.END_TIME_UNIX, desc);
        return this;
    }

    public ShiftSelection orderByEndTimeUnix() {
        orderBy(ShiftColumns.END_TIME_UNIX, false);
        return this;
    }

    public ShiftSelection hourlyPay(float... value) {
        addEquals(ShiftColumns.HOURLY_PAY, toObjectArray(value));
        return this;
    }

    public ShiftSelection hourlyPayNot(float... value) {
        addNotEquals(ShiftColumns.HOURLY_PAY, toObjectArray(value));
        return this;
    }

    public ShiftSelection hourlyPayGt(float value) {
        addGreaterThan(ShiftColumns.HOURLY_PAY, value);
        return this;
    }

    public ShiftSelection hourlyPayGtEq(float value) {
        addGreaterThanOrEquals(ShiftColumns.HOURLY_PAY, value);
        return this;
    }

    public ShiftSelection hourlyPayLt(float value) {
        addLessThan(ShiftColumns.HOURLY_PAY, value);
        return this;
    }

    public ShiftSelection hourlyPayLtEq(float value) {
        addLessThanOrEquals(ShiftColumns.HOURLY_PAY, value);
        return this;
    }

    public ShiftSelection orderByHourlyPay(boolean desc) {
        orderBy(ShiftColumns.HOURLY_PAY, desc);
        return this;
    }

    public ShiftSelection orderByHourlyPay() {
        orderBy(ShiftColumns.HOURLY_PAY, false);
        return this;
    }

    public ShiftSelection dayOfWeek(String... value) {
        addEquals(ShiftColumns.DAY_OF_WEEK, value);
        return this;
    }

    public ShiftSelection dayOfWeekNot(String... value) {
        addNotEquals(ShiftColumns.DAY_OF_WEEK, value);
        return this;
    }

    public ShiftSelection dayOfWeekLike(String... value) {
        addLike(ShiftColumns.DAY_OF_WEEK, value);
        return this;
    }

    public ShiftSelection dayOfWeekContains(String... value) {
        addContains(ShiftColumns.DAY_OF_WEEK, value);
        return this;
    }

    public ShiftSelection dayOfWeekStartsWith(String... value) {
        addStartsWith(ShiftColumns.DAY_OF_WEEK, value);
        return this;
    }

    public ShiftSelection dayOfWeekEndsWith(String... value) {
        addEndsWith(ShiftColumns.DAY_OF_WEEK, value);
        return this;
    }

    public ShiftSelection orderByDayOfWeek(boolean desc) {
        orderBy(ShiftColumns.DAY_OF_WEEK, desc);
        return this;
    }

    public ShiftSelection orderByDayOfWeek() {
        orderBy(ShiftColumns.DAY_OF_WEEK, false);
        return this;
    }

    public ShiftSelection dayOfMonth(int... value) {
        addEquals(ShiftColumns.DAY_OF_MONTH, toObjectArray(value));
        return this;
    }

    public ShiftSelection dayOfMonthNot(int... value) {
        addNotEquals(ShiftColumns.DAY_OF_MONTH, toObjectArray(value));
        return this;
    }

    public ShiftSelection dayOfMonthGt(int value) {
        addGreaterThan(ShiftColumns.DAY_OF_MONTH, value);
        return this;
    }

    public ShiftSelection dayOfMonthGtEq(int value) {
        addGreaterThanOrEquals(ShiftColumns.DAY_OF_MONTH, value);
        return this;
    }

    public ShiftSelection dayOfMonthLt(int value) {
        addLessThan(ShiftColumns.DAY_OF_MONTH, value);
        return this;
    }

    public ShiftSelection dayOfMonthLtEq(int value) {
        addLessThanOrEquals(ShiftColumns.DAY_OF_MONTH, value);
        return this;
    }

    public ShiftSelection orderByDayOfMonth(boolean desc) {
        orderBy(ShiftColumns.DAY_OF_MONTH, desc);
        return this;
    }

    public ShiftSelection orderByDayOfMonth() {
        orderBy(ShiftColumns.DAY_OF_MONTH, false);
        return this;
    }

    public ShiftSelection monthName(String... value) {
        addEquals(ShiftColumns.MONTH_NAME, value);
        return this;
    }

    public ShiftSelection monthNameNot(String... value) {
        addNotEquals(ShiftColumns.MONTH_NAME, value);
        return this;
    }

    public ShiftSelection monthNameLike(String... value) {
        addLike(ShiftColumns.MONTH_NAME, value);
        return this;
    }

    public ShiftSelection monthNameContains(String... value) {
        addContains(ShiftColumns.MONTH_NAME, value);
        return this;
    }

    public ShiftSelection monthNameStartsWith(String... value) {
        addStartsWith(ShiftColumns.MONTH_NAME, value);
        return this;
    }

    public ShiftSelection monthNameEndsWith(String... value) {
        addEndsWith(ShiftColumns.MONTH_NAME, value);
        return this;
    }

    public ShiftSelection orderByMonthName(boolean desc) {
        orderBy(ShiftColumns.MONTH_NAME, desc);
        return this;
    }

    public ShiftSelection orderByMonthName() {
        orderBy(ShiftColumns.MONTH_NAME, false);
        return this;
    }

    public ShiftSelection year(int... value) {
        addEquals(ShiftColumns.YEAR, toObjectArray(value));
        return this;
    }

    public ShiftSelection yearNot(int... value) {
        addNotEquals(ShiftColumns.YEAR, toObjectArray(value));
        return this;
    }

    public ShiftSelection yearGt(int value) {
        addGreaterThan(ShiftColumns.YEAR, value);
        return this;
    }

    public ShiftSelection yearGtEq(int value) {
        addGreaterThanOrEquals(ShiftColumns.YEAR, value);
        return this;
    }

    public ShiftSelection yearLt(int value) {
        addLessThan(ShiftColumns.YEAR, value);
        return this;
    }

    public ShiftSelection yearLtEq(int value) {
        addLessThanOrEquals(ShiftColumns.YEAR, value);
        return this;
    }

    public ShiftSelection orderByYear(boolean desc) {
        orderBy(ShiftColumns.YEAR, desc);
        return this;
    }

    public ShiftSelection orderByYear() {
        orderBy(ShiftColumns.YEAR, false);
        return this;
    }

    public ShiftSelection numHrsShift(Float... value) {
        addEquals(ShiftColumns.NUM_HRS_SHIFT, value);
        return this;
    }

    public ShiftSelection numHrsShiftNot(Float... value) {
        addNotEquals(ShiftColumns.NUM_HRS_SHIFT, value);
        return this;
    }

    public ShiftSelection numHrsShiftGt(float value) {
        addGreaterThan(ShiftColumns.NUM_HRS_SHIFT, value);
        return this;
    }

    public ShiftSelection numHrsShiftGtEq(float value) {
        addGreaterThanOrEquals(ShiftColumns.NUM_HRS_SHIFT, value);
        return this;
    }

    public ShiftSelection numHrsShiftLt(float value) {
        addLessThan(ShiftColumns.NUM_HRS_SHIFT, value);
        return this;
    }

    public ShiftSelection numHrsShiftLtEq(float value) {
        addLessThanOrEquals(ShiftColumns.NUM_HRS_SHIFT, value);
        return this;
    }

    public ShiftSelection orderByNumHrsShift(boolean desc) {
        orderBy(ShiftColumns.NUM_HRS_SHIFT, desc);
        return this;
    }

    public ShiftSelection orderByNumHrsShift() {
        orderBy(ShiftColumns.NUM_HRS_SHIFT, false);
        return this;
    }

    public ShiftSelection grossPay(Float... value) {
        addEquals(ShiftColumns.GROSS_PAY, value);
        return this;
    }

    public ShiftSelection grossPayNot(Float... value) {
        addNotEquals(ShiftColumns.GROSS_PAY, value);
        return this;
    }

    public ShiftSelection grossPayGt(float value) {
        addGreaterThan(ShiftColumns.GROSS_PAY, value);
        return this;
    }

    public ShiftSelection grossPayGtEq(float value) {
        addGreaterThanOrEquals(ShiftColumns.GROSS_PAY, value);
        return this;
    }

    public ShiftSelection grossPayLt(float value) {
        addLessThan(ShiftColumns.GROSS_PAY, value);
        return this;
    }

    public ShiftSelection grossPayLtEq(float value) {
        addLessThanOrEquals(ShiftColumns.GROSS_PAY, value);
        return this;
    }

    public ShiftSelection orderByGrossPay(boolean desc) {
        orderBy(ShiftColumns.GROSS_PAY, desc);
        return this;
    }

    public ShiftSelection orderByGrossPay() {
        orderBy(ShiftColumns.GROSS_PAY, false);
        return this;
    }
}
