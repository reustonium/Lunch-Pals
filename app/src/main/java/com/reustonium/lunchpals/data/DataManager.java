package com.reustonium.lunchpals.data;

import com.reustonium.lunchpals.data.remote.PalsService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    private final PalsService mPalService;

    @Inject
    public DataManager(PalsService palService) {
        mPalService = palService;
    }

    public List<String> getPals() {
        return mPalService.getPals();
    }
}
