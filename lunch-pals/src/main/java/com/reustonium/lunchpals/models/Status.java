package com.reustonium.lunchpals.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by Andrew on 2/12/14.
 */
@ParseClassName("Status")
public class Status extends ParseObject {

    public Status(){

    }

    public void setOwner(ParseUser owner) {
        put("owner", owner);
    }

    public ParseUser getOwner(){
        return (ParseUser)getParseObject("owner");
    }

    public void setDate(Date date){
        put("date", date);
    }

}
