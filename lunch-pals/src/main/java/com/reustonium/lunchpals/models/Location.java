package com.reustonium.lunchpals.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Andrew on 2/20/14.
 */
@ParseClassName("Location")
public class Location extends ParseObject{

    public String getName(){
        return(getString("name"));
    }

    public void setName(String name){
        put("name", name);
    }
}
