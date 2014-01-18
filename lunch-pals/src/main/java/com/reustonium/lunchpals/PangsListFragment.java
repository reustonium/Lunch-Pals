package com.reustonium.lunchpals;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Andrew on 1/16/14.
 */
public class PangsListFragment extends Fragment {
        ArrayList<ParseUser> users;
        HazPangsAdapter mAdapter;
        View rootView;
        ListView listView;
        Switch mSwitch;
        TextView textView;
        TextView userText;

        public PangsListFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            users = new ArrayList<ParseUser>();
            mAdapter = new HazPangsAdapter(getActivity(), users);

            //Setup View
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            listView = (ListView) rootView.findViewById(R.id.listView_palsList);
            mSwitch = (Switch) rootView.findViewById(R.id.switch_hazPangs);
            textView = (TextView) rootView.findViewById(R.id.textView_greeting);
            userText = (TextView) rootView.findViewById(R.id.home_username);

            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();

            UpdateStatus();

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
            listView.setAdapter(this.mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    ParsePush push = new ParsePush();
                    push.setChannel(String.format("user_%s",users.get(position).getObjectId()));
                    push.setMessage(String.format("hey %s, you got nudged by %s",users.get(position).getUsername(),user.getUsername()));
                    push.sendInBackground();
                }
            });

            // Handle Switch OnClick
            mSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.put("hazPangs", mSwitch.isChecked());
                    user.put("pangsUpdatedAt", new Date());
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                e.printStackTrace();
                            }
                            UpdateStatus();
                        }
                    });
                    Toast mToast = Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT);
                    mToast.show();
                }
            });

        }

        public void UpdateStatus(){
            final ParseQuery<ParseUser> query = ParseUser.getQuery();

            try{
                mAdapter.clear();
                List<ParseUser> usersList = query.find();
                mAdapter.addAll(usersList);
                mAdapter.notifyDataSetChanged();

            }catch(Exception ex){
                ex.printStackTrace();
            }

        }

        class HazPangsAdapter extends ArrayAdapter<ParseUser> {
            private ArrayList<ParseUser> users;
            final int TIMEOUT = 1000 * 60* 60 * 12;

            public HazPangsAdapter(Context context, ArrayList<ParseUser> users){
                super(context, R.layout.fragment_home_row,R.id.frag_home_username ,users);
                this.users = users;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View row = super.getView(position, convertView, parent);

                TextView username = (TextView)row.findViewById(R.id.frag_home_username);
                TextView updatedAt = (TextView)row.findViewById(R.id.frag_home_updatedAt);
                ImageView indicator = (ImageView)row.findViewById(R.id.frag_home_indicator);

                username.setText(users.get(position).getUsername());

                String timeString = LPUtil.calcTimeBetween(users.get(position).getDate("pangsUpdatedAt"), new Date());

                updatedAt.setText(String.format("%s since last updated", timeString));

                if(users.get(position).getBoolean("hazPangs")){
                    indicator.setImageResource(android.R.drawable.presence_online);
                } else {
                    indicator.setImageResource(android.R.drawable.presence_busy);
                }

                Date now = new Date();
                if(Math.round(now.getTime() - users.get(position).getDate("pangsUpdatedAt").getTime()) > TIMEOUT){
                    indicator.setImageResource(android.R.drawable.presence_offline);
                }
                return row;
            }
        }
    }

