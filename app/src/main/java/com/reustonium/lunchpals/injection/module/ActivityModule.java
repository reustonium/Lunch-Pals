package com.reustonium.lunchpals.injection.module;

<<<<<<< HEAD

=======
>>>>>>> d5a53c60ab47c63e2116e2a684b08cfef4a1189f
import android.app.Activity;
import android.content.Context;

import com.reustonium.lunchpals.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

<<<<<<< HEAD
/**
 * Created by Andrew on 6/21/2016.
 */
=======
>>>>>>> d5a53c60ab47c63e2116e2a684b08cfef4a1189f
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }
}
