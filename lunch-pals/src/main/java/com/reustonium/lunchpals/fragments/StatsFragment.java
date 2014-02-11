package com.reustonium.lunchpals.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.reustonium.lunchpals.R;

import java.util.ArrayList;
import java.util.List;

public class StatsFragment extends Fragment {

    View rootView;
    TextView nameTV;
    TextView fromTV;
    TextView receivedTV;
    ProgressBar progress;
    LinearLayout from;
    LinearLayout to;

    public StatsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_stats, container, false);
        nameTV = (TextView)rootView.findViewById(R.id.stats_username);
        fromTV = (TextView)rootView.findViewById(R.id.stats_sent);
        receivedTV = (TextView)rootView.findViewById(R.id.stats_nudged);
        progress = (ProgressBar)rootView.findViewById(R.id.stats_progressbar);
        from = (LinearLayout)rootView.findViewById(R.id.stats_from);
        to = (LinearLayout)rootView.findViewById(R.id.stats_to);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        //Set Loading UI
        toggleLoadingUI(true);

        ParseUser user = ParseUser.getCurrentUser();
        nameTV.setText(user.getUsername());

        // From
        ParseQuery<ParseUser> innerQ = ParseUser.getQuery();
        innerQ.whereEqualTo("objectId", user.getObjectId());

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Nudge");
        query.whereMatchesQuery("fromUser", innerQ);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                fromTV.setText(String.valueOf(parseObjects.size()));
            }
        });

        // To
        ParseQuery<ParseObject> queryTo = ParseQuery.getQuery("Nudge");
        queryTo.whereMatchesQuery("toUser", innerQ);
        queryTo.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                receivedTV.setText(String.valueOf(parseObjects.size()));
                toggleLoadingUI(false);
            }
        });
    }

    private void toggleLoadingUI(boolean on){
        if(on){
            from.setVisibility(View.GONE);
            to.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
        }
        else{
            from.setVisibility(View.VISIBLE);
            to.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
        }
    }
}
