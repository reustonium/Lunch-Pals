package com.reustonium.lunchpals.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.SignInButton;
import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.data.model.User;
import com.reustonium.lunchpals.ui.base.BaseActivity;
import com.reustonium.lunchpals.ui.main.PalsAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private ProgressDialog mAuthProgressDialog;

    @Inject LoginPresenter mLoginPresenter;
    @Inject PalsAdapter mPalsAdapter;

    @Bind(R.id.activity_login_toolbar) Toolbar mToolbar;
    @Bind(R.id.activity_login_recycler_pal_list) RecyclerView mRecyclerView;
    @Bind(R.id.login_with_google) SignInButton mSignInButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_login);
        mLoginPresenter.attachView(this);
        ButterKnife.bind(this);

        //Add Toolbar
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorGreyLight));
        setSupportActionBar(mToolbar);

        //Progress Dialog
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle(getString(R.string.progress_dialog_loading));
        mAuthProgressDialog.setMessage(getString(R.string.progress_dialog_authenticating_with_firebase));
        mAuthProgressDialog.setCancelable(false);

        //Sign In Button
        mSignInButton.setSize(SignInButton.SIZE_WIDE);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignInGooglePressed(v);
            }
        });

        //Add RecyclerView
        mPalsAdapter = new PalsAdapter();
        mRecyclerView.setAdapter(mPalsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLoginPresenter.loadDemoPals();
    }

    private void onSignInGooglePressed(View view) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent);
        mAuthProgressDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginPresenter.checkAuthState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }

    @Override
    public void showPals(List<User> pals) {
        mPalsAdapter.setPals(pals);
        mPalsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showGeneralError(String error) {

    }

    @Override
    public void onLoginSuccess(String result) {

    }
}
