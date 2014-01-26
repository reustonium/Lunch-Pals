package com.reustonium.lunchpals.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.reustonium.lunchpals.R;


public class StatsFragment extends Fragment {

    View rootView;

    public StatsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO update stats
    }
}
