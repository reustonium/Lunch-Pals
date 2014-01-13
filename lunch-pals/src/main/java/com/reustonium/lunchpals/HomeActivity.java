package com.reustonium.lunchpals;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.util.Date;
import java.util.ArrayList;

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
                    //.add(R.id.container, new PlaceholderFragment())
                    .add(R.id.container, new PlaceholderFragment(), "mFragment")
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
            case R.id.home_ab_about:
                new AlertDialog.Builder(this)
                        .setTitle("About LunchPals")
                        .setMessage("Alpha Release 0.2.2")
                        .setPositiveButton("Thanks Pal!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();
                return true;

            case R.id.home_ab_refresh:
                //TODO Update users
                PlaceholderFragment pFrag = (PlaceholderFragment) getFragmentManager().findFragmentByTag("mFragment");
                pFrag.UpdateStatus();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class PlaceholderFragment extends Fragment {
        ArrayList<ParseUser> users;
        HazPangsAdapter mAdapter;

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            this.users = new ArrayList<ParseUser>();
            this.mAdapter = new HazPangsAdapter(getActivity(), users);

            UpdateStatus();

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
            listView.setAdapter(this.mAdapter);

            // Handle Switch OnClick
            mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    user.put("hazPangs", b);
                    user.put("pangsUpdatedAt", new Date());
                    user.saveInBackground();
                    Toast mToast = Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT);
                    mToast.show();
                }
            });

            return rootView;
        }

        private void UpdateStatus(){
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

        class HazPangsAdapter extends ArrayAdapter<ParseUser>{
            private ArrayList<ParseUser> users;

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
                updatedAt.setText(users.get(position).getDate("pangsUpdatedAt").toString());

                if(users.get(position).getBoolean("hazPangs")){
                    indicator.setImageResource(android.R.drawable.presence_online);
                } else {
                    indicator.setImageResource(android.R.drawable.presence_busy);
                }
                return row;
            }
        }
    }
}
