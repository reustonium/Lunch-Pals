package com.reustonium.lunchpals.ui.createAccount;

import android.view.View;

import com.reustonium.lunchpals.ui.base.MvpView;

public interface CreateAccountMvpView extends MvpView {
    void onCreateAccountPressed(View view);
    void onPasswordReset();
    void showError(String message);
}
