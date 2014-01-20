package com.reustonium.lunchpals;

import com.parse.ParsePush;
import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by Andrew on 1/19/14.
 */
public class Nudge {
    private ParseUser fromUser;
    private ParseUser toUser;
    private String message;
    final int TIMEOUT = 1000 * 60* 60 * 12;

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


    public ParseUser getFromUser() {
        return fromUser;
    }

    public void setFromUser(ParseUser fromUser) {
        this.fromUser = fromUser;
    }

    public ParseUser getToUser() {
        return toUser;
    }

    public void setToUser(ParseUser toUser) {
        this.toUser = toUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean sendNudge() {
        boolean sent;
        boolean uniqueNudge = true;//!toUser.getUsername().equals(fromUser.getUsername());

        if(uniqueNudge && canNudge()){
            ParsePush push = new ParsePush();
            push.setChannel(String.format("user_%s", toUser.getObjectId()));
            push.setMessage(message);
            push.sendInBackground();

            sent = true;
        } else {
            sent = false;
        }
        return sent;
    }

    public boolean canNudge(){
        Date now = new Date();
        return (Math.round(now.getTime() - toUser.getDate("pangsUpdatedAt").getTime()) > TIMEOUT);
    }
}
