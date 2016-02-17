package com.reustonium.lunchpals.injection.component;

import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;
import com.reustonium.lunchpals.data.DataManager;
import com.reustonium.lunchpals.data.SyncService;
import com.reustonium.lunchpals.data.local.DatabaseHelper;
import com.reustonium.lunchpals.data.local.PreferencesHelper;
import com.reustonium.lunchpals.data.remote.RibotsService;
import com.reustonium.lunchpals.injection.ApplicationContext;
import com.reustonium.lunchpals.injection.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    RibotsService ribotsService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    Bus eventBus();

}
