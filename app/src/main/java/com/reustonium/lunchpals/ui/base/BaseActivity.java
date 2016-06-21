package com.reustonium.lunchpals.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.reustonium.lunchpals.LunchPalsApplication;
import com.reustonium.lunchpals.injection.component.ActivityComponent;
import com.reustonium.lunchpals.injection.component.DaggerActivityComponent;
import com.reustonium.lunchpals.injection.module.ActivityModule;

/**
 * Created by Andrew on 6/21/2016.
 */

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(LunchPalsApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }
}
