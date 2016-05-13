package com.example.ian.timecardcapstone.calender;

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


public class ShiftsFragment extends CaldroidFragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int LOADER_ID = 1;
    private static final String LOG_TAG = CaldroidFragment.class.getSimpleName();
    public static  String CURSOR_EXTRA = "cursor_extra";
    private CursorLoadedListener<Cursor> mCursorLoadedListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);


        getLoaderManager().initLoader(LOADER_ID, null, this);




        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader shiftsLoader = new CursorLoader(getContext(), ShiftColumns.CONTENT_URI,
                new String[] {ShiftColumns.START_TIME_HHMM, ShiftColumns.END_TIME_HHMM, ShiftColumns.DAY_OF_MONTH,
                        ShiftColumns.MONTH_NAME, ShiftColumns.NUM_HRS_SHIFT, ShiftColumns.GROSS_PAY},
                null, null, null
                );
        Log.w(LOG_TAG, "LOADER CREATED");
        return shiftsLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.e(LOG_TAG, "LOADER DATA: " + DatabaseUtils.dumpCursorToString(data));
       /* extraData.put(CURSOR_EXTRA, data);
        refreshView();*/
        if (data.moveToFirst()) {
            //data.moveToLast();
//data.getInt(data.getColumnIndex(ShiftColumns.MONTH_NAME)
          //  data.getInt(data.getColumnIndex(ShiftColumns.YEAR))
           // int month = Integer.parseInt(data.getString(data.getColumnIndex(ShiftColumns.MONTH_NAME)));
            //int year = data.getInt(data.getColumnIndex(ShiftColumns.YEAR));
            mCursorLoadedListener = (CursorLoadedListener<Cursor>) getNewDatesGridAdapter(getMonth(), 2016);
            mCursorLoadedListener.onCursorLoaded(data);
        }

    refreshView();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        CalendarGridAdapter calendarGridAdapter = new CalendarGridAdapter(getActivity(), month, year, getCaldroidData(), extraData);
       /* synchronized (mCalendarGridAdapter) {
            try {
                //mCalendarGridAdapter.wait();
                return mCalendarGridAdapter;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            return calendarGridAdapter;
        }


    public interface CursorLoadedListener<Cursor> {
        void onCursorLoaded(Cursor cursor);
    }

    public void setCursorLoadedListener(CursorLoadedListener<Cursor> listener) {
        mCursorLoadedListener = listener;
    }

}
