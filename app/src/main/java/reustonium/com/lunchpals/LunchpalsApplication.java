package reustonium.com.lunchpals;

import android.app.Application;
import android.content.Context;

import reustonium.com.lunchpals.injection.component.ApplicationComponent;
import reustonium.com.lunchpals.injection.component.DaggerApplicationComponent;
import reustonium.com.lunchpals.injection.module.ApplicationModule;

public class LunchpalsApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static LunchpalsApplication get(Context context) {
        return (LunchpalsApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
