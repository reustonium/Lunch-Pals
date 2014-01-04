package com.reustonium.lunchpals;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link LocationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class LocationsFragment extends Fragment {

    MyAdapter adapter;
    ListView listLocations;
    TextView addNewLocation;
    AddLocationDialog addLocale;

    public static LocationsFragment newInstance() {
        LocationsFragment fragment = new LocationsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public LocationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addLocale = new AddLocationDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_locations, container, false);

        adapter = new MyAdapter(getActivity().getApplicationContext(), "Location");
        adapter.setTextKey("name");
        adapter.loadObjects();

        listLocations = (ListView) v.findViewById(R.id.list_locations);
        listLocations.setAdapter(adapter);

        addNewLocation = (TextView) v.findViewById(R.id.textView_AddLocation);
        addNewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addLocale.show(getActivity().getFragmentManager(), "dialog");

            }
        });

        return v;
    }

    public void doPositiveClick(){

    }


    private class MyAdapter extends ParseQueryAdapter<ParseObject> {

        private Context context;

        public MyAdapter(Context context, String pClass) {
            super(context, pClass);
            this.context = context;
        }

        @Override
        public View getItemView(ParseObject object, View v, ViewGroup parent) {
            if (v == null) {
                v = View.inflate(this.context, R.layout.location_listitem, null);
            }

            TextView textView = (TextView) v.findViewById(R.id.textView_LocationItem);
            textView.setText(object.get("name").toString());
            return v;
        }
    }
}


