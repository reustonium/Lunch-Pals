package com.reustonium.lunchpals.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
<<<<<<< HEAD
 * Created by Andrew on 6/21/2016.
=======
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the Activity to be memorised in the
 * correct component.
>>>>>>> d5a53c60ab47c63e2116e2a684b08cfef4a1189f
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
