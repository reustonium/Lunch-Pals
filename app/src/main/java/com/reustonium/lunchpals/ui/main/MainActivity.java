package com.reustonium.lunchpals.ui.main;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import com.reustonium.lunchpals.ui.base.BaseActivity;
import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.ui.profile.ProfileActivity;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements MainMvpView, PalsAdapter.Callback {

    @Inject MainPresenter mMainPresenter;
    @Inject PalsAdapter mPalsAdapter;

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.recycler_pal_list) RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);

        //Add Toolbar
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorText));
        setSupportActionBar(mToolbar);

        //Add RecyclerView
        mPalsAdapter = new PalsAdapter();
        mPalsAdapter.setCallback(this);
        mRecyclerView.setAdapter(mPalsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMainPresenter.attachView(this);
        mMainPresenter.loadPals();
    }

    @Override
    public void showPals(List<String> pals) {
        mPalsAdapter.setPals(pals);
        mPalsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPalsEmpty() {
        mPalsAdapter.setPals(Collections.<String>emptyList());
        mPalsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

    @Override
    public void onPalClicked(String palName) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.EXTRA_PALNAME, palName);
        startActivity(intent);
    }
}
