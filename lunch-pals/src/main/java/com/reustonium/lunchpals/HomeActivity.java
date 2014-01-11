package com.reustonium.lunchpals;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
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
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

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

            ParseQuery<ParseUser> query = ParseUser.getQuery();
            
            //Setup View
            final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            final ListView listView = (ListView) rootView.findViewById(R.id.listView_palsList);
            final Switch mSwitch = (Switch) rootView.findViewById(R.id.switch_hazPangs);
            TextView textView = (TextView) rootView.findViewById(R.id.textView_greeting);

            // Get User Stuffz
            final ParseUser user = ParseUser.getCurrentUser();
            user.fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    mSwitch.setChecked(user.getBoolean("hazPangs"));
                }
            });
            
            // Set Greeting
            textView.setText(String.format("Hi Pal! Do you haz pangs today %s?", user.getUsername()));

            // Handle Switch OnClick
            mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    user.put("hazPangs", b);
                    user.saveInBackground();
                }
            });

            // Setup Listview

            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> parseUsers, ParseException e) {
                    List<ParseUser> users = new ArrayList<ParseUser>();
                    for(ParseUser user : parseUsers){
                        users.add(user);
                    }
                    ParseUser[] usersArray = users.toArray(new ParseUser[users.size()]);
                    listView.setAdapter(new HazPangsAdapter(getActivity(), usersArray));
                }
            });

            return rootView;
        }

        class HazPangsAdapter extends ArrayAdapter<ParseUser>{
            private ParseUser[] users;

            public HazPangsAdapter(Context context, ParseUser[] users){
                super(context, R.layout.fragment_home_row,R.id.frag_home_username ,users);
                this.users = users;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View row = super.getView(position, convertView, parent);

                TextView username = (TextView)row.findViewById(R.id.frag_home_username);
                TextView hazPangs = (TextView)row.findViewById(R.id.frag_home_hazPang);

                username.setText(users[position].getUsername());
                if(users[position].getBoolean("hazPangs")){
                    hazPangs.setText("HAZ!");
                } else {
                    hazPangs.setText("No Haz");
                }
                return row;
            }
        }
    }

}
