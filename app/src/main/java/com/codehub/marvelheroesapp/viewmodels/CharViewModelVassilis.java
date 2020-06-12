package com.codehub.marvelheroesapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codehub.marvelheroesapp.json.DataModel;
import com.codehub.marvelheroesapp.json.HeroesModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CharViewModelVassilis extends ViewModel {
    private static String JSON_URL="https://gateway.marvel.com/v1/public/characters?nameStartsWith=B&limit=100&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private static String JSON_URL1="https://gateway.marvel.com/v1/public/characters?nameStartsWith=H&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private static String JSON_URL2="https://gateway.marvel.com/v1/public/characters?nameStartsWith=Dr&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private static String JSON_URL3="https://gateway.marvel.com/v1/public/characters?nameStartsWith=M&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private static String JSON_URL4="https://gateway.marvel.com/v1/public/characters?nameStartsWith=Ir&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private static String JSON_URL5="https://gateway.marvel.com/v1/public/characters?nameStartsWith=Sp&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private static String JSON_URL6="https://gateway.marvel.com/v1/public/characters?nameStartsWith=Th&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private static String JSON_URL7="https://gateway.marvel.com/v1/public/characters?nameStartsWith=W&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";

    private MutableLiveData<List<HeroesModel>> stream = new MutableLiveData<>();

    public MutableLiveData<List<HeroesModel>> getStream() {
        return stream;
    }

    private void handleResponse(String response) {
        try {
            DataModel dataModel = new Gson().fromJson(response, DataModel.class);

            List<HeroesModel> heroes = stream.getValue();
            if (heroes == null) {
                heroes = new ArrayList<>();
            }

            List<HeroesModel> array;
            array = dataModel.getData().getResults();

            for (int i = 0; i < array.size(); i++) {
                HeroesModel model = array.get(i);
                heroes.add(model);
            }
            stream.postValue(heroes);
            Log.i("response", dataModel.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeRepeatableRequests(final RequestQueue queue, final String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                handleResponse(response);

                if (JSON_URL1.equals(url)) {
                    makeRepeatableRequests(queue, JSON_URL2);
                } else if (JSON_URL2.equals(url)) {
                    makeRepeatableRequests(queue, JSON_URL3);
                } else if (JSON_URL3.equals(url)) {
                    makeRepeatableRequests(queue, JSON_URL4);
                } else if (JSON_URL4.equals(url)) {
                    makeRepeatableRequests(queue, JSON_URL5);
                } else if (JSON_URL5.equals(url)) {
                    makeRepeatableRequests(queue, JSON_URL6);
                } else if (JSON_URL6.equals(url)) {
                    makeRepeatableRequests(queue, JSON_URL7);
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

    public void makeRequest(final RequestQueue queue) {

        StringRequest stringRequest= new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                handleResponse(response);

                makeRepeatableRequests(queue, JSON_URL1);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) ;
        Log.i("response",stream.toString());

        queue.add(stringRequest);
    }
}
