package com.reustonium.lunchpals.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Andrew on 3/16/14.
 */
@ParseClassName("Vote")
public class Vote extends ParseObject {

    public ParseUser getUser() {
        return (ParseUser)getParseObject("user");
    }

    public void setUser(ParseUser user) {
        put("user",user);
    }

    public Location getLocation() {
        return (Location)getParseObject("location");
    }

    public void setLocation(Location location) {
        put("location", location);
    }

    public int getValue() {
        return getInt("value");
    }

    public void setValue(int value) {
        put("value", value);
    }

}
