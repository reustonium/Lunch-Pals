package com.reustonium.lunchpals.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.reustonium.lunchpals.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Andrew on 4/5/2016.
 */
@Singleton
public class PreferencesHelper {
    public static final String PREF_FILE_NAME = "lunchpals_pref_file";

    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }
}
