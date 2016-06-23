package com.reustonium.lunchpals;

import android.app.Application;
import android.content.Context;

import com.reustonium.lunchpals.injection.component.ApplicationComponent;
import com.reustonium.lunchpals.injection.component.DaggerApplicationComponent;
import com.reustonium.lunchpals.injection.module.ApplicationModule;

import timber.log.Timber;

<<<<<<< HEAD
/**
 * Created by Andrew on 6/20/2016.
 */
public class LunchPalsApplication extends Application {

=======
public class LunchPalsApplication extends Application {
>>>>>>> d5a53c60ab47c63e2116e2a684b08cfef4a1189f
    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
<<<<<<< HEAD
            //Timber.plant(new Timber.DebugTree());
        }
=======
            Timber.plant(new Timber.DebugTree());
        }

>>>>>>> d5a53c60ab47c63e2116e2a684b08cfef4a1189f
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
