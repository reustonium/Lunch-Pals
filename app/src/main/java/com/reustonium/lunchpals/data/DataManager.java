package com.reustonium.lunchpals.data;

import com.firebase.client.AuthData;
import com.reustonium.lunchpals.data.local.PreferencesHelper;
import com.reustonium.lunchpals.data.model.User;
import com.reustonium.lunchpals.data.remote.main.PalsService;
import com.reustonium.lunchpals.data.remote.login.LoginService;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class DataManager {

    private final PalsService mPalService;
    private final LoginService mLoginService;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(PalsService palService, LoginService loginService,
                       PreferencesHelper preferencesHelper) {
        mPalService = palService;
        mLoginService = loginService;
        mPreferencesHelper = preferencesHelper;
    }

    public List<User> getPals() {
        return mPalService.getPals();
    }

    public Observable<AuthData> checkAuthState() {
        return mLoginService.checkAuthState();
    }

}
