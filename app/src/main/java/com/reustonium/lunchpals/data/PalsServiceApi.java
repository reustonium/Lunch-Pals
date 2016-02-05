package com.reustonium.lunchpals.data;

import java.util.List;

/**
 * Created by andrew on 2/4/16.
 */
public interface PalsServiceApi {
    interface PalsServiceCallback<T> {

        void onLoaded(T pals);
    }

    void getAllPals(PalsServiceCallback<List<Pal>> callback);

    void getPal(String palId, PalsServiceCallback<Pal> callback);

    void savePal(Pal pal);
}
