package com.reustonium.lunchpals.ui.profile;

import android.os.Bundle;

import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.ui.base.BaseActivity;

import javax.inject.Inject;

public class ProfileActivity extends BaseActivity implements ProfileMvpView {

    @Inject ProfilePresenter mProfilePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_profile);
    }

    @Override
    public void showProfile(String pal) {
    }

    @Override
    public void showError() {

    }
}
