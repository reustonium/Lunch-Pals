package com.reustonium.lunchpals.ui.login;

import android.view.View;

import com.reustonium.lunchpals.ui.base.MvpView;

/**
 * Created by andrew on 3/19/16.
 */
public interface LoginMvpView extends MvpView {

    public void onSignUpPressed(View view);

    public void onSignInPressed(View view);
}
