package com.reustonium.lunchpals;



import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView listLocations;
    TextView addNewLocation;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationsFragment newInstance(String param1, String param2) {
        LocationsFragment fragment = new LocationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public LocationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_locations, container, false);

        final MyAdapter adapter = new MyAdapter(getActivity().getApplicationContext(), "Location");
        adapter.setTextKey("name");

        listLocations = (ListView) v.findViewById(R.id.list_locations);
        listLocations.setAdapter(adapter);

        addNewLocation = (TextView) v.findViewById(R.id.textView_AddLocation);
        addNewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddLocationDialog addLocale = new AddLocationDialog();
                addLocale.show(getActivity().getFragmentManager(), "dialog");
                adapter.notifyDataSetChanged();
            }
        });
        return v;
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


