package com.reustonium.lunchpals;

import android.app.Application;
import android.content.Context;

import com.reustonium.lunchpals.injection.component.ApplicationComponent;
import com.reustonium.lunchpals.injection.component.DaggerApplicationComponent;
import com.reustonium.lunchpals.injection.module.ApplicationModule;

import timber.log.Timber;

/**
 * Created by Andrew on 6/20/2016.
 */
public class LunchPalsApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            //Timber.plant(new Timber.DebugTree());
        }
    }

    public static LunchPalsApplication get(Context context) {
        return (LunchPalsApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
