package com.codehub.marvelheroesapp.Activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.codehub.marvelheroesapp.Adapters.FavAdapter;
import com.codehub.marvelheroesapp.DatabaseFiles.FavDB;
import com.codehub.marvelheroesapp.DatabaseFiles.FavoriteHero;
import com.codehub.marvelheroesapp.R;
import com.codehub.marvelheroesapp.json.HeroesModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.codehub.marvelheroesapp.CreateNotificationChannel.CHANNEL_ID;

public class FavoritesList extends AppCompatActivity {
    RecyclerView recyclerView;
    private FavDB favDB;
    private List<FavoriteHero> favHero = new ArrayList<>();
    FavAdapter favAdapter;

    BottomNavigationView bottomNav;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_list);

        recyclerView = findViewById(R.id.recycler_view_for_all);
        recyclerView.setHasFixedSize(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //display back button in toolbar
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        favDB = new FavDB(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView); // set swipe to recyclerview
        loadData();
    }

    private void loadData() {
        if (favHero != null) {
            favHero.clear();
        }
        SQLiteDatabase db = favDB.getReadableDatabase();
        Cursor cursor = favDB.select_all_favorite_list();
        try {
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_TITLE));
                int id = cursor.getInt(cursor.getColumnIndex(FavDB.KEY_ID));
                /*int image = Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavDB.ITEM_IMAGE)));*/
                String image = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_IMAGE));
                FavoriteHero favItem = new FavoriteHero(title, id, image);
                favHero.add(favItem);
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }
        favAdapter = new FavAdapter(getApplicationContext(), favHero);
        recyclerView.setAdapter(favAdapter);
    }

    private ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition(); // get position which is swipe
            final FavoriteHero favItem = favHero.get(position);
            if (direction == ItemTouchHelper.LEFT) {
                favAdapter.notifyItemRemoved(position); // item removed from recyclerview
                favHero.remove(position);
                favDB.remove_fav(favItem.getId());
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
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
                    case R.id.notifications:
                        Intent notif_intent = new Intent(FavoritesList.this, NotificationsActivity.class);
                        notif_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        final PendingIntent pendingIntent = PendingIntent.getActivity(FavoritesList.this, 0, notif_intent, 0);

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
                        break;
                }
                return false;
            }
        });
    }
}