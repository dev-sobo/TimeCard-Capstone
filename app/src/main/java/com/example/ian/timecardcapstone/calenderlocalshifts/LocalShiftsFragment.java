package com.example.ian.timecardcapstone.calenderlocalshifts;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ian.timecardcapstone.provider.shift.ShiftColumns;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

/* need to rework entire local shift system.
   User should be able to clock in, and clock out even upon the app being closed.
   This may require the clocking in and clocking out information to be saved to a file.
   The clocking in and clocking out system should also take into account if the shift being worked currently
   is a supervisor shift or not.

   Is it possible to merge both of the calendar systems into one? How would I go about doing this.... and how to represent it?

*/
public class LocalShiftsFragment extends CaldroidFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 1;
    private static final String LOG_TAG = CaldroidFragment.class.getSimpleName();
    public static String CURSOR_EXTRA = "cursor_extra";
   // private CursorLoadedListener<Cursor> mCursorLoadedListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);


        getLoaderManager().initLoader(LOADER_ID, null, this);


        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader shiftsLoader = new CursorLoader(getContext(), ShiftColumns.CONTENT_URI,
                new String[]{ShiftColumns.START_TIME_HHMM, ShiftColumns.END_TIME_HHMM, ShiftColumns.DAY_OF_MONTH,
                        ShiftColumns.MONTH_NAME, ShiftColumns.NUM_HRS_SHIFT, ShiftColumns.GROSS_PAY},
                null, null, null
        );
        Log.w(LOG_TAG, "LOADER CREATED");
        return shiftsLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.e(LOG_TAG, "LOADER DATA: " + DatabaseUtils.dumpCursorToString(data));

        data.moveToFirst();


        refreshView();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        CalendarGridAdapter calendarGridAdapter = new CalendarGridAdapter(getActivity(), month, year, getCaldroidData(), extraData);


        return calendarGridAdapter;
    }






}

/**
 *interface related shit
 *
 public interface CursorLoadedListener<Cursor> {
 void onCursorLoaded(Cursor cursor);
 }
 public void setCursorLoadedListener(CursorLoadedListener<Cursor> listener) {
 mCursorLoadedListener = listener;
 }

 if (data.moveToFirst()) {
 mCursorLoadedListener = (CursorLoadedListener<Cursor>) getNewDatesGridAdapter(getMonth(), 2016);
 mCursorLoadedListener.onCursorLoaded(data);
 }
 */

