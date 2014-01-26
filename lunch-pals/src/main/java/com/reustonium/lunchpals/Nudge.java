package com.reustonium.lunchpals;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Andrew on 1/19/14.
 */
public class Nudge{
    private ParseUser fromUser;
    private ParseUser toUser;
    private String message;
    final int TIMEOUT = 1000 * 60* 30;
    final int SLEEPTIMER = 1000 * 60* 60 * 12;

    /**
     * @param _to The ParseUser who will receive the nudge
     * @param _from The ParseUser sending the nudge
     * @param _msg The message sent via nudge
    */
    public Nudge(ParseUser _from, ParseUser _to, String _msg) {
        fromUser = _from;
        toUser = _to;
        message = _msg;
    }

    public Nudge() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean sendNudge() {
        boolean sent;
        if(canNudge()){
            ParsePush push = new ParsePush();
            push.setChannel(String.format("user_%s", toUser.getObjectId()));
            push.setMessage(message);
            push.sendInBackground();
            postToDB();
            sent = true;
        } else {
            sent = false;
        }
        return sent;
    }

    private void postToDB() {
        ParseObject nudge = new ParseObject("Nudge");
        nudge.put("fromUser", fromUser);
        nudge.put("toUser", toUser);
        nudge.put("message", message);
        nudge.saveInBackground();
    }

    public boolean canNudge(){
        final Date now = new Date();
        boolean isAway = Math.round(now.getTime() - toUser.getDate("pangsUpdatedAt").getTime()) > SLEEPTIMER;
        boolean alreadyNudged = false;


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Nudge");
        query.whereEqualTo("toUser", toUser);
        query.addDescendingOrder("createdAt");
        try {
            ParseObject firstNudge = query.getFirst();
            alreadyNudged = Math.round(now.getTime() - firstNudge.getCreatedAt().getTime()) < TIMEOUT;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (isAway && !alreadyNudged);
    }

    public boolean isUniqueNudge(){
        return !toUser.getUsername().equals(fromUser.getUsername());
    }

    public List<ParseObject> getNudgesTo(ParseUser user){
        List<ParseObject> list = new ArrayList<ParseObject>();

        ParseQuery<ParseUser> innerQ = ParseUser.getQuery();
        innerQ.whereEqualTo("objectId", user.getObjectId());

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Nudge");
        query.whereMatchesQuery("toUser", innerQ);
        try {
            list = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ParseObject> getNudgesFrom(ParseUser user){
        List<ParseObject> list = new ArrayList<ParseObject>();

        ParseQuery<ParseUser> innerQ = ParseUser.getQuery();
        innerQ.whereEqualTo("objectId", user.getObjectId());

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Nudge");
        query.whereMatchesQuery("fromUser", innerQ);
        try {
            list = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }
}
