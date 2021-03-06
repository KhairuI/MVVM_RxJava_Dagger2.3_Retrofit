package com.example.dagger2_demo.di.main;

import androidx.lifecycle.ViewModel;

import com.example.dagger2_demo.di.ViewModelKey;
import com.example.dagger2_demo.ui.auth.AuthViewModel;
import com.example.dagger2_demo.ui.main.list.ListViewModel;
import com.example.dagger2_demo.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    public abstract ViewModel bindListViewModel(ListViewModel viewModel);

}
