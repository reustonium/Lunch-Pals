package com.reustonium.lunchpals.ui.login;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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

    @Inject LoginPresenter mLoginPresenter;
    @Inject PalsAdapter mPalsAdapter;

    @Bind(R.id.activity_login_toolbar) Toolbar mToolbar;
    @Bind(R.id.activity_login_recycler_pal_list) RecyclerView mRecyclerView;

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

        //Add RecyclerView
        mPalsAdapter = new PalsAdapter();
        mRecyclerView.setAdapter(mPalsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLoginPresenter.loadDemoPals();
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
