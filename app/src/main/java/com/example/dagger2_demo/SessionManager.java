package com.example.dagger2_demo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.dagger2_demo.di.auth.AuthResource;
import com.example.dagger2_demo.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {
    private static final String TAG = "SessionManager";

    private MediatorLiveData<AuthResource<User>> catchUser= new MediatorLiveData<>();

    @Inject
    public SessionManager() {
    }

    public void authWithId(final LiveData<AuthResource<User>> source){
        if(catchUser != null){
            catchUser.setValue(AuthResource.loading((User) null));
            catchUser.addSource(source, new Observer<AuthResource<User>>() {
                @Override
                public void onChanged(AuthResource<User> userAuthResource) {
                    catchUser.setValue(userAuthResource);
                    catchUser.removeSource(source);
                }
            });
        }

    }

    private void logout(){
        Log.d(TAG,"Logging out.....");
        catchUser.setValue(AuthResource.<User>logout());
    }

    public LiveData<AuthResource<User>> getAuthUser(){
        return catchUser;
    }
}
