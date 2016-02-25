package reustonium.com.lunchpals;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class LunchpalsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(BuildConfig.DEBUG)
                .build();
        Fabric.with(fabric);
    }

    public static LunchpalsApplication get(Context context) {
        return (LunchpalsApplication) context.getApplicationContext();
    }
}
