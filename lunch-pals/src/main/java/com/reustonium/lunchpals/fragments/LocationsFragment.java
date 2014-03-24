package com.reustonium.lunchpals.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.models.Location;
import com.reustonium.lunchpals.models.Vote;

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
    AutoLocationAdapter autoLocationAdapter;
    //VoteListAdapter voteAdapter;
    ArrayList<Location> locations;
    ArrayList<Vote> votes;

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
        votes = new ArrayList<Vote>();
        autoLocationAdapter = new AutoLocationAdapter(getActivity(), R.layout.fragment_locations_auto_row,  locations);
        //voteAdapter = new VoteListAdapter(getActivity(), R.layout.fragment_locations_row, votes);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoadingUI(true);

        autoCompleteTextView.setAdapter(autoLocationAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Location selected = (Location)adapterView.getAdapter().getItem(i);
                Vote v = new Vote();
                v.setUser(ParseUser.getCurrentUser());
                v.setLocation(selected);
                v.setValue(1);
                v.saveInBackground();
                Toast.makeText(getActivity(), "Voted for: " + selected.getName(), Toast.LENGTH_SHORT ).show();
                //TODO update listview
            }
        });
        //listView.setAdapter(voteAdapter);
        getLocations();
        getVotes();
        }

    private void getVotes() {
        ParseQuery<Vote> query = ParseQuery.getQuery(Vote.class);
        query.setLimit(1000);

/*        voteAdapter.clear();
        query.findInBackground(new FindCallback<Vote>() {
            @Override
            public void done(List<Vote> votes, ParseException e) {
                voteAdapter.addAll(votes);
                voteAdapter.notifyDataSetChanged();
            }
        });*/
    }

    private void getLocations(){
        final ParseQuery<Location> query = ParseQuery.getQuery(Location.class);
        query.setLimit(10000);
        autoLocationAdapter.clear();
        query.findInBackground(new FindCallback<Location>() {
            @Override
            public void done(List<Location> locations, ParseException e) {
                autoLocationAdapter.addAll(locations);
                autoLocationAdapter.notifyDataSetChanged();
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

    class AutoLocationAdapter extends ArrayAdapter<Location> implements Filterable{
        private ArrayList<Location> locations;
        private ArrayList<Location> allLocations;
        private Filter filter;

        public AutoLocationAdapter(Context context, int layout, ArrayList<Location> locations) {
            super(context, layout , locations);
            this.locations = locations;
            this.allLocations = locations;
        }

        @Override
        public int getCount() {
            return locations.size();
        }

        @Override
        public Location getItem(int position) {
            return locations.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.fragment_locations_auto_row, null);
            }

            Location l = locations.get(position);

            if (l != null) {

                TextView name = (TextView) v.findViewById(R.id.frag_location_auto_name);
                TextView town = (TextView) v.findViewById(R.id.frag_location_auto_town);

                if (name != null){
                    name.setText(l.getName());
                }
                if (town != null){
                    town.setText(l.getTown());
                }
            }

            return v;
        }

        @Override
        public Filter getFilter() {
            if (filter == null) {
                filter = new LocationFilter();
            }
            return filter;
        }

        private class LocationFilter extends Filter{

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                if (charSequence == null ||charSequence.length()==0) {
                    locations = allLocations;
                    results.values = locations;
                    results.count = locations.size();
                    notifyDataSetChanged();
                }
                else {
                    ArrayList<Location> nLocations = new ArrayList<Location>();
                    for(Location l : locations){
                        if(l.getName().toUpperCase().startsWith(charSequence.toString().toUpperCase())){
                            nLocations.add(l);
                        }
                    }
                    results.values = nLocations;
                    results.count = nLocations.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults.count==0) {
                    notifyDataSetInvalidated();
                }
                else{
                    locations = (ArrayList<Location>) filterResults.values;
                    notifyDataSetChanged();
                }
            }
        }
    }

    class VoteListAdapter extends ArrayAdapter<Vote>{
        ArrayList<Vote> votes;

        public VoteListAdapter(Context context, int layout, ArrayList<Vote> votes) {
            super(context, layout, votes);
            this.votes = votes;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.fragment_locations_row, null);
            }

            Vote vote = votes.get(position);

            if (vote != null) {

                TextView name = (TextView) v.findViewById(R.id.frag_location_name);
                TextView town = (TextView) v.findViewById(R.id.frag_location_town);
                ImageView thumbUp = (ImageView)v.findViewById(R.id.frag_location_thumbUp);

                if (name != null){
                    name.setText(vote.getLocation().getName());
                }
                if (town != null){
                    town.setText(vote.getLocation().getTown());
                }
                if(thumbUp != null){
                    thumbUp.setImageResource(R.drawable.ic_thumbupon);
                }
            }

            return v;
        }
    }
}
