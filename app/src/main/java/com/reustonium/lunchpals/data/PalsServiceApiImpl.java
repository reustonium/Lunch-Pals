package com.reustonium.lunchpals.data;

import android.os.Handler;
import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 2/4/16.
 */
public class PalsServiceApiImpl implements PalsServiceApi {
    private static final int SERVICE_LATENCY_IN_MILLIS = 2000;
    private static final ArrayMap<String, Pal> NOTES_SERVICE_DATA =
            PalsServiceApiEndpoint.loadPersistedPals();

    @Override
    public void getAllPals(final PalsServiceCallback callback) {
        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Pal> pals = new ArrayList<>(NOTES_SERVICE_DATA.values());
                callback.onLoaded(pals);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void getPal(final String palId, final PalsServiceCallback callback) {
        //TODO: Add network latency here too.
        Pal pal = NOTES_SERVICE_DATA.get(palId);
        callback.onLoaded(pal);
    }

    @Override
    public void savePal(Pal pal) {
        NOTES_SERVICE_DATA.put(pal.getId(), pal);
    }
}
