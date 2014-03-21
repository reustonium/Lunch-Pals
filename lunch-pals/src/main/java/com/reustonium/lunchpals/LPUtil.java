package com.reustonium.lunchpals;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.reustonium.lunchpals.models.Status;

import java.util.Date;

/**
 * Created by Andrew on 1/16/14.
 */
public class LPUtil {

    public static String calcTimeBetween(Date start, Date end){
        int SECOND = 1000;
        int MINUTE = SECOND * 60;
        int HOUR = MINUTE * 60;
        int DAY = HOUR * 24;

        int milliSeconds = Math.round(end.getTime() - start.getTime());

        if(milliSeconds > DAY){
            return String.format("%d days", Math.round(milliSeconds / DAY));
        }

        if(milliSeconds < DAY && milliSeconds > HOUR){
            return String.format("%d hours", Math.round(milliSeconds / HOUR));
        }

        if(milliSeconds < HOUR){
            return String.format("%d minutes", Math.round(milliSeconds / MINUTE));
        }

        //Throw an exception instead?
        return null;
    }

    public static Status getStatus(ParseUser user, Date today) throws ParseException {
        ParseQuery<Status> query = ParseQuery.getQuery("Status");
        query.whereEqualTo("user", user);
        query.whereGreaterThan("createdAt", today);
        if (query.getFirst() != null) {
            return query.getFirst();
        } else{
            return new Status();
        }
    }

}
