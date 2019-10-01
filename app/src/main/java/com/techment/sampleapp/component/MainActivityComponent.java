package com.techment.sampleapp.component;

import android.content.Context;

import com.techment.sampleapp.main.MainActivity;
import com.techment.sampleapp.module.AdapterModule;
import com.techment.sampleapp.module.MainActivityMvpModule;
import com.techment.sampleapp.qualifier.ActivityContext;
import com.techment.sampleapp.scopes.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = {AdapterModule.class, MainActivityMvpModule.class}, dependencies = ApplicationComponent.class)
public interface MainActivityComponent {
    @ActivityContext
    Context getContext();

    void injectMainActivity(MainActivity mainActivity);
}
