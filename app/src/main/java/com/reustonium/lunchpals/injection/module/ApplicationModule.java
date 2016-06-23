package com.reustonium.lunchpals.injection.module;

<<<<<<< HEAD

=======
>>>>>>> d5a53c60ab47c63e2116e2a684b08cfef4a1189f
import android.app.Application;
import android.content.Context;

import com.reustonium.lunchpals.injection.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
<<<<<<< HEAD
 * Created by Andrew on 6/20/2016.
=======
 * Provide application-level dependencies.
>>>>>>> d5a53c60ab47c63e2116e2a684b08cfef4a1189f
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
