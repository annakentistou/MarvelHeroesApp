package com.codehub.marvelheroesapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codehub.marvelheroesapp.json.DataModel;
import com.codehub.marvelheroesapp.json.HeroesModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private Adapter myadapter;
    private List<HeroesModel> heroes;
    private static String JSON_URL="https://gateway.marvel.com/v1/public/characters?nameStartsWith=M&limit=100&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private static String JSON_URL1="https://gateway.marvel.com/v1/public/characters?nameStartsWith=S&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private static String JSON_URL2="https://gateway.marvel.com/v1/public/characters?nameStartsWith=D&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view =  inflater.inflate(R.layout.fragment_first, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        if(view != null) {
            recyclerView = view.findViewById(R.id.recycler_view_for_all);
        }
        heroes = new ArrayList<>();
        extractHeroesInfo();
    }
        private void extractHeroesInfo() {
            RequestQueue queue = Volley.newRequestQueue(getContext());

            StringRequest stringRequest= new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        DataModel dataModel = new Gson().fromJson(response, DataModel.class);

                        List<HeroesModel> array = new ArrayList<>();
                        array = dataModel.getData().getResults();
                        for (int i = 0; i < array.size(); i++) {
                            HeroesModel model = array.get(i);
                            heroes.add(model);
                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        myadapter = new Adapter(getContext(), heroes);
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
    public void onDestroyView(){
        super.onDestroyView();
    }


}

