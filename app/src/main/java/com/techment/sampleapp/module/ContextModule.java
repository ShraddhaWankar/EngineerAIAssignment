package com.techment.sampleapp.module;

import android.content.Context;

import com.techment.sampleapp.qualifier.ApplicationContext;
import com.techment.sampleapp.scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private Context context;

    public ContextModule(Context context){
        this.context = context;
    }
    @Provides
    @ApplicationScope
    @ApplicationContext
    public Context provideContext(){
        return context;
    }
}
