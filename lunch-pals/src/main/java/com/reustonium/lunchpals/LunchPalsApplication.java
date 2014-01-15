package com.reustonium.lunchpals;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Andrew on 12/29/13.
 */
public class LunchPalsApplication extends Application{
    Intent intent;

    @Override
    public void onCreate() {
        super.onCreate();


    }
}
