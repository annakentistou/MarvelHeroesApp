package com.codehub.marvelheroesapp.Activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.codehub.marvelheroesapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.codehub.marvelheroesapp.CreateNotificationChannel.CHANNEL_ID;

public class NotificationsActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        bottomNav = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_page:
                        goToActivity(MainActivity.class);
                        break;
                    case R.id.search_view:
                        goToActivity(SearchActivity.class);
                        break;
                    case R.id.myfavoriteList:
                        goToActivity(FavoritesList.class);
                        break;
                    case R.id.notifications:

                        break;
                }
                return false;
            }
        });
    }

    private void goToActivity(Class activityName){
        Intent intent = new Intent(NotificationsActivity.this, activityName);
        startActivity(intent);
    }
}
