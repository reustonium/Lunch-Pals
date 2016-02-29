package com.reustonium.lunchpals.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.reustonium.lunchpals.LunchpalsApplication;
import com.reustonium.lunchpals.injection.component.ActivityComponent;
import com.reustonium.lunchpals.injection.component.DaggerActivityComponent;
import com.reustonium.lunchpals.injection.module.ActivityModule;

/**
 * Created by andrew on 2/25/16.
 */
public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(LunchpalsApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }
}
