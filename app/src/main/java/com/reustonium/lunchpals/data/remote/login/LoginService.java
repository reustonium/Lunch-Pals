package com.reustonium.lunchpals.data.remote.login;

import com.reustonium.lunchpals.data.model.LoginResult;

public interface LoginService {
    LoginResult signinWithEmail(String email, String password);
}
