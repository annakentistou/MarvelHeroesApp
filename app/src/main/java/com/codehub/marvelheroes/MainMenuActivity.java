package com.codehub.marvelheroes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<HeroesInfo> heroes;
    private static String JSON_URL="https://navneet7k.github.io/cars_list.json";
    /*private static String JSON_URL = "https://gateway.marvel.com/v1/public/characters?nameStartsWith=M&limit=100&ts=1&apikey=94bd7ab20112da5e1ae5f197769ecd7a&hash=49b68d02a0d6bbeed0553ccf47ab7d68";*/
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        heroes = new ArrayList<>();
        extractHeroesInfo();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        recyclerView = findViewById(R.id.recycler_view_for_all);

    }

    private void extractHeroesInfo() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject heroesObject = response.getJSONObject(i);

                        HeroesInfo heroesInfo = new HeroesInfo();
                        heroesInfo.setName(heroesObject.getString("name").toString());
                        /*heroesInfo.setDescription(heroesObject.getString("description").toString());
                        heroesInfo.setPath(heroesObject.getString("path"));*/
                        /*heroesInfo.setId(heroesObject.getInt("id"));*/

                        heroes.add(heroesInfo);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new Adapter(getApplicationContext(),heroes);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: ");
            }
        });

        queue.add(jsonArrayRequest);
    }
}
