package com.reustonium.lunchpals.injection.component;

import android.app.Application;
import android.content.Context;

import com.reustonium.lunchpals.injection.ApplicationContext;
import com.reustonium.lunchpals.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();
    Application application();

}
