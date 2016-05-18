package com.example.ian.timecardcapstone.provider.rosterappsdata;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.example.ian.timecardcapstone.provider.base.AbstractSelection;

/**
 * Selection for the {@code rosterappsdata} table.
 */
public class RosterappsdataSelection extends AbstractSelection<RosterappsdataSelection> {
    @Override
    protected Uri baseUri() {
        return RosterappsdataColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code RosterappsdataCursor} object, which is positioned before the first entry, or null.
     */
    public RosterappsdataCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new RosterappsdataCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public RosterappsdataCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code RosterappsdataCursor} object, which is positioned before the first entry, or null.
     */
    public RosterappsdataCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new RosterappsdataCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public RosterappsdataCursor query(Context context) {
        return query(context, null);
    }


    public RosterappsdataSelection id(long... value) {
        addEquals("rosterappsdata." + RosterappsdataColumns._ID, toObjectArray(value));
        return this;
    }

    public RosterappsdataSelection idNot(long... value) {
        addNotEquals("rosterappsdata." + RosterappsdataColumns._ID, toObjectArray(value));
        return this;
    }

    public RosterappsdataSelection orderById(boolean desc) {
        orderBy("rosterappsdata." + RosterappsdataColumns._ID, desc);
        return this;
    }

    public RosterappsdataSelection orderById() {
        return orderById(false);
    }

    public RosterappsdataSelection rosterappsData(String... value) {
        addEquals(RosterappsdataColumns.ROSTERAPPS_DATA, value);
        return this;
    }

    public RosterappsdataSelection rosterappsDataNot(String... value) {
        addNotEquals(RosterappsdataColumns.ROSTERAPPS_DATA, value);
        return this;
    }

    public RosterappsdataSelection rosterappsDataLike(String... value) {
        addLike(RosterappsdataColumns.ROSTERAPPS_DATA, value);
        return this;
    }

    public RosterappsdataSelection rosterappsDataContains(String... value) {
        addContains(RosterappsdataColumns.ROSTERAPPS_DATA, value);
        return this;
    }

    public RosterappsdataSelection rosterappsDataStartsWith(String... value) {
        addStartsWith(RosterappsdataColumns.ROSTERAPPS_DATA, value);
        return this;
    }

    public RosterappsdataSelection rosterappsDataEndsWith(String... value) {
        addEndsWith(RosterappsdataColumns.ROSTERAPPS_DATA, value);
        return this;
    }

    public RosterappsdataSelection orderByRosterappsData(boolean desc) {
        orderBy(RosterappsdataColumns.ROSTERAPPS_DATA, desc);
        return this;
    }

    public RosterappsdataSelection orderByRosterappsData() {
        orderBy(RosterappsdataColumns.ROSTERAPPS_DATA, false);
        return this;
    }

    public RosterappsdataSelection colorOfShifts(String... value) {
        addEquals(RosterappsdataColumns.COLOR_OF_SHIFTS, value);
        return this;
    }

    public RosterappsdataSelection colorOfShiftsNot(String... value) {
        addNotEquals(RosterappsdataColumns.COLOR_OF_SHIFTS, value);
        return this;
    }

    public RosterappsdataSelection colorOfShiftsLike(String... value) {
        addLike(RosterappsdataColumns.COLOR_OF_SHIFTS, value);
        return this;
    }

    public RosterappsdataSelection colorOfShiftsContains(String... value) {
        addContains(RosterappsdataColumns.COLOR_OF_SHIFTS, value);
        return this;
    }

    public RosterappsdataSelection colorOfShiftsStartsWith(String... value) {
        addStartsWith(RosterappsdataColumns.COLOR_OF_SHIFTS, value);
        return this;
    }

    public RosterappsdataSelection colorOfShiftsEndsWith(String... value) {
        addEndsWith(RosterappsdataColumns.COLOR_OF_SHIFTS, value);
        return this;
    }

    public RosterappsdataSelection orderByColorOfShifts(boolean desc) {
        orderBy(RosterappsdataColumns.COLOR_OF_SHIFTS, desc);
        return this;
    }

    public RosterappsdataSelection orderByColorOfShifts() {
        orderBy(RosterappsdataColumns.COLOR_OF_SHIFTS, false);
        return this;
    }
}
