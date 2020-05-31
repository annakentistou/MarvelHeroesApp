package com.codehub.marvelheroesapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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

public class SearchActivity extends AppCompatActivity {

    ViewPager viewPager;
    RecyclerView recyclerView;
    Adapter myadapter;
    private List<HeroesModel> heroes;
    private String JSON_URL = "https://gateway.marvel.com/v1/public/characters?nameStartsWith=Bi&limit=100&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private String JSON_URL1 = "https://gateway.marvel.com/v1/public/characters?nameStartsWith=Dr&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private String JSON_URL2 = "https://gateway.marvel.com/v1/public/characters?nameStartsWith=H&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private String JSON_URL3 = "https://gateway.marvel.com/v1/public/characters?nameStartsWith=I&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private String JSON_URL4 = "https://gateway.marvel.com/v1/public/characters?nameStartsWith=M&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private String JSON_URL5 = "https://gateway.marvel.com/v1/public/characters?nameStartsWith=S&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private String JSON_URL6 = "https://gateway.marvel.com/v1/public/characters?nameStartsWith=Th&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private String JSON_URL7 = "https://gateway.marvel.com/v1/public/characters?nameStartsWith=W&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search); //actually it's not a fragment, just an activity
        recyclerView = findViewById(R.id.recycler_view_for_all);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        EditText editText = findViewById(R.id.search_bar);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    //filter for search view 31/5/2020
    private void filter(String text) {
        List<HeroesModel> filteredList = new ArrayList<>();
        for (HeroesModel item: heroes) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        myadapter.filterList(filteredList);
    }
    
    @Override
    public void onPostResume() {
        super.onPostResume();
        heroes = new ArrayList<>();
        extractHeroesInfo();
    }

    private void extractHeroesInfo() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
            //onResponse it's the same with in all requests
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

        //add stringRequest1
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, JSON_URL1, new Response.Listener<String>() {
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

        queue.add(stringRequest1);

        //add stringRequest2
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, JSON_URL2, new Response.Listener<String>() {
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

        queue.add(stringRequest2);

        //add stringRequest3
        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, JSON_URL3, new Response.Listener<String>() {
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

        queue.add(stringRequest3);

        //add stringRequest4
        StringRequest stringRequest4 = new StringRequest(Request.Method.GET, JSON_URL4, new Response.Listener<String>() {
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

        queue.add(stringRequest4);

        //add stringRequest5
        StringRequest stringRequest5 = new StringRequest(Request.Method.GET, JSON_URL5, new Response.Listener<String>() {
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

        queue.add(stringRequest5);

        //add stringRequest6
        StringRequest stringRequest6 = new StringRequest(Request.Method.GET, JSON_URL6, new Response.Listener<String>() {
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

        queue.add(stringRequest6);

        //add stringRequest7
        StringRequest stringRequest7 = new StringRequest(Request.Method.GET, JSON_URL7, new Response.Listener<String>() {
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

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    myadapter = new Adapter(getApplicationContext(), heroes);
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

        queue.add(stringRequest7);
    }
}
