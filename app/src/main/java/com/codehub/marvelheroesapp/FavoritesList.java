package com.codehub.marvelheroesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FavoritesList extends AppCompatActivity {

    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //display back button in toolbar
    }
//back button in Action Bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent Home = new Intent(FavoritesList.this, MainActivity.class);
                startActivity(Home);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        //Bottom Navigation Menu management  31/5/2020
        bottomNav = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_page:
                        Intent intent = new Intent(FavoritesList.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.search_view:
                        Intent search_intent = new Intent(FavoritesList.this, SearchActivity.class);
                        startActivity(search_intent);
                        break;
                    case R.id.myfavoriteList:

                        break;
                    /*case R.id.notifications:
                        Intent not_intent = new Intent(MainActivity.this, NotificationActivity.class);
                        startActivity(not_intent);
                        break;*/
                }
                return false;
            }
        });
    }

}
