package com.reustonium.lunchpals.ui.login;

import android.view.View;

import com.firebase.client.AuthData;
import com.reustonium.lunchpals.ui.base.MvpView;

/**
 * Created by Andrew on 3/21/2016.
 */
public interface LoginMvpView extends MvpView {
    void onSignInPressed(View view);
    void onSignUpPressed(View view);
    void showLoginError(String error);
    void onLoginSuccess(AuthData authData, String mEncodedEmail);
}
