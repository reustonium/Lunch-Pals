package com.reustonium.lunchpals.injection.component;

import dagger.Component;
import com.reustonium.lunchpals.injection.PerActivity;
import com.reustonium.lunchpals.injection.module.ActivityModule;
import com.reustonium.lunchpals.ui.main.MainActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
