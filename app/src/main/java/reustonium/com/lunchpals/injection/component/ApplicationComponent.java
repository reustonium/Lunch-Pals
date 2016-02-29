package reustonium.com.lunchpals.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import reustonium.com.lunchpals.injection.ApplicationContext;
import reustonium.com.lunchpals.injection.module.ApplicationModule;

/**
 * Created by andrew on 2/25/16.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext Context context();
    Application application();
}
