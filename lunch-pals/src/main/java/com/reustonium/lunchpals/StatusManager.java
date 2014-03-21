package com.reustonium.lunchpals;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.reustonium.lunchpals.models.Status;

import java.util.Date;


/**
 * Created by Andrew on 3/20/2014.
 */
public class StatusManager {
    ParseUser user;
    int haz;

    public StatusManager(){

    }

    //TODO Set Status
    public void SetStatus(ParseUser user, int haz) {
        this.user = user;
        this.haz = haz;
        ParseQuery<Status> query = ParseQuery.getQuery("Status");
        query.whereEqualTo("user", user);
        query.getFirstInBackground(new GetStatusCallback());
    }

    private class GetStatusCallback extends GetCallback<Status> {

        @Override
        public void done(Status status, ParseException e) {

                if (e == null && status != null) {
                    Date created = status.getCreatedAt();
                    Date today = new Date();
                    if (created.compareTo(today) == 0) {
                        //update existing status
                        status.setHaz(haz);
                        status.saveInBackground();
                    } else {
                        //create new status obj
                        Status mStatus = new Status();
                        mStatus.put("user", user);
                        mStatus.put("haz", haz);
                        mStatus.saveInBackground();
                    }
                }

                if (e != null && status == null) {
                    Status mStatus = new Status();
                    mStatus.put("user", user);
                    mStatus.put("haz", haz);
                    mStatus.saveInBackground();
                } else {
                    e.printStackTrace();
                }
            }

        }
    }


