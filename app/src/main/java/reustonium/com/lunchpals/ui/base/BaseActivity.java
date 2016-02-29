package reustonium.com.lunchpals.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import reustonium.com.lunchpals.LunchpalsApplication;
import reustonium.com.lunchpals.injection.component.ActivityComponent;
import reustonium.com.lunchpals.injection.component.DaggerActivityComponent;
import reustonium.com.lunchpals.injection.module.ActivityModule;

/**
 * Created by andrew on 2/25/16.
 */
public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(LunchpalsApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }
}
