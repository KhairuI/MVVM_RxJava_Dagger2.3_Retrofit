package com.example.dagger2_demo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dagger2_demo.R;
import com.example.dagger2_demo.model.Post;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.PostViewHolder> {

    private List<Post> posts = new ArrayList<>();

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        ((PostViewHolder)holder).bind(posts.get(position));

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
    public void setPosts(List<Post> posts){
        this.posts = posts;
        notifyDataSetChanged();
    }



    public class PostViewHolder extends RecyclerView.ViewHolder{

        TextView title;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.singleTextId);
        }

        public void bind(Post post){
            title.setText(post.getTitle());
        }
    }
}
