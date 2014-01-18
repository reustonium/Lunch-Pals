package com.reustonium.lunchpals;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseUser;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Parse.initialize(this, AppConsts.APPID, AppConsts.CLIENTKEY);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PangsListFragment(), "mFragment")
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
                PangsListFragment pFrag = (PangsListFragment) getFragmentManager().findFragmentByTag("mFragment");
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


}
