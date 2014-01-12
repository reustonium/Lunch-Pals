package com.reustonium.lunchpals;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import java.util.Date;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_ab_logout:
                ParseUser.logOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class PlaceholderFragment extends Fragment {
        ParseUser[] users;
        HazPangsAdapter mAdapter;

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            users = GetUsers();
            mAdapter = new HazPangsAdapter(getActivity(), users);

            //Setup View
            final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            final ListView listView = (ListView) rootView.findViewById(R.id.listView_palsList);
            final Switch mSwitch = (Switch) rootView.findViewById(R.id.switch_hazPangs);
            TextView textView = (TextView) rootView.findViewById(R.id.textView_greeting);
            TextView userText = (TextView) rootView.findViewById(R.id.home_username);

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
            userText.setText(user.getUsername());

            // Setup Listview
            listView.setAdapter(mAdapter);

            // Handle Switch OnClick
            mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    user.put("hazPangs", b);
                    user.put("pangsUpdatedAt", new Date());
                    user.saveInBackground();
                }
            });

            return rootView;
        }

        private ParseUser[] GetUsers(){
            final ParseQuery<ParseUser> query = ParseUser.getQuery();
            ParseUser[] usersArray;

            try {
                List<ParseUser> users = query.find();
                usersArray = users.toArray(new ParseUser[users.size()]);
            } catch (ParseException e) {
                usersArray = null;
                e.printStackTrace();
            }
            return usersArray;
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
                TextView updatedAt = (TextView)row.findViewById(R.id.frag_home_updatedAt);
                ImageView indicator = (ImageView)row.findViewById(R.id.frag_home_indicator);

                username.setText(users[position].getUsername());
                updatedAt.setText(users[position].getDate("pangsUpdatedAt").toString());

                if(users[position].getBoolean("hazPangs")){
                    indicator.setImageResource(android.R.drawable.presence_online);
                } else {
                    indicator.setImageResource(android.R.drawable.presence_busy);
                }
                return row;
            }
        }
    }
}
