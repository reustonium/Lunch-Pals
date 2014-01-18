package com.reustonium.lunchpals;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Parse.initialize(this, AppConsts.APPID, AppConsts.CLIENTKEY);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
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
                finish();
                return true;

            case R.id.home_ab_refresh:
                //TODO Update users
                PlaceholderFragment pFrag = (PlaceholderFragment) getFragmentManager().findFragmentByTag("mFragment");
                pFrag.UpdateStatus();
                return true;

            case R.id.home_ab_featReq:
                Intent github = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/reustonium/Lunch-Pals/issues/new"));
                startActivity(github);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class PlaceholderFragment extends Fragment {
        ArrayList<ParseUser> users;
        HazPangsAdapter mAdapter;
        View rootView;
        ListView listView;
        Switch mSwitch;
        TextView textView;
        TextView userText;

        public PlaceholderFragment() {

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

            // Handle Switch OnClick
            mSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.put("hazPangs", mSwitch.isChecked());
                    user.put("pangsUpdatedAt", new Date());
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e!=null){
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
}
