package com.example.dagger2_demo.di.main;

import com.example.dagger2_demo.adapter.MyAdapter;
import com.example.dagger2_demo.api.mainapi.MainApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @Provides
    static MyAdapter provideAdapter(){
        return new MyAdapter();
    }

    @Provides
    static MainApi provideMainApi(Retrofit retrofit){
        return  retrofit.create(MainApi.class);
    }
}
