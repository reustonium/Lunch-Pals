package com.reustonium.lunchpals.injection.module;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

import com.reustonium.lunchpals.data.remote.PalsService;
import com.reustonium.lunchpals.data.remote.ParsePalsService;
import com.reustonium.lunchpals.injection.ApplicationContext;

import javax.inject.Singleton;

/**
 * Provide application-level dependencies.
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

    @Provides
    @Singleton
    PalsService providePalsService() {
        return new ParsePalsService();
    }
}
