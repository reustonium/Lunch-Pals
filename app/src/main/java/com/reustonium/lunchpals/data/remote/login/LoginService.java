package com.reustonium.lunchpals.data.remote.login;

import com.firebase.client.AuthData;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

public interface LoginService {
    Observable<AuthData> authWithPassword(String email, String password);

    Observable<AuthData> checkAuthState();

    Observable<Map<String, Object>> createUser(String email, String password);

    Observable saveUser(HashMap<String, Object> userAndUidMapping);
}
