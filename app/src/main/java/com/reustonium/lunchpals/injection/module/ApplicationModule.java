package com.reustonium.lunchpals.injection.module;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

import com.firebase.client.Firebase;
import com.reustonium.lunchpals.data.remote.FirebasePalService;
import com.reustonium.lunchpals.data.remote.PalsService;
import com.reustonium.lunchpals.data.remote.Util;
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
        return new FirebasePalService();
    }

    @Provides
    @Singleton
    Firebase provideFirebase(@ApplicationContext Context context){
        Firebase.setAndroidContext(context);
        return new Firebase(Util.FIREBASE_URL);
    }
}
