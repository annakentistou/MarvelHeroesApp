package com.codehub.marvelheroesapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.codehub.marvelheroesapp.Adapters.SeriesAdapter;
import com.codehub.marvelheroesapp.R;


import com.codehub.marvelheroesapp.json.CreatorsNameModel;

import com.codehub.marvelheroesapp.json.DataSeriesModel;
import com.codehub.marvelheroesapp.json.SeriesModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private SeriesAdapter myadapter;
    private List<SeriesModel> series;
    private List<CreatorsNameModel> creators;
    private static String JSON_URL="https://gateway.marvel.com/v1/public/series?limit=30&ts=1&apikey=cbc3143464c6ede991022c465a83c158&hash=96b097677f87d6beb9af6fd11c1bd405";
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        View view = getView();
        if(view != null) {
            recyclerView = view.findViewById(R.id.recycler_view_for_all);
        }
        series = new ArrayList<>();

        extractSeriesInfo();
    }

    private void extractSeriesInfo() {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest= new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    DataSeriesModel dataModel = new Gson().fromJson(response, DataSeriesModel.class);

                    List<SeriesModel> array;
                    array = dataModel.getData().getResults();
                    if(array != null) {
                        for (int i = 0; i < array.size(); i++) {
                            SeriesModel model = array.get(i);
                            series.add(model);
                        }
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    myadapter = new SeriesAdapter(getContext(), series);
                    recyclerView.setAdapter(myadapter);

                    Log.i("response", dataModel.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) ;

        queue.add(stringRequest);
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
    public void onDestroy(){
        super.onDestroy();
    }

}


