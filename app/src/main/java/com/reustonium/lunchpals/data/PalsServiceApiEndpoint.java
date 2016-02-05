package com.reustonium.lunchpals.data;

import android.support.v4.util.ArrayMap;

/**
 * Created by andrew on 2/4/16.
 */
public final class PalsServiceApiEndpoint {
    static {
        DATA = new ArrayMap(2);
        addPal("Oh yes!", "I demand trial by Unit testing", null);
        addPal("Espresso", "UI Testing for Android", null);
    }

    private final static ArrayMap<String, Pal> DATA;

    private static void addPal(String title, String description, String imageUrl) {
        Pal newPal = new Pal(title, description, imageUrl);
        DATA.put(newPal.getId(), newPal);
    }

    /**
     * @return the Pals to show when starting the app.
     */
    public static ArrayMap<String, Pal> loadPersistedPals() {
        return DATA;
    }
}
