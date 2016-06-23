package com.reustonium.lunchpals.injection.component;

import com.reustonium.lunchpals.ui.main.MainActivity;
import com.reustonium.lunchpals.injection.PerActivity;
import com.reustonium.lunchpals.injection.module.ActivityModule;
<<<<<<< HEAD
import com.reustonium.lunchpals.ui.signup.SignUpActivity;

import dagger.Component;

=======

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 */
>>>>>>> d5a53c60ab47c63e2116e2a684b08cfef4a1189f
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
<<<<<<< HEAD
    void inject(SignUpActivity signUpActivity);
}
=======

}
>>>>>>> d5a53c60ab47c63e2116e2a684b08cfef4a1189f
