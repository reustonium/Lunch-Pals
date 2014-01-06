package com.reustonium.lunchpals;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

        boolean hazPangs;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);

            // Set Greeting
            ParseUser user = ParseUser.getCurrentUser();

            TextView textView = (TextView) rootView.findViewById(R.id.textView_greeting);
            String greeting = String.format("Hi Pal! Do you haz pangs today %s?", user.getUsername());
            textView.setText(greeting);

            // Setup Switch - Pull current status if already set
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Status");
            query.whereEqualTo("createdBy", user);
            query.orderByAscending("createdAt");

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> parseObjects, ParseException e) {
                    if(e==null){
                        for(ParseObject obj : parseObjects){
                            Log.v("!!!", String.format("Craeted at: %d with status: %b", obj.getDate("createdAt"), obj.getBoolean("hazPangs")));

                        }

                        Log.v("!!!", String.format("there are: %d objects", parseObjects.size()));
                    } else {
                        Log.v("!!!", e.getMessage());
                    }
                }
            });

            // Setup Switch to default to false

            // Handle Switch OnClick
            Switch mSwitch = (Switch) rootView.findViewById(R.id.switch_hazPangs);
            mSwitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    ParseObject status = new ParseObject("Status");
                    status.put("hazPangs", b);
                    status.put("createdBy", ParseUser.getCurrentUser());
                    status.saveInBackground();
                }
            });

            // Setup Listview

            return rootView;
        }
    }

}
