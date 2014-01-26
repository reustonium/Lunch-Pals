package com.reustonium.lunchpals.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;
import com.reustonium.lunchpals.Nudge;
import com.reustonium.lunchpals.R;



public class StatsFragment extends Fragment {

    View rootView;
    TextView nameTV;
    TextView fromTV;
    TextView receivedTV;

    public StatsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_stats, container, false);
        nameTV = (TextView)rootView.findViewById(R.id.stats_username);
        fromTV = (TextView)rootView.findViewById(R.id.stats_sent);
        receivedTV = (TextView)rootView.findViewById(R.id.stats_nudged);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Nudge nudge = new Nudge();
        ParseUser user = ParseUser.getCurrentUser();
        int sent = nudge.getNudgesFrom(user).size();
        int received = nudge.getNudgesTo(user).size();

        nameTV.setText(user.getUsername());
        fromTV.setText(String.valueOf(sent));
        receivedTV.setText(String.valueOf(received));
    }
}
