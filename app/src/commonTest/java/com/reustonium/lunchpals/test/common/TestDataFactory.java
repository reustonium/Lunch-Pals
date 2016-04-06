package com.reustonium.lunchpals.test.common;

import com.reustonium.lunchpals.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestDataFactory {

    public static List<User> makePalsList(int numPals) {
        List<User> pals = new ArrayList<>();
        for (int i = 0; i < numPals; i++) {
            pals.add(makePal(String.valueOf(i)));
        }
        return pals;
    }

    private static User makePal(String palName) {
        HashMap<String, Object> map = new HashMap<>();
        return new User(palName, "a@b.com", map);
    }
}