package com.reustonium.lunchpals.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Andrew on 3/20/2014.
 */
@ParseClassName("Status")
public class Status extends ParseObject {

    public ParseUser getUser() {
        return (ParseUser)getParseObject("user");
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    public int getHaz() {
        return getInt("haz");
    }

    public void setHaz(int haz) {
        put("haz", haz);
    }
}
