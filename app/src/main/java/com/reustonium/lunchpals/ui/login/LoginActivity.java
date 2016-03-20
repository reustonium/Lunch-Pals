package com.reustonium.lunchpals.ui.login;

import android.os.Bundle;
import android.view.View;

import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by andrew on 3/19/16.
 */
public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject LoginPresenter mLoginPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActivityComponent().inject(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }

    //MVP View Methods Implemented Here
    @Override
    public void onSignUpPressed(View view) {

    }

    @Override
    public void onSignInPressed(View view) {

    }
}
