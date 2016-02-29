package com.reustonium.lunchpals.ui.main;

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


public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject MainPresenter mMainPresenter;
    @Inject PalsAdapter mPalsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);

        //Add Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorText));
        setSupportActionBar(toolbar);

        //Add RecyclerView
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_pal_list);
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
}
