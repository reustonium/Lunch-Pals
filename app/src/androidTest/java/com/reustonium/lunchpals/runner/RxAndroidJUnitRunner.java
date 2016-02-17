package com.reustonium.lunchpals.runner;

import android.os.Bundle;
import android.support.test.espresso.Espresso;

import rx.plugins.RxJavaPlugins;
import com.reustonium.lunchpals.util.RxIdlingExecutionHook;
import com.reustonium.lunchpals.util.RxIdlingResource;

/**
 * Runner that registers a Espresso Indling resource that handles waiting for
 * RxJava Observables to finish.
 * WARNING - Using this runner will block the tests if the application uses long-lived hot
 * Observables such us event buses, etc.
 */
public class RxAndroidJUnitRunner extends UnlockDeviceAndroidJUnitRunner {

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        RxIdlingResource rxIdlingResource = new RxIdlingResource();
        RxJavaPlugins.getInstance()
                .registerObservableExecutionHook(new RxIdlingExecutionHook(rxIdlingResource));
        Espresso.registerIdlingResources(rxIdlingResource);
    }
}
