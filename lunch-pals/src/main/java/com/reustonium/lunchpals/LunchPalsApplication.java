package com.reustonium.lunchpals;

import android.app.Application;
import android.content.Intent;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;

/**
 * Created by Andrew on 12/29/13.
 */
public class LunchPalsApplication extends Application{
    Intent intent;

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, AppConsts.APPID, AppConsts.CLIENTKEY);

        ParseUser user = ParseUser.getCurrentUser();
        user.logOut();
        user = null;

        if(user!=null){
            intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
