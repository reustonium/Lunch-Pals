package com.reustonium.lunchpals.data;

import com.firebase.client.AuthData;
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

    @Inject
    public DataManager(PalsService palService, LoginService loginService) {
        mPalService = palService;
        mLoginService = loginService;
    }

    public List<String> getPals() {
        return mPalService.getPals();
    }

    public Observable<AuthData> authWithPassword(String email, String password) {
        return mLoginService.authWithPassword(email, password);
    }

    public Observable<AuthData> checkAuthState() {
        return mLoginService.checkAuthState();
    }

    public Observable<Map<String, Object>> createUser(String email, String password) {
        return mLoginService.createUser(email, password);
    }
}
