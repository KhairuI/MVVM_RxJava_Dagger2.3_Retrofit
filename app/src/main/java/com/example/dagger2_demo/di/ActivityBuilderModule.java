package com.example.dagger2_demo.di;

import com.example.dagger2_demo.di.auth.AuthModule;
import com.example.dagger2_demo.di.auth.AuthViewModelsModule;
import com.example.dagger2_demo.ui.auth.AuthActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {


    @ContributesAndroidInjector(
            modules = {AuthViewModelsModule.class, AuthModule.class}
    )
    abstract AuthActivity contributeAuthActivity();

}
