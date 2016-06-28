package com.reustonium.lunchpals.ui.signup;

import com.reustonium.lunchpals.ui.base.MvpView;

public interface SignUpView extends MvpView {
    void showProgress();
    void hideProgress();
    void showError(String message);
}
