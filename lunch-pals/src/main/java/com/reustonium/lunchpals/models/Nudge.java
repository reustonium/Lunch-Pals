package com.reustonium.lunchpals.models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by Andrew on 2/10/14.
 */
@ParseClassName("Nudge")
public class Nudge extends ParseObject{

    final int TIMEOUT = 1000 * 60* 30;
    final int SLEEPTIMER = 1000 * 60* 60 * 12;

    public Nudge(){

    }

    public void setMessage(String msg){
        put("message", msg);
    }

    public String getMessage(){
        return getString("message");
    }

    public void setToUser(ParseUser toUser){
        put("toUser", toUser);
        put("fromUser", ParseUser.getCurrentUser());
    }

    public ParseUser getToUser(){
        return (ParseUser)getParseObject("toUser");
    }

    public ParseUser getFromUser(){
        return (ParseUser)getParseObject("fromUser");
    }

    public void send(){
        if(canNudge()){
            //TODO update push to send JSON payload including "from" and the proper "action" i.e. "com.reustonium.lunchpals.NUDGE"
            ParsePush push = new ParsePush();
            push.setChannel(String.format("user_%s", getToUser().getObjectId()));
            push.setMessage(getMessage());
            push.sendInBackground();
            this.saveInBackground();
        }
    }

    public boolean canNudge(){
        final Date now = new Date();
        boolean isAway = Math.round(now.getTime() - getToUser().getDate("pangsUpdatedAt").getTime()) > SLEEPTIMER;
        boolean alreadyNudged = false;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Nudge");
        query.whereEqualTo("toUser", getToUser());
        query.addDescendingOrder("createdAt");
        try {
            ParseObject firstNudge = query.getFirst();
            alreadyNudged = Math.round(now.getTime() - firstNudge.getCreatedAt().getTime()) < TIMEOUT;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (isAway && !alreadyNudged);
    }

    public boolean isUnique(){
        return !getToUser().getUsername().equals(getFromUser().getUsername());
    }

}
