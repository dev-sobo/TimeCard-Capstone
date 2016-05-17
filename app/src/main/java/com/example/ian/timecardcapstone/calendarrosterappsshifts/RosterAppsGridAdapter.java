package com.example.ian.timecardcapstone.calendarrosterappsshifts;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ian.timecardcapstone.R;
import com.example.ian.timecardcapstone.provider.rosterappsdata.RosterappsdataColumns;
import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.Map;

import hirondelle.date4j.DateTime;

/**
 * Created by ian on 5/13/2016.
 */
public class RosterAppsGridAdapter extends CaldroidGridAdapter {
    private static final String LOG_TAG = RosterAppsGridAdapter.class.getSimpleName();
    private Cursor mCursor;

    public RosterAppsGridAdapter(Context context, int month, int year, Map<String, Object> caldroidData, Map<String, Object> extraData) {
        super(context, month, year, caldroidData, extraData);
        mCursor = context.getContentResolver().query(RosterappsdataColumns.CONTENT_URI,null, null, null, null);
      //  Log.e(LOG_TAG, DatabaseUtils.dumpCursorToString(mCursor));
        Log.i(LOG_TAG, "I IS INITIALIZED");
        Log.e(LOG_TAG, "CURSOR: " + DatabaseUtils.dumpCursorToString(mCursor));
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rosterAppsCell = convertView;



        if (convertView == null) {
            rosterAppsCell = inflater.inflate(R.layout.rosterappscellview, null);
        }
        TextView rosterAppsData = (TextView) rosterAppsCell.findViewById(R.id.rosterAppsData);
        TextView textViewDate = (TextView) rosterAppsCell.findViewById(R.id.rosterAppsDate);
        DateTime dateTime = this.datetimeList.get(position);
        Resources resources = context.getResources();
        if(dateTime.getMonth() != month) {
            rosterAppsCell.setBackgroundColor(resources.getColor(R.color.caldroid_gray));
            textViewDate.setTextColor(resources.getColor(R.color.caldroid_darker_gray));
        }

        textViewDate.setText(dateTime.getDay().toString());

        if (mCursor != null && dateTime.getMonth() == month) {

               if (mCursor.moveToPosition(position)) {

                    rosterAppsData.setText(mCursor.getString(mCursor.getColumnIndex(RosterappsdataColumns.ROSTERAPPS_DATA)));

            }
        }
        rosterAppsData.setVisibility(View.GONE);

        return rosterAppsCell;
    }
    public void setCursorToFirst(){
        mCursor.moveToFirst();

    }


}


