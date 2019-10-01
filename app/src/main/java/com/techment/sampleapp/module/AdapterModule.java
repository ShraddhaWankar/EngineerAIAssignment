package com.techment.sampleapp.module;

import com.techment.sampleapp.adapter.RecyclerViewAdapter;
import com.techment.sampleapp.main.MainActivity;
import com.techment.sampleapp.scopes.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = {MainActivityContextModule.class})
public class AdapterModule {

    @Provides
    @ActivityScope
    RecyclerViewAdapter getDataList(RecyclerViewAdapter.DataAdapterCallback clickListener){
        return new RecyclerViewAdapter(clickListener);
    }

    @Provides
    @ActivityScope
    RecyclerViewAdapter.DataAdapterCallback getClickListener(MainActivity mainActivity){
        return mainActivity;
    }

}
