package com.example.ian.timecardcapstone.calendarrosterappsshifts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.example.ian.timecardcapstone.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

public class RosterappsFragment extends CaldroidFragment {

    public static TextView monthTitleTextView;
    public static GridView weekdayGridView;
    protected static int screenToPass;
    protected static int screenHeight = 0;
    private FrameLayout rosterAppsContainer;
    private int _monthTitleTextViewSize = 0;
    private int _weekdayGridViewSize = 0;

    public RosterappsFragment() {
    }

    public static int getHeights() {
        screenToPass = screenHeight - weekdayGridView.getHeight() - monthTitleTextView.getHeight();

        return screenToPass;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        View littleView = inflater.inflate(R.layout.fragment_rosterapps, container, false);
        // Inflate the layout for this fragment

        monthTitleTextView = getMonthTitleTextView();
        weekdayGridView = getWeekdayGridView();

        monthTitleTextView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int OldBottom) {
                if (left == 0 && top == 0 && right == 0 && bottom == 0) {
                    return;
                }
                _monthTitleTextViewSize = bottom - top;
                if (_weekdayGridViewSize != 0 && _monthTitleTextViewSize != 0) {
                    screenToPass = screenHeight - _weekdayGridViewSize - _monthTitleTextViewSize;
                }
            }
        });
        weekdayGridView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int OldBottom) {
                if (left == 0 && top == 0 && right == 0 && bottom == 0) {
                    return;
                }
                _monthTitleTextViewSize = bottom - top;
                if (_weekdayGridViewSize != 0 && _monthTitleTextViewSize != 0) {
                    screenToPass = screenHeight - _weekdayGridViewSize - _monthTitleTextViewSize;
                }
            }
        });


        return rootView;
    }

    public void passScreenHeight(int passedScreenHeight) {
        screenHeight = passedScreenHeight;
    }

    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        RosterAppsGridAdapter rosterAppsGridAdapter = new RosterAppsGridAdapter(getActivity(), month, year, getCaldroidData(), getExtraData());
        rosterAppsGridAdapter.setCursorToFirst();
        return rosterAppsGridAdapter;
    }
}
