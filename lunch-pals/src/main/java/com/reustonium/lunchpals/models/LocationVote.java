package com.reustonium.lunchpals.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Andrew on 3/15/14.
 */
@ParseClassName("LocationVote")
public class LocationVote extends ParseObject {
    Location location;
    ParseUser voter;
    int tally;

    public Location getLocation() {
        return location;
    }

    public ParseUser getVoter() {
        return voter;
    }

    public int getTally() {
        return tally;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setVoter(ParseUser voter) {
        this.voter = voter;
    }

    public void setTally(int tally) {
        this.tally = tally;
    }
}
