package com.example.dagger2_demo.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;


import com.bumptech.glide.RequestManager;
import com.example.dagger2_demo.R;
import com.example.dagger2_demo.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    private static final String TAG="AuthActivity";
    private AuthViewModel authViewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        initViewModel();
        setLogo();

    }

    private void initViewModel() {
        /*authViewModel= new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(AuthViewModel.class);*/
        authViewModel= new ViewModelProvider(getViewModelStore(),providerFactory).get(AuthViewModel.class);
    }

    private void setLogo(){
        requestManager.load(logo).into((ImageView) findViewById(R.id.logoImageId));
    }



}