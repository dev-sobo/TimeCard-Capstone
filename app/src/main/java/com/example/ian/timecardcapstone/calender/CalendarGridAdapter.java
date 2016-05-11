package com.example.ian.timecardcapstone.calender;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ian.timecardcapstone.R;
import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.Map;

import hirondelle.date4j.DateTime;


public class CalendarGridAdapter extends CaldroidGridAdapter  {
    private static final String LOG_TAG = CalendarGridAdapter.class.getSimpleName();

    public CalendarGridAdapter(Context context, int month, int year, Map<String, Object> caldroidData,
                               Map<String, Object> extraData) {
        super(context, month, year, caldroidData, extraData);


        /*

        if (!getExtraData().isEmpty() && getExtraData().containsKey(ShiftsFragment.CURSOR_EXTRA)) {

        }
*/
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
        try {
            shiftDataText.setText(getShiftText());
        } catch (NoSuchFieldException e) {
            Log.e(LOG_TAG, e.getMessage());

        }

        if (dateTime.getMonth() != month) {
            dateTextView.setTextColor(resources.getColor(R.color.caldroid_darker_gray));
            cellView.setBackgroundColor(resources.getColor(R.color.caldroid_gray));
        }
        //shiftDataText.setText("this is a really long sentence to see what happens when something super long is on here");

        return cellView;
    }

    private String getShiftText() throws NoSuchFieldException {
        Map<String, Object> extraData = getExtraData();
        if (extraData.isEmpty()) {
            throw new NoSuchFieldException();
        }
        Cursor shiftCursor = (Cursor) extraData.get(ShiftsFragment.CURSOR_EXTRA);

        return "hi";
    }


}
