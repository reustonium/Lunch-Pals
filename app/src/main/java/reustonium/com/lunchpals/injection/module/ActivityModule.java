package reustonium.com.lunchpals.injection.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import reustonium.com.lunchpals.injection.ActivityContext;

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {

        mActivity = activity;

    }

    @Provides
    Activity provideActivity() {

        return mActivity;

    }

    @Provides
    @ActivityContext
    Context providesContext() {

        return mActivity;

    }
}
