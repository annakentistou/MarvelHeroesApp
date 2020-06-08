package com.codehub.marvelheroesapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

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

public class CharViewModel extends ViewModel {
    private List<HeroesModel> heroes;
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

    public void makeRequest(RequestQueue queue){

        StringRequest stringRequest= new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    DataModel dataModel = new Gson().fromJson(response, DataModel.class);
                    if(heroes == null) {
                        heroes = new ArrayList<>();
                    }
                    List<HeroesModel> array;
                    array = dataModel.getData().getResults();
                    for (int i = 0; i < array.size(); i++) {
                        HeroesModel model = array.get(i);
                        heroes.add(model);
                    }
                    /*stream.postValue(heroes);*/
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

        //add stringRequest1
        StringRequest stringRequest1= new StringRequest(Request.Method.GET, JSON_URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    DataModel dataModel = new Gson().fromJson(response, DataModel.class);

                    List<HeroesModel> array;
                    array = dataModel.getData().getResults();
                    for (int i = 0; i < array.size(); i++) {
                        HeroesModel model = array.get(i);
                        heroes.add(model);
                    }
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

        queue.add(stringRequest1);

        //add stringRequest2
        StringRequest stringRequest2= new StringRequest(Request.Method.GET, JSON_URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    DataModel dataModel = new Gson().fromJson(response, DataModel.class);

                    List<HeroesModel> array;
                    array = dataModel.getData().getResults();
                    for (int i = 0; i < array.size(); i++) {
                        HeroesModel model = array.get(i);
                        heroes.add(model);
                    }
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

        queue.add(stringRequest2);

        //add stringRequest3
        StringRequest stringRequest3= new StringRequest(Request.Method.GET, JSON_URL3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    DataModel dataModel = new Gson().fromJson(response, DataModel.class);

                    List<HeroesModel> array;
                    array = dataModel.getData().getResults();
                    for (int i = 0; i < array.size(); i++) {
                        HeroesModel model = array.get(i);
                        heroes.add(model);
                    }

                    Log.i("IRON", dataModel.toString());

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

        queue.add(stringRequest3);

        //add stringRequest4
        StringRequest stringRequest4= new StringRequest(Request.Method.GET, JSON_URL4, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    DataModel dataModel = new Gson().fromJson(response, DataModel.class);

                    List<HeroesModel> array;
                    array = dataModel.getData().getResults();
                    for (int i = 0; i < array.size(); i++) {
                        HeroesModel model = array.get(i);
                        heroes.add(model);
                    }

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

        queue.add(stringRequest4);

        //add stringRequest5
        StringRequest stringRequest5= new StringRequest(Request.Method.GET, JSON_URL5, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    DataModel dataModel = new Gson().fromJson(response, DataModel.class);

                    List<HeroesModel> array;
                    array = dataModel.getData().getResults();
                    for (int i = 0; i < array.size(); i++) {
                        HeroesModel model = array.get(i);
                        heroes.add(model);
                    }

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

        queue.add(stringRequest5);

        //add stringRequest6
        StringRequest stringRequest6= new StringRequest(Request.Method.GET, JSON_URL6, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    DataModel dataModel = new Gson().fromJson(response, DataModel.class);

                    List<HeroesModel> array;
                    array = dataModel.getData().getResults();
                    for (int i = 0; i < array.size(); i++) {
                        HeroesModel model = array.get(i);
                        heroes.add(model);
                    }

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

        queue.add(stringRequest6);

        //add stringRequest7
        StringRequest stringRequest7= new StringRequest(Request.Method.GET, JSON_URL7, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    DataModel dataModel = new Gson().fromJson(response, DataModel.class);

                    List<HeroesModel> array;
                    array = dataModel.getData().getResults();
                    for (int i = 0; i < array.size(); i++) {
                        HeroesModel model = array.get(i);
                        model.setFavStatus(0);
                        heroes.add(model);
                    }

                    stream.postValue(heroes);
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

        queue.add(stringRequest7);
    }
}