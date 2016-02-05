package com.reustonium.lunchpals.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by andrew on 2/4/16.
 */
public interface PalsRepository {

    interface LoadPalsCallback {

        void onPalsLoaded(List<Pal> pals);
    }

    interface GetPalCallback {

        void onPalLoaded(Pal pal);
    }

    void getPals(@NonNull LoadPalsCallback callback);

    void getPal(@NonNull String palId, @NonNull GetPalCallback callback);

    void savePal(@NonNull Pal pal);

    void refreshData();
}
