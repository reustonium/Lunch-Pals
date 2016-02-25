package reustonium.com.lunchpals.injection.component;

import dagger.Component;
import reustonium.com.lunchpals.injection.PerActivity;
import reustonium.com.lunchpals.injection.module.ActivityModule;
import reustonium.com.lunchpals.ui.main.MainActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
