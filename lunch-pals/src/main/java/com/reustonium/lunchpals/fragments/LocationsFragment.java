package com.reustonium.lunchpals.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.models.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class LocationsFragment extends Fragment {

    View rootView;
    ProgressBar progressBar;
    AutoCompleteTextView autoCompleteTextView;
    ListView listView;
    LocationAdapter locationAdapter;
    ArrayList<Location> locations;

    public LocationsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_locations, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.location_progressbar);
        autoCompleteTextView = (AutoCompleteTextView) rootView.findViewById(R.id.location_autoComplete);
        listView = (ListView) rootView.findViewById(R.id.location_listView);
        locations = new ArrayList<Location>();
        locationAdapter = new LocationAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1,  locations);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoadingUI(true);

        //TODO Preload AutoComplete
        autoCompleteTextView.setAdapter(locationAdapter);
        listView.setAdapter(locationAdapter);
        updateLocations();
    }

    private void updateLocations(){
        //TODO update listView with VOTES from DB
        final ParseQuery<Location> query = ParseQuery.getQuery(Location.class);

        locationAdapter.clear();
        query.findInBackground(new FindCallback<Location>() {
            @Override
            public void done(List<Location> locations, ParseException e) {
                locationAdapter.addAll(locations);
                locationAdapter.notifyDataSetChanged();
                showLoadingUI(false);
            }
        });
    }
    private void showLoadingUI(boolean show) {
        if(show){
            progressBar.setVisibility(View.VISIBLE);
            autoCompleteTextView.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            autoCompleteTextView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
    }

    class LocationAdapter extends ArrayAdapter<Location>{

        public LocationAdapter(Context context, int layout, ArrayList<Location> locations) {
            super(context, layout , locations);
        }

    }
}
