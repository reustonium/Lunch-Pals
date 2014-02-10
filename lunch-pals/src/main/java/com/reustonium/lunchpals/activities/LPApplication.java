package com.reustonium.lunchpals.activities;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.reustonium.lunchpals.AppConsts;
import com.reustonium.lunchpals.models.Nudge;

/**
 * Created by Andrew on 2/2/14.
 */
public class LPApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Nudge.class);
        Parse.initialize(this, AppConsts.APPID, AppConsts.CLIENTKEY);
    }
}
