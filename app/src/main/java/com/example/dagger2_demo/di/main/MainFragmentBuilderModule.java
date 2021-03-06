package com.example.dagger2_demo.di.main;

import com.example.dagger2_demo.ui.main.list.ListFragment;
import com.example.dagger2_demo.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract ListFragment contributeListFragment();
}
