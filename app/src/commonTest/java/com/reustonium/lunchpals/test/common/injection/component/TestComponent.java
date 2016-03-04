package com.reustonium.lunchpals.test.common.injection.component;

import com.reustonium.lunchpals.injection.component.ApplicationComponent;
import com.reustonium.lunchpals.test.common.injection.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {
}
