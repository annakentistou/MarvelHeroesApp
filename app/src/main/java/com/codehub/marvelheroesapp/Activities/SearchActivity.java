package com.codehub.marvelheroesapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.codehub.marvelheroesapp.Adapters.Adapter;
import com.codehub.marvelheroesapp.R;
import com.codehub.marvelheroesapp.json.HeroesModel;
import com.codehub.marvelheroesapp.viewmodels.CharViewModel;
import com.codehub.marvelheroesapp.viewmodels.CharViewModelVassilis;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    RecyclerView recyclerView;
    Adapter myAdapter;
    /*private List<HeroesModel> heroes;*/
    private List<HeroesModel> filtered;

    private CharViewModelVassilis viewModel; //initialize ViewModel

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.recycler_view_for_all);

        viewModel = new ViewModelProvider(this).get(CharViewModelVassilis.class);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        //Request queue
        viewModel.getStream().observe(this, new Observer<List<HeroesModel>>() {
            @Override
            public void onChanged(@Nullable List<HeroesModel> heroes) {
                Log.i("response", heroes.toString());
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setHasFixedSize(true);
                myAdapter = new Adapter(getApplicationContext(), heroes);
                recyclerView.setAdapter(myAdapter);

                filtered = new ArrayList<>();
                for (HeroesModel item : heroes) {
                    filtered.add(item);
                }
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        viewModel.makeRequest(queue);

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
        for (HeroesModel item : filtered) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        myAdapter.filterList(filteredList);
    }

    @Override
    public void onPostResume() {
        super.onPostResume();

        //Bottom Navigation Menu management  31/5/2020
        bottomNav = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_page:
                        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.search_view:
                        break;
                    case R.id.myfavoriteList:
                        Intent fav_intent = new Intent(SearchActivity.this, FavoritesList.class);
                        startActivity(fav_intent);
                        break;
                    case R.id.notifications:
                        Intent not_intent = new Intent(SearchActivity.this, NotificationsActivity.class);
                        startActivity(not_intent);
                        break;
                }
                return false;
            }
        });
    }
}

