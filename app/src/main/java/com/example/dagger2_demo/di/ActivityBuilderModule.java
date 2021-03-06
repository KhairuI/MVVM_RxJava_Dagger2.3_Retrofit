package com.example.dagger2_demo.di;

import com.example.dagger2_demo.di.auth.AuthModule;
import com.example.dagger2_demo.di.auth.AuthViewModelsModule;
import com.example.dagger2_demo.di.main.MainFragmentBuilderModule;
import com.example.dagger2_demo.di.main.MainModule;
import com.example.dagger2_demo.di.main.MainViewModelsModule;
import com.example.dagger2_demo.ui.auth.AuthActivity;
import com.example.dagger2_demo.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {


    // Auth Activity
    @ContributesAndroidInjector(
            modules = {AuthViewModelsModule.class, AuthModule.class}
    )
    abstract AuthActivity contributeAuthActivity();

    // Main Activity
    @ContributesAndroidInjector(
            modules = {MainFragmentBuilderModule.class, MainViewModelsModule.class, MainModule.class}
    )
    abstract MainActivity contributeMainActivity();

}
