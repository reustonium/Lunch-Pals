package com.reustonium.lunchpals.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Andrew on 2/20/14.
 */
@ParseClassName("Location")
public class Location extends ParseObject{

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    int score;

    public String getName(){
        return(getString("name"));
    }

    public void setName(String name){
        put("name", name);
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getTown(){
        return (getString("town"));
    }
}
