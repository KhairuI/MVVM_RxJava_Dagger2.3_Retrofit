package com.example.dagger2_demo.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.dagger2_demo.SessionManager;
import com.example.dagger2_demo.di.auth.AuthResource;
import com.example.dagger2_demo.model.User;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";
    private final SessionManager sessionManager;

    @Inject
    public ProfileViewModel( SessionManager sessionManager) {
        this.sessionManager= sessionManager;
        Log.d(TAG, "ProfileViewModel: Profile View Model is ready..");

    }

    public LiveData<AuthResource<User>> getAuthenticateUser(){
        return sessionManager.getAuthUser();
    }
}
