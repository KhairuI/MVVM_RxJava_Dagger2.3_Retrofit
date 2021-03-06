package com.example.dagger2_demo.ui.main.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dagger2_demo.R;
import com.example.dagger2_demo.di.auth.AuthResource;
import com.example.dagger2_demo.model.User;
import com.example.dagger2_demo.ui.auth.AuthViewModel;
import com.example.dagger2_demo.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ProfileFragment extends DaggerFragment {

    private static final String TAG = "ProfileFragment";
    private ProfileViewModel profileViewModel;
    private TextView name, email,phone;

    @Inject
    ViewModelProviderFactory providerFactory;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Toast.makeText(getActivity(), "Profile", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        name= view.findViewById(R.id.nameId);
        email= view.findViewById(R.id.emailId);
        phone= view.findViewById(R.id.phoneId);
        initViewModel();
        subscribeObserver();
    }

    private void initViewModel() {
        profileViewModel= new ViewModelProvider(this,providerFactory).get(ProfileViewModel.class);
    }

    private void subscribeObserver(){
        profileViewModel.getAuthenticateUser().removeObservers(getViewLifecycleOwner());
        profileViewModel.getAuthenticateUser().observe(getViewLifecycleOwner(), new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource!= null){
                    switch (userAuthResource.status){
                        case AUTHENTICATED:
                            setDetails(userAuthResource.data);
                            break;
                        case ERROR:
                            setError(userAuthResource.message);
                            break;
                    }
                }
            }
        });
    }

    private void setError(String message) {
        email.setError(message);
        name.setText("Error");
        phone.setText("Error");
    }

    private void setDetails(User data) {

        name.setText(data.getUsername());
        email.setText(data.getEmail());
        phone.setText(data.getPhone());

    }
}