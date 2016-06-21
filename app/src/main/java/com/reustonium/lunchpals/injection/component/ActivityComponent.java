package com.reustonium.lunchpals.injection.component;

import com.reustonium.lunchpals.ui.main.MainActivity;
import com.reustonium.lunchpals.injection.PerActivity;
import com.reustonium.lunchpals.injection.module.ActivityModule;

import dagger.Component;

/**
 * Created by Andrew on 6/21/2016.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}