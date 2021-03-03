package com.example.dagger2_demo.ui.auth;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.dagger2_demo.api.authapi.AuthApi;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    private static final String TAG="AuthViewModel";
    private final AuthApi authApi;

    @Inject
    public AuthViewModel(AuthApi authApi) {
        this.authApi= authApi;
        Log.d(TAG,"AuthViewModel is working...");

        if(this.authApi==null){
            Log.d(TAG,"API is NULL...");
        }
        else {
            Log.d(TAG,"API is Not NULL...");
        }
    }
}
