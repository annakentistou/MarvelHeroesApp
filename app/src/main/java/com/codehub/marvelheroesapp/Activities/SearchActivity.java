package com.codehub.marvelheroesapp.Activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.codehub.marvelheroesapp.Adapters.Adapter;
import com.codehub.marvelheroesapp.R;
import com.codehub.marvelheroesapp.json.HeroesModel;
import com.codehub.marvelheroesapp.viewmodels.CharViewModelNew;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import static com.codehub.marvelheroesapp.CreateNotificationChannel.CHANNEL_ID;

public class SearchActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    RecyclerView recyclerView;
    Adapter myAdapter;
    private List<HeroesModel> filtered;
    private CharViewModelNew viewModel;
    private NotificationManager notificationManager;
    BadgeDrawable badgeDrawable;
    ProgressDialog progress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.recycler_view_for_all);

        progress = new ProgressDialog();
        progress.progressDialog(SearchActivity.this);

        bottomNav = findViewById(R.id.bottom_navigation);
        badgeDrawable = bottomNav.getOrCreateBadge(R.id.notifications);
        badgeDrawable.setBackgroundColor(Color.RED);
        badgeDrawable.setBadgeTextColor(Color.WHITE);
        badgeDrawable.setMaxCharacterCount(2000);
        badgeDrawable.setNumber(3);
        badgeDrawable.setVisible(true);

        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        viewModel = new ViewModelProvider(this).get(CharViewModelNew.class);

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

        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_page:
                       goToActivity(MainActivity.class);
                        break;
                    case R.id.search_view:
                        break;
                    case R.id.myfavoriteList:
                        goToActivity(FavoritesList.class);
                        break;
                    case R.id.notifications:
                        Intent notif_intent = new Intent(SearchActivity.this, NotificationsActivity.class);
                        notif_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        final PendingIntent pendingIntent = PendingIntent.getActivity(SearchActivity.this, 0, notif_intent, 0);

                        String title = "Marvel App";
                        String message = "There is no Notifications";
                        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true)
                                .build();
                        notificationManager.notify(1, notification);

                        if(badgeDrawable.isVisible()){
                            bottomNav.removeBadge(R.id.notifications);
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void goToActivity(Class activityName){
        Intent intent = new Intent(SearchActivity.this, activityName);
        startActivity(intent);
    }
}

