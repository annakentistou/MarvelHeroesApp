package com.codehub.marvelheroesapp.Activities;

import android.app.Notification;
import android.app.NotificationManager;
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
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

       /* notificationManager = NotificationManagerCompat.from(this);*/
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        Button btn = findViewById(R.id.click);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String title = "Marvel App";
                String message = "Alert Message";
                Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true)
                        .build();
                notificationManager.notify(1, notification);
            }
        });
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
                        Intent intent = new Intent(NotificationsActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.search_view:
                        Intent search_intent = new Intent(NotificationsActivity.this, SearchActivity.class);
                        startActivity(search_intent);
                        break;
                    case R.id.myfavoriteList:
                        Intent fav_intent = new Intent(NotificationsActivity.this, FavoritesList.class);
                        startActivity(fav_intent);
                        break;
                    case R.id.notifications:

                        break;
                }
                return false;
            }
        });

    }
}
