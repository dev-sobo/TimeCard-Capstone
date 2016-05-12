package com.example.ian.timecardcapstone.calender;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ian.timecardcapstone.R;
import com.example.ian.timecardcapstone.provider.shift.ShiftColumns;
import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.Map;

import hirondelle.date4j.DateTime;


public class CalendarGridAdapter extends CaldroidGridAdapter implements ShiftsFragment.CursorLoadedListener<Cursor> {
    private static final String LOG_TAG = CalendarGridAdapter.class.getSimpleName();
    private Cursor mCursor;
    private Context mContext;

    public CalendarGridAdapter(Context context, int month, int year, Map<String, Object> caldroidData,
                               Map<String, Object> extraData) {
        super(context, month, year, caldroidData, extraData);
        mContext = context;

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
        TextView shiftDataText = (TextView) cellView.findViewById(R.id.shiftDataText);

        DateTime dateTime = this.datetimeList.get(position);
        Resources resources = context.getResources();

        dateTextView.setText(dateTime.getDay().toString());
            shiftDataText.setText("");




        if (dateTime.getMonth() != month) {
            dateTextView.setTextColor(resources.getColor(R.color.caldroid_darker_gray));
            cellView.setBackgroundColor(resources.getColor(R.color.caldroid_gray));
        }

        //shiftDataText.setText("this is a really long sentence to see what happens when something super long is on here");

        return cellView;
    }

   /* private String getShiftText()  {
        Map<String, Object> extraData = getExtraData();
        if (extraData.isEmpty()) {
           Log.e(LOG_TAG, "EXTRADATA IS EMPTY");
            return "empty";
        }
        Cursor shiftCursor = (Cursor) extraData.get(ShiftsFragment.CURSOR_EXTRA);
        shiftCursor.moveToFirst();

        return shiftCursor.getString(shiftCursor.getColumnIndex(ShiftColumns.START_TIME_HHMM));
    }*/



    @Override
    public void onCursorLoaded(Cursor cursor) {
        cursor.moveToFirst();
        String start_time = cursor.getString(cursor.getColumnIndex(ShiftColumns.START_TIME_HHMM));
        String end_time = cursor.getString(cursor.getColumnIndex(ShiftColumns.END_TIME_HHMM));
        LayoutInflater inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View cellView =  inflater.inflate(R.layout.cellview, null);
        TextView shiftDataText = (TextView) cellView.findViewById(R.id.shiftDataText);
        shiftDataText.setText(start_time + "     " + end_time);
    }

}
