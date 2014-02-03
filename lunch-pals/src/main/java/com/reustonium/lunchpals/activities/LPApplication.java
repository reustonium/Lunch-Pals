package com.reustonium.lunchpals.activities;

import android.app.Application;

import com.parse.Parse;
import com.reustonium.lunchpals.AppConsts;

/**
 * Created by Andrew on 2/2/14.
 */
public class LPApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, AppConsts.APPID, AppConsts.CLIENTKEY);
    }
}
