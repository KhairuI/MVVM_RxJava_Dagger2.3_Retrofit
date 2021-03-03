package com.example.dagger2_demo.di.auth;

import androidx.lifecycle.ViewModel;
import com.example.dagger2_demo.di.ViewModelKey;
import com.example.dagger2_demo.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);
}
