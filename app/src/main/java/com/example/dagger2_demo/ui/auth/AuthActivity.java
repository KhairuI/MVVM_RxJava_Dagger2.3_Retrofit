package com.example.dagger2_demo.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.bumptech.glide.RequestManager;
import com.example.dagger2_demo.R;
import com.example.dagger2_demo.di.auth.AuthResource;
import com.example.dagger2_demo.model.User;
import com.example.dagger2_demo.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    private static final String TAG="AuthActivity";
    private AuthViewModel authViewModel;
    private EditText editText;
    private Button button;
    private ProgressBar progressBar;

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
        editText= findViewById(R.id.editTextId);
        button= findViewById(R.id.loginButtonId);
        progressBar= findViewById(R.id.progressId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        subscribeObserver();

    }

    private void subscribeObserver() {

        authViewModel.observeUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource != null){
                    switch (userAuthResource.status){
                        case LOADING:
                            showProgressBar(true);
                            break;

                        case AUTHENTICATED:
                            showProgressBar(false);
                            Log.d(TAG,"Login Success: "+userAuthResource.data.getEmail());
                            break;

                        case ERROR:
                            showProgressBar(false);
                            Toast.makeText(AuthActivity.this, userAuthResource.message+"\n Enter number 1 to 10", Toast.LENGTH_SHORT).show();
                            break;

                        case NOT_AUTHENTICATED:
                            showProgressBar(false);
                            break;
                    }
                }
            }
        });
    }

    private void showProgressBar(boolean isVisible){
        if(isVisible){
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void login() {
        if(TextUtils.isEmpty(editText.getText().toString())){
            Toast.makeText(this, "Enter user id", Toast.LENGTH_SHORT).show();
            return;
        }

        authViewModel.authenticateWithId(Integer.parseInt(editText.getText().toString()));
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