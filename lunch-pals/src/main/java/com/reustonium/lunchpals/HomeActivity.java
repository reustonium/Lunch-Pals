package com.reustonium.lunchpals;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

            // Set Greeting
            final ParseUser user = ParseUser.getCurrentUser();

            TextView textView = (TextView) rootView.findViewById(R.id.textView_greeting);
            String greeting = String.format("Hi Pal! Do you haz pangs today %s?", user.getUsername());
            textView.setText(greeting);

            // Setup Switch - Pull current status if already set
            final Switch mSwitch = (Switch) rootView.findViewById(R.id.switch_hazPangs);
            user.fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    mSwitch.setChecked(user.getBoolean("hazPangs"));
                }
            });

            // Setup Switch to default to false

            // Handle Switch OnClick
            mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    user.put("hazPangs", b);
                    user.saveInBackground();
                }
            });

            // Setup Listview
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> parseUsers, ParseException e) {
                    List<String> usernames = new ArrayList<String>();
                    for(ParseUser user : parseUsers){
                        usernames.add(user.getUsername());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, usernames);
                    ListView listView = (ListView) rootView.findViewById(R.id.listView_palsList);
                    listView.setAdapter(adapter);
                }
            });

            return rootView;
        }
    }

}
