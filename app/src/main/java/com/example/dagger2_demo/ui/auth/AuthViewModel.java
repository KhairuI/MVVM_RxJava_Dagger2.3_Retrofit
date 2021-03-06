package com.example.dagger2_demo.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dagger2_demo.SessionManager;
import com.example.dagger2_demo.api.authapi.AuthApi;
import com.example.dagger2_demo.di.auth.AuthResource;
import com.example.dagger2_demo.model.User;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private static final String TAG="AuthViewModel";
    private final AuthApi authApi;
    private SessionManager sessionManager;
   // private MediatorLiveData<AuthResource<User>> authUser= new MediatorLiveData<>();

    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager) {
        this.authApi= authApi;
        this.sessionManager= sessionManager;
        Log.d(TAG,"AuthViewModel is working...");
    }

    public void authenticateWithId(int id){
        Log.d(TAG, "authenticateWithId: attempting to login.....");

        sessionManager.authWithId(queryUserId(id));
    }

    private LiveData<AuthResource<User>> queryUserId(int id){

        // convert Flowable to live data
        return LiveDataReactiveStreams.fromPublisher(authApi.getUser(id)
                // if error happen........
                .onErrorReturn(new Function<Throwable, User>() {
                    @Override
                    public User apply(Throwable throwable) throws Throwable {
                        User errorUser= new User();
                        errorUser.setId(-1);
                        return errorUser;
                    }
                }).map(new Function<User, AuthResource<User>>() {
                    @Override
                    public AuthResource<User> apply(User user) throws Throwable {

                        if(user.getId()==-1){
                            return AuthResource.error("Could not Authenticate",(User) null);
                        }
                        return AuthResource.authenticated(user);
                    }
                }).subscribeOn(Schedulers.io()));
    }

    public LiveData<AuthResource<User>> observeAuthState(){
        return sessionManager.getAuthUser();
    }
}
