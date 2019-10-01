package com.techment.sampleapp.module;

import com.techment.sampleapp.main.MainContract;
import com.techment.sampleapp.scopes.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityMvpModule {
    private final MainContract.View mView;

    public MainActivityMvpModule(MainContract.View mView){
        this.mView = mView;
    }

    @Provides
    @ActivityScope
    MainContract.View provideView(){
        return mView;
    }
}
