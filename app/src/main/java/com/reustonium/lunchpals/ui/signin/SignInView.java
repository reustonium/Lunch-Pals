package com.reustonium.lunchpals.ui.signin;

import com.reustonium.lunchpals.ui.base.MvpView;

public interface SignInView extends MvpView{
    void showProgress();
    void hideProgress();
    void showError(String message);
}
