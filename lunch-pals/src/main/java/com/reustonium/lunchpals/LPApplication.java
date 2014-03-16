package com.reustonium.lunchpals;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.reustonium.lunchpals.models.Location;
import com.reustonium.lunchpals.models.LocationVote;
import com.reustonium.lunchpals.models.Nudge;

/**
 * Created by Andrew on 2/2/14.
 */
public class LPApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Nudge.class);
        ParseObject.registerSubclass(Location.class);
        ParseObject.registerSubclass(LocationVote.class);
        Parse.initialize(this, AppConsts.APPID, AppConsts.CLIENTKEY);
    }
}
