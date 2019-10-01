package com.techment.sampleapp.component;

import android.content.Context;

import com.techment.sampleapp.MyApplication;
import com.techment.sampleapp.module.ContextModule;
import com.techment.sampleapp.module.RetrofitModule;
import com.techment.sampleapp.network.DemoAPI;
import com.techment.sampleapp.qualifier.ApplicationContext;
import com.techment.sampleapp.scopes.ApplicationScope;

import dagger.Component;

@ApplicationScope
@Component(modules = {ContextModule.class, RetrofitModule.class})
public interface ApplicationComponent {
    DemoAPI getAPIInterface();

    @ApplicationContext
    Context getContext();

    void injectApplication(MyApplication application);
}
