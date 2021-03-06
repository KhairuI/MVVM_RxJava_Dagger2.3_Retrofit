package com.example.dagger2_demo.api.mainapi;

import com.example.dagger2_demo.model.Post;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {
    @GET("posts")
    Flowable<List<Post>> getPost(
            @Query("userId") int id
    );
}
