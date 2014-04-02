package com.reustonium.lunchpals.models;

import android.content.Context;

import java.util.ArrayList;

import com.parse.ParseUser;

/**
 * Created by Andrew on 4/1/2014.
 */
public class PalsListManager {
    private ArrayList<ParseUser> mUsers;
    private static PalsListManager sPalsListManager;
    private Context mContext;

    private PalsListManager(Context context){
        mContext = context;
        mUsers = new ArrayList<ParseUser>();
    }

    public static PalsListManager get(Context c){
        if (sPalsListManager == null) {
            sPalsListManager = new PalsListManager(c.getApplicationContext());
        }
        return sPalsListManager;
    }

    public ArrayList<ParseUser> getUsers(){
        return mUsers;
    }
}
