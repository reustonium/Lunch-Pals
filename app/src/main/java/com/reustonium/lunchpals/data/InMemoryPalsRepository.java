package com.reustonium.lunchpals.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load pals from the a data source.
 */
public class InMemoryPalsRepository implements PalsRepository {

    private final PalsServiceApi mPalsServiceApi;

    /**
     * This method has reduced visibility for testing and is only visible to tests in the same
     * package.
     */
    @VisibleForTesting
    List<Pal> mCachedPals;

    public InMemoryPalsRepository(@NonNull PalsServiceApi palsServiceApi) {
        mPalsServiceApi = checkNotNull(palsServiceApi);
    }

    @Override
    public void getPals(@NonNull final LoadPalsCallback callback) {
        checkNotNull(callback);
        // Load from API only if needed.
        if (mCachedPals == null) {
            mPalsServiceApi.getAllPals(new PalsServiceApi.PalsServiceCallback<List<Pal>>() {
                @Override
                public void onLoaded(List<Pal> pals) {
                    mCachedPals = ImmutableList.copyOf(pals);
                    callback.onPalsLoaded(mCachedPals);
                }
            });
        } else {
            callback.onPalsLoaded(mCachedPals);
        }
    }

    @Override
    public void savePal(@NonNull Pal pal) {
        checkNotNull(pal);
        mPalsServiceApi.savePal(pal);
        refreshData();
    }

    @Override
    public void getPal(@NonNull final String palId, @NonNull final GetPalCallback callback) {
        checkNotNull(palId);
        checkNotNull(callback);
        // Load pals matching the id always directly from the API.
        mPalsServiceApi.getPal(palId, new PalsServiceApi.PalsServiceCallback<Pal>() {
            @Override
            public void onLoaded(Pal pal) {
                callback.onPalLoaded(pal);
            }
        });
    }

    @Override
    public void refreshData() {
        mCachedPals = null;
    }

}