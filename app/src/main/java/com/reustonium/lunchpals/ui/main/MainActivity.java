package com.reustonium.lunchpals.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

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

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
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
    protected void onDestroy() {
        super.onDestroy();

        mMainPresenter.detachView();
    }

    /***** MVP View methods implementation *****/

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
    public void onPalClicked(String palName, View profileImage, View textPalName) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.EXTRA_PALNAME, palName);

        String transitionNameImage = getString(R.string.transition_image_pal_profile);
        String transitionNameText = getString(R.string.transition_text_pal_name);
        String transitionToolbar = getString(R.string.transition_toolbar);

        Pair<View, String> p1 = Pair.create(profileImage, transitionNameImage);
        Pair<View, String> p2 = Pair.create(textPalName, transitionNameText);
        Pair<View, String> p3 = Pair.create((View) mToolbar, transitionToolbar);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, p1, p2, p3);

        startActivity(intent, options.toBundle());
    }
}
