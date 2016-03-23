package com.reustonium.lunchpals.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

import com.reustonium.lunchpals.data.DataManager;
import com.reustonium.lunchpals.data.remote.login.LoginService;
import com.reustonium.lunchpals.data.remote.main.PalsService;
import com.reustonium.lunchpals.injection.ApplicationContext;
import com.reustonium.lunchpals.injection.module.ApplicationModule;

/**
 * Created by andrew on 2/25/16.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext Context context();
    Application application();
    PalsService palsService();
    LoginService loginService();
    DataManager dataManager();
}
