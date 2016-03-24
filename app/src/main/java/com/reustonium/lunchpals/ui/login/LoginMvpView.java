package com.reustonium.lunchpals.ui.login;

import android.view.View;

import com.reustonium.lunchpals.data.model.LoginResult;
import com.reustonium.lunchpals.ui.base.MvpView;

/**
 * Created by Andrew on 3/21/2016.
 */
public interface LoginMvpView extends MvpView {
    void onSignInPressed(View view);
    void onSignUpPressed(View view);
    void showPasswordError();
    void showEmailError();
    void showGeneralError(String error);
    void onLoginSuccess(LoginResult result);
}
