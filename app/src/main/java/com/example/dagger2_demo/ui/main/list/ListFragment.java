package com.example.dagger2_demo.ui.main.list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dagger2_demo.R;
import com.example.dagger2_demo.adapter.MyAdapter;
import com.example.dagger2_demo.model.Post;
import com.example.dagger2_demo.ui.main.Resource;
import com.example.dagger2_demo.ui.main.profile.ProfileViewModel;
import com.example.dagger2_demo.utils.VerticalSpacing;
import com.example.dagger2_demo.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ListFragment extends DaggerFragment {

    private static final String TAG = "ListFragment";
    private ListViewModel listViewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Inject
    MyAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView= view.findViewById(R.id.recycleViewId);
        progressBar= view.findViewById(R.id.listProgressId);

        initViewModel();
        setData();
        subscribePost();
    }

    private void initViewModel() {
        listViewModel= new ViewModelProvider(this,providerFactory).get(ListViewModel.class);
    }

    private void subscribePost(){
        listViewModel.observePost().removeObservers(getViewLifecycleOwner());
        listViewModel.observePost().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {
                if(listResource!= null){
                    switch (listResource.status){
                        case LOADING:

                            break;
                        case SUCCESS:
                            progressBar.setVisibility(View.GONE);
                            adapter.setPosts(listResource.data);
                            break;

                        case ERROR:
                            Toast.makeText(getActivity(), ""+listResource.message, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

            }
        });
    }

    private void setData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        VerticalSpacing verticalSpacing= new VerticalSpacing(15);
        recyclerView.addItemDecoration(verticalSpacing);
        recyclerView.setAdapter(adapter);

    }
}