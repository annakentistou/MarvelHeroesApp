package com.codehub.marvelheroesapp.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.codehub.marvelheroesapp.Adapters.Adapter;
import com.codehub.marvelheroesapp.R;
import com.codehub.marvelheroesapp.json.HeroesModel;
import com.codehub.marvelheroesapp.viewmodels.CharViewModelNew;

import java.util.List;

public class FirstFragment extends Fragment {

    private CharViewModelNew viewModel; //initialize ViewModel

    private View view;
    private RecyclerView recyclerView;
    private Adapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(CharViewModelNew.class);

        //Request queue
        viewModel.getStream().observe(getActivity(), new Observer<List<HeroesModel>>() {
            @Override
            public void onChanged(@Nullable List<HeroesModel> heroes) {
                Log.i("response", heroes.toString());

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setHasFixedSize(true);
                myAdapter = new Adapter(getContext(), heroes);
                recyclerView.setAdapter(myAdapter);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        viewModel.makeRequest(queue);
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        if (view != null) {
            recyclerView = view.findViewById(R.id.recycler_view_for_all);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}

