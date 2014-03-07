package com.reustonium.lunchpals.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.reustonium.lunchpals.LPUtil;
import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.models.Nudge;

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
        TextView userText;
        ProgressBar progress;
        LinearLayout switchLayout;

        public PangsListFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            users = new ArrayList<ParseUser>();
            mAdapter = new HazPangsAdapter(getActivity(), users);
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            listView = (ListView) rootView.findViewById(R.id.listView_palsList);
            userText = (TextView) rootView.findViewById(R.id.home_username);
            progress = (ProgressBar) rootView.findViewById(R.id.home_progressbar);
            switchLayout = (LinearLayout) rootView.findViewById(R.id.home_switch);

            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();

            showLoadingUI(true);

            final ParseUser user = ParseUser.getCurrentUser();
            user.put("pangsUpdatedAt", new Date());
            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    UpdateStatus();
                }
            });

            user.fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    mSwitch.setChecked(user.getBoolean("hazPangs"));
                }
            });

            userText.setText(user.getUsername());
            listView.setAdapter(this.mAdapter);
            listView.setOnItemClickListener(new PalsListOnClickListener());
            mSwitch.setOnClickListener(new StatusSwitchOnClickListener());
        }

        public void UpdateStatus(){
            final ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.orderByDescending("pangsUpdatedAt");

            mAdapter.clear();
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> parseUsers, ParseException e) {
                    mAdapter.addAll(parseUsers);
                    mAdapter.notifyDataSetChanged();
                    showLoadingUI(false);
                }
            });
        }

        private void showLoadingUI(boolean on){
            if(on){
                switchLayout.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
            }
            else{
                switchLayout.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
            }
        }

        public void onStatusClicked(View view){

        }

        class StatusSwitchOnClickListener implements View.OnClickListener{

            ParseUser user = ParseUser.getCurrentUser();

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
        }

        class PalsListOnClickListener implements AdapterView.OnItemClickListener{

            ParseUser user = ParseUser.getCurrentUser();

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String msg = String.format("You've been nudged by %s", user.getUsername());
                final Nudge nudge = new Nudge();
                nudge.setMessage(msg);
                nudge.setToUser(users.get(position));

                if(!nudge.canNudge() || !nudge.isUnique()){
                    String message;
                    if(nudge.isUnique()){
                        message = String.format("%s doesn't need a nudge", users.get(position).getUsername());
                    } else {
                        message = "You can't nudge yourself, pervert!";
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(message)
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.show();
                } else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(String.format("Nudge %s?", users.get(position).getUsername()))
                            .setPositiveButton("Yep!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    nudge.send();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    builder.show();
                }
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

