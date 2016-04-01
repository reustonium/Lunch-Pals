package com.reustonium.lunchpals.data.remote.login;

import com.firebase.client.AuthData;

import rx.Observable;

public interface LoginService {
    Observable<AuthData> authWithPassword(String email, String password);

    Observable<AuthData> checkAuthState();
}
