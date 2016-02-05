package com.reustonium.lunchpals.data;


import android.support.annotation.VisibleForTesting;
import android.support.v4.util.ArrayMap;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by andrew on 2/4/16.
 */
public class FakePalsServiceApiImpl implements PalsServiceApi {

    // TODO replace this with a new test specific data set.
    private static final ArrayMap<String, Pal> PALS_SERVICE_DATA = new ArrayMap();

    @Override
    public void getAllPals(PalsServiceCallback<List<Pal>> callback) {
        callback.onLoaded(Lists.newArrayList(PALS_SERVICE_DATA.values()));
    }

    @Override
    public void getPal(String palId, PalsServiceCallback<Pal> callback) {
        Pal pal = PALS_SERVICE_DATA.get(palId);
        callback.onLoaded(pal);
    }

    @Override
    public void savePal(Pal pal) {
        PALS_SERVICE_DATA.put(pal.getId(), pal);
    }

    @VisibleForTesting
    public static void addPals(Pal... pals) {
        for (Pal pal : pals) {
            PALS_SERVICE_DATA.put(pal.getId(), pal);
        }
    }
}
