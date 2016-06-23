package com.reustonium.lunchpals.injection.component;

import android.app.Application;
import android.content.Context;

import com.reustonium.lunchpals.injection.ApplicationContext;
import com.reustonium.lunchpals.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

<<<<<<< HEAD
/**
 * Created by Andrew on 6/20/2016.
 */
=======
>>>>>>> d5a53c60ab47c63e2116e2a684b08cfef4a1189f
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();
    Application application();

}
