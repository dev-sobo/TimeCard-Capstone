package com.example.ian.timecardcapstone.calendarrosterappsshifts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ian.timecardcapstone.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

public class RosterappsFragment extends CaldroidFragment {


    public RosterappsFragment() {


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
        return rootView;
    }

    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        RosterAppsGridAdapter rosterAppsGridAdapter = new RosterAppsGridAdapter(getActivity(), month, year, getCaldroidData(), getExtraData());
        rosterAppsGridAdapter.setCursorToFirst();
        return rosterAppsGridAdapter;
    }
}
