package com.reustonium.lunchpals.ui.profile;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity implements ProfileMvpView {

    public static final String EXTRA_PALNAME =
            "com.reustonium.lunchpals.ui.profile.ProfileActivity.EXTRA_PALNAME";
    @Inject ProfilePresenter mProfilePresenter;

    @Bind(R.id.profile_pal_name_textview) TextView mPalNameTextView;
    @Bind(R.id.profile_toolbar) Toolbar mToolbar;

    private String mPalName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        mPalName = getIntent().getStringExtra(EXTRA_PALNAME);
        if (mPalName == null) {
            throw new IllegalArgumentException("ProfileActivity requires a PalName!");
        }
        showProfile(mPalName);

        mProfilePresenter.attachView(this);

        //Add Toolbar
        setSupportActionBar(mToolbar);
    }

    @Override
    public void showProfile(String pal) {
        mPalNameTextView.setText(pal);
    }

    @Override
    public void showError() {

    }
}
