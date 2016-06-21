package com.reustonium.lunchpals.injection.module;


import android.app.Application;
import android.content.Context;

import com.reustonium.lunchpals.injection.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Andrew on 6/20/2016.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

}
