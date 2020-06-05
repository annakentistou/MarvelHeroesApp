package com.codehub.marvelheroesapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
import com.codehub.marvelheroesapp.Adapters.Adapter;
import com.codehub.marvelheroesapp.R;
import com.codehub.marvelheroesapp.json.DataModel;
import com.codehub.marvelheroesapp.json.HeroesModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragmentOld extends Fragment {

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    private View view;
    private RecyclerView recyclerView;
    private Adapter myadapter;
    private List<HeroesModel> heroes;
    private static String JSON_URL="https://gateway.marvel.com/v1/public/characters?nameStartsWith=B&limit=100&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private static String JSON_URL1="https://gateway.marvel.com/v1/public/characters?nameStartsWith=Dr&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private static String JSON_URL2="https://gateway.marvel.com/v1/public/characters?nameStartsWith=H&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private static String JSON_URL3="https://gateway.marvel.com/v1/public/characters?nameStartsWith=Ir&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private static String JSON_URL4="https://gateway.marvel.com/v1/public/characters?nameStartsWith=M&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private static String JSON_URL5="https://gateway.marvel.com/v1/public/characters?nameStartsWith=Sp&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private static String JSON_URL6="https://gateway.marvel.com/v1/public/characters?nameStartsWith=Th&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
    private static String JSON_URL7="https://gateway.marvel.com/v1/public/characters?nameStartsWith=W&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";
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
            }) ;

            queue.add(stringRequest);

            //add stringRequest1
            StringRequest stringRequest1= new StringRequest(Request.Method.GET, JSON_URL1, new Response.Listener<String>() {
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
            }) ;

            queue.add(stringRequest1);

            //add stringRequest2
            StringRequest stringRequest2= new StringRequest(Request.Method.GET, JSON_URL2, new Response.Listener<String>() {
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
            }) ;

            queue.add(stringRequest2);

            //add stringRequest3
            StringRequest stringRequest3= new StringRequest(Request.Method.GET, JSON_URL3, new Response.Listener<String>() {
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
            }) ;

            queue.add(stringRequest3);

            //add stringRequest4
            StringRequest stringRequest4= new StringRequest(Request.Method.GET, JSON_URL4, new Response.Listener<String>() {
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
            }) ;

            queue.add(stringRequest4);

            //add stringRequest5
            StringRequest stringRequest5= new StringRequest(Request.Method.GET, JSON_URL5, new Response.Listener<String>() {
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
            }) ;

            queue.add(stringRequest5);

            //add stringRequest6
            StringRequest stringRequest6= new StringRequest(Request.Method.GET, JSON_URL6, new Response.Listener<String>() {
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
            }) ;

            queue.add(stringRequest6);

            //add stringRequest7
            StringRequest stringRequest7= new StringRequest(Request.Method.GET, JSON_URL7, new Response.Listener<String>() {
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

            queue.add(stringRequest7);
        }

        //31/5/2020 search area
 /*   @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.top_app_bar, menu);

        MenuItem searchItem = menu.findItem(R.id.search_view_top_bar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myadapter.getFilter().filter(newText);
                return false;
            }
        });

    }*/

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

