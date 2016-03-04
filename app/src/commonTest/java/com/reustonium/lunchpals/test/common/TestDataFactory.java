package com.reustonium.lunchpals.test.common;

import java.util.ArrayList;
import java.util.List;

public class TestDataFactory {

    public static List<String> makePalsList(int numPals) {
        List<String> pals = new ArrayList<>();
        for (int i=0; i< numPals; i++) {
            pals.add(makePal(String.valueOf(i)));
        }
        return pals;
    }

    private static String makePal(String palName) {
        return "Generic Pal # " + palName;
    }
}