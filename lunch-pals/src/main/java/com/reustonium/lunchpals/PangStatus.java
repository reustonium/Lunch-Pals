package com.reustonium.lunchpals;

import com.parse.ParseObject;

import java.sql.Date;

/**
 * Created by Andrew on 1/18/14.
 */
public class PangStatus extends ParseObject {
    private boolean hazPangs;
    private Date date;

    public PangStatus(boolean _hazPangs, Date _date){
        hazPangs = _hazPangs;
        date = _date;
    }
}
