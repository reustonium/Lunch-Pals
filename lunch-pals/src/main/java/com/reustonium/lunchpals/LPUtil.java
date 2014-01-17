package com.reustonium.lunchpals;

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
            return String.format("%d days", milliSeconds % DAY);
        }

        if(milliSeconds < DAY && milliSeconds > HOUR){
            return String.format("%d hours", milliSeconds % HOUR);
        }

        if(milliSeconds < HOUR){
            return String.format("%d minutes", milliSeconds % MINUTE);
        }

        //Throw an exception instead?
        return null;
    }

}
