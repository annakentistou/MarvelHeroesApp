package com.codehub.marvelheroesapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.codehub.marvelheroesapp.Adapters.Adapter;
import com.codehub.marvelheroesapp.Adapters.TabsAdapter;
import com.codehub.marvelheroesapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    MaterialToolbar toolbar;
    NavigationView navigationView;
    BottomNavigationView bottomNav;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TabLayout tabLayout;
    TabItem all, comics, series;
    TabsAdapter tabsAdapter;
    ViewPager viewPager;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        //side menu management
        drawerLayout = findViewById(R.id.main_drawer);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //load fragment
        viewPager = findViewById(R.id.viewpager);

        //tabs management
        tabLayout = findViewById(R.id.tabs);
        all = findViewById(R.id.tab_all);
        comics = findViewById(R.id.tab_comics);
        series = findViewById(R.id.tab_series);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Bottom Navigation Menu management  31/5/2020
        bottomNav = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_page:

                        break;
                    case R.id.search_view:
                        Intent search_intent = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(search_intent);
                        break;
                   case R.id.myfavoriteList:
                        Intent fav_intent = new Intent(MainActivity.this, FavoritesList.class);
                        startActivity(fav_intent);
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

    //Manage side menu items
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //close side menu when you select an item

       /* if (item.getItemId() == R.id.profile) {

        }*/
        if (item.getItemId() == R.id.sign_out) {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
