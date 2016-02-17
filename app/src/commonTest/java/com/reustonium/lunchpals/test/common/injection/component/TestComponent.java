package com.reustonium.lunchpals.test.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import com.reustonium.lunchpals.injection.component.ApplicationComponent;
import com.reustonium.lunchpals.test.common.injection.module.ApplicationTestModule;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
