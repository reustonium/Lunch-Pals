package com.reustonium.lunchpals.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.parse.ParseUser;
import com.reustonium.lunchpals.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();

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
