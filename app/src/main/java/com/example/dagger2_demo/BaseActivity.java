package com.example.dagger2_demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.dagger2_demo.di.auth.AuthResource;
import com.example.dagger2_demo.model.User;
import com.example.dagger2_demo.ui.auth.AuthActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObserver();
    }

    private void subscribeObserver(){
        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {

                if(userAuthResource != null){
                    switch (userAuthResource.status){
                        case LOADING:
                            break;

                        case AUTHENTICATED:
                            Log.d(TAG,"Login Success: "+userAuthResource.data.getEmail());
                            break;

                        case ERROR:
                            break;

                        case NOT_AUTHENTICATED:
                            goToAuthActivity();
                            break;
                    }
                }
            }
        });
    }

    private void goToAuthActivity(){
        Intent intent= new Intent(this,AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
