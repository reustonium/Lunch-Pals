package com.reustonium.lunchpals;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.parse.Parse;
import com.parse.ParseUser;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Parse.initialize(this, AppConsts.APPID, AppConsts.CLIENTKEY);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ParseUser user = ParseUser.getCurrentUser();
                Intent intent;

                if(user!=null){
                    intent = new Intent(getApplicationContext(), HomeActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}
