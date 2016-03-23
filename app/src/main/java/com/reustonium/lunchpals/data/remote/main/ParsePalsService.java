package com.reustonium.lunchpals.data.remote.main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 3/2/2016.
 */
public class ParsePalsService implements PalsService {
    @Override
    public List<String> getPals() {
        List<String> pals = new ArrayList<>();
        pals.add("Andy");
        pals.add("Jimmy Jamm");
        pals.add("Frankzilla");
        pals.add("Mr. Ballooon Hands");
        return pals;
    }
}
