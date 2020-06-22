package com.codehub.marvelheroesapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.codehub.marvelheroesapp.Adapters.ComicsAdapter;
import com.codehub.marvelheroesapp.R;
import com.codehub.marvelheroesapp.json.ComicsModel;
import com.codehub.marvelheroesapp.json.CreatorsNameModel;
import com.codehub.marvelheroesapp.json.DataComicsModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private ComicsAdapter myadapter;
    private List<ComicsModel> comics;
    private List<CreatorsNameModel> creators;
    private static String JSON_URL = "https://gateway.marvel.com/v1/public/comics?limit=30&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_second, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        View view = getView();
        if (view != null) {
            recyclerView = view.findViewById(R.id.recycler_view_for_all);
        }
        comics = new ArrayList<>();
        extractComicsInfo();
    }

    private void extractComicsInfo() {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    DataComicsModel dataModel = new Gson().fromJson(response, DataComicsModel.class);

                    List<ComicsModel> array;

                    array = dataModel.getData().getResults();
                    for (int i = 0; i < array.size(); i++) {
                        ComicsModel model = array.get(i);
                        comics.add(model);
                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    myadapter = new ComicsAdapter(getContext(), comics);
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
        });

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
    public void onDestroy() {
        super.onDestroy();
    }

}
