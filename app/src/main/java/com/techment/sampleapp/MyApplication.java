package com.techment.sampleapp;

import android.app.Activity;
import android.app.Application;

import com.techment.sampleapp.component.ApplicationComponent;
import com.techment.sampleapp.component.DaggerApplicationComponent;
import com.techment.sampleapp.module.ContextModule;

public class MyApplication extends Application {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder().contextModule(new ContextModule(this)).build();
    }

    public static MyApplication get(Activity activity){
        return (MyApplication) activity.getApplication();
    }

    public ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }
}
