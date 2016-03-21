package com.reustonium.lunchpals.ui.login;

import android.os.Bundle;
import android.view.View;

import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onSignInPressed(View view) {
    }

    public void onSignUpPressed(View view) {
    }
}
