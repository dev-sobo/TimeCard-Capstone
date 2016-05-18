package com.example.ian.timecardcapstone.calenderlocalshifts;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ian.timecardcapstone.R;
import com.example.ian.timecardcapstone.provider.shift.ShiftColumns;
import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.Locale;
import java.util.Map;

import hirondelle.date4j.DateTime;


@SuppressWarnings("deprecation")
public class CalendarGridAdapter extends CaldroidGridAdapter {
    private static final String LOG_TAG = CalendarGridAdapter.class.getSimpleName();
    private Cursor mCursor;
    private Context mContext;

    public CalendarGridAdapter(Context context, int month, int year, Map<String, Object> caldroidData,
                               Map<String, Object> extraData) {
        super(context, month, year, caldroidData, extraData);
        mContext = context;
        mCursor = mContext.getContentResolver().query(ShiftColumns.CONTENT_URI, new String[]{ShiftColumns.START_TIME_HHMM, ShiftColumns.END_TIME_HHMM, ShiftColumns.DAY_OF_MONTH,
                ShiftColumns.MONTH_NAME, ShiftColumns.NUM_HRS_SHIFT, ShiftColumns.GROSS_PAY}, null, null, null);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cellView = convertView;


        if (convertView == null) {
            cellView = inflater.inflate(R.layout.cellview, null);
        }

        TextView dateTextView = (TextView) cellView.findViewById(R.id.dateText);
        TextView startTimeText = (TextView) cellView.findViewById(R.id.startTimeText);
        TextView endTimeText = (TextView) cellView.findViewById(R.id.endTimeText);
        TextView numHoursWrkedText = (TextView) cellView.findViewById(R.id.numOfHoursWorkedText);
        TextView grossPayText = (TextView) cellView.findViewById(R.id.grossPayText);


        DateTime dateTime = this.datetimeList.get(position);
        Resources resources = context.getResources();
        if (mCursor == null) {

            startTimeText.setText("");
        }
        dateTextView.setText(String.format(Locale.getDefault(),"%td", dateTime.getDay().longValue()));


        if (dateTime.getMonth() != month) {
            dateTextView.setTextColor(resources.getColor(R.color.caldroid_darker_gray));
            cellView.setBackgroundColor(resources.getColor(R.color.caldroid_gray));
        }
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                int cursorToday = mCursor.getInt(mCursor.getColumnIndex(ShiftColumns.DAY_OF_MONTH));
                Log.e(LOG_TAG, "TODAY: " + getToday().getDay());
                if (cursorToday == dateTime.getDay()) {
                    dateTextView.setText(String.format(Locale.getDefault(),"%td", dateTime.getDay().longValue()));
                    dateTextView.setContentDescription(dateTime.getDay().toString());

                    startTimeText.setText("START TIME: " + mCursor.getString(mCursor.getColumnIndex(ShiftColumns.START_TIME_HHMM)));
                    startTimeText.setContentDescription("START TIME: " + mCursor.getString(mCursor.getColumnIndex(ShiftColumns.START_TIME_HHMM)));
                    endTimeText.setText("END TIME: " + mCursor.getString(mCursor.getColumnIndex(ShiftColumns.END_TIME_HHMM)));
                    endTimeText.setContentDescription("END TIME: " + mCursor.getString(mCursor.getColumnIndex(ShiftColumns.END_TIME_HHMM)));
                    numHoursWrkedText.setText("# HRS WRKD: " + mCursor.getString(mCursor.getColumnIndex(ShiftColumns.NUM_HRS_SHIFT)));
                    numHoursWrkedText.setContentDescription("# HRS WRKD: " + mCursor.getString(mCursor.getColumnIndex(ShiftColumns.NUM_HRS_SHIFT)));
                    grossPayText.setText("GROSS PAY: " + mCursor.getString(mCursor.getColumnIndex(ShiftColumns.GROSS_PAY)));
                    grossPayText.setContentDescription("GROSS PAY: " + mCursor.getString(mCursor.getColumnIndex(ShiftColumns.GROSS_PAY)));
                }
            }
        }
        return cellView;
    }


}
