package com.reustonium.lunchpals.test.common.injection.module;

import android.app.Application;
import android.content.Context;

import com.reustonium.lunchpals.data.DataManager;
import com.reustonium.lunchpals.data.remote.login.LoginService;
import com.reustonium.lunchpals.data.remote.main.PalsService;
import com.reustonium.lunchpals.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module
public class ApplicationTestModule {

    private final Application mApplication;

    public ApplicationTestModule(Application application) {
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

    /************* MOCKS *************/
    @Provides
    @Singleton
    DataManager provideDataManager() {
        return mock(DataManager.class);
    }

    @Provides
    @Singleton
    PalsService providePalsService() {
        return mock(PalsService.class);
    }

    @Provides
    @Singleton
    LoginService provideLoginService() {
        return mock(LoginService.class);
    }
}
