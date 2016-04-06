package com.reustonium.lunchpals.data.remote.login;

import com.firebase.client.AuthData;

import rx.Observable;

/**
 * Created by Andrew on 4/6/2016.
 */
public class FirebaseLoginService implements LoginService {
    @Override
    public Observable<AuthData> checkAuthState() {
        return null;
    }
}
