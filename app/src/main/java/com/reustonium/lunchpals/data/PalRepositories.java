package com.reustonium.lunchpals.data;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;
/**
 * Created by andrew on 2/4/16.
 */
public class PalRepositories {

    private PalRepositories() {
        // no instance
    }

    private static PalsRepository repository = null;

    public synchronized static PalsRepository getInMemoryRepoInstance(@NonNull PalsServiceApi palsServiceApi) {
        checkNotNull(palsServiceApi);
        if (null == repository) {
            repository = new InMemoryPalsRepository(palsServiceApi);
        }
        return repository;
    }


}
