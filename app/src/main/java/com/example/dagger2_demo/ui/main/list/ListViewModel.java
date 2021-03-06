package com.example.dagger2_demo.ui.main.list;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dagger2_demo.SessionManager;
import com.example.dagger2_demo.api.mainapi.MainApi;
import com.example.dagger2_demo.model.Post;
import com.example.dagger2_demo.ui.main.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    private static final String TAG = "ListViewModel";
    private SessionManager sessionManager;
    private MainApi mainApi;

    private MediatorLiveData<Resource<List<Post>>> posts;

    @Inject
    public ListViewModel(SessionManager sessionManager, MainApi mainApi) {
        this.sessionManager = sessionManager;
        this.mainApi = mainApi;
        Log.d(TAG, "ListViewModel: List view model working......");

    }

    public LiveData<Resource<List<Post>>> observePost(){

        if(posts==null){
            posts= new MediatorLiveData<>();
            posts.setValue(Resource.loading((List<Post>)null));

            final LiveData<Resource<List<Post>>> source= LiveDataReactiveStreams.fromPublisher(
                    mainApi.getPost(sessionManager.getAuthUser().getValue().data.getId())
                    .onErrorReturn(new Function<Throwable, List<Post>>() {
                        @Override
                        public List<Post> apply(Throwable throwable) throws Throwable {
                            Log.e(TAG,"apply: "+throwable);
                            Post post= new Post();
                            post.setId(-1);
                            ArrayList<Post> posts1= new ArrayList<>();
                            posts1.add(post);
                            return posts1;
                        }
                    })
                    .map(new Function<List<Post>, Resource<List<Post>>>() {
                        @Override
                        public Resource<List<Post>> apply(List<Post> posts) throws Throwable {
                            if(posts.size()>0){
                                if(posts.get(0).getId()==-1){
                                    return  Resource.error("Data not retrieve",null);
                                }
                            }
                            return Resource.success(posts);
                        }
                    })
                    .subscribeOn(Schedulers.io())
            );

            posts.addSource(source, new Observer<Resource<List<Post>>>() {
                @Override
                public void onChanged(Resource<List<Post>> listResource) {
                    posts.setValue(listResource);
                    posts.removeSource(source);
                }
            });
        }

        return posts;
    }
}
