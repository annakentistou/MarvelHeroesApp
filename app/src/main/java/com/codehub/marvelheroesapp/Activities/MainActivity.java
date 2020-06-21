package com.codehub.marvelheroesapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codehub.marvelheroesapp.Adapters.TabsAdapter;
import com.codehub.marvelheroesapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.codehub.marvelheroesapp.CreateNotificationChannel.CHANNEL_ID;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    MaterialToolbar toolbar;
    NavigationView navigationView;
    BottomNavigationView bottomNav;
    TabLayout tabLayout;
    TabItem all, comics, series;
    TabsAdapter tabsAdapter;
    ViewPager viewPager;
    private String intent_username, intent_email;
    private static final int PICK_IMAGE = 1;
    private NotificationManager notificationManager;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        final LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);
        loadingDialog.startLoadindDialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
            }
        }, 3000);

        intent_username = getIntent().getStringExtra("TAKE_FULLNAME");
        intent_email = getIntent().getStringExtra("TAKE_USER_EMAIL");

        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        if (intent_username != null || intent_email != null) {
            ((TextView) header.findViewById(R.id.user_name)).setText(intent_username);
            ((TextView) header.findViewById(R.id.user_email)).setText(intent_email);
        }else{
            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personEmail = acct.getEmail();
                Uri personPhoto = acct.getPhotoUrl();

                ((TextView) header.findViewById(R.id.user_name)).setText(personName);
                ((TextView) header.findViewById(R.id.user_email)).setText(personEmail);
               ImageView img = header.findViewById(R.id.imageView);
                Glide.with(this).load(personPhoto).into(img);
            }
        }

        (header.findViewById(R.id.imageView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upload_img = new Intent(Intent.ACTION_GET_CONTENT);
                upload_img.addCategory(Intent.CATEGORY_OPENABLE);
                upload_img.setType("image/*");
                startActivityForResult(Intent.createChooser(upload_img, "GET_IMAGE"), PICK_IMAGE);
            }
        });
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
                    case R.id.notifications:
                        Intent intent = new Intent(MainActivity.this, NotificationsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        final PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.gallery:
                Intent upload_img = new Intent(Intent.ACTION_GET_CONTENT);
                upload_img.addCategory(Intent.CATEGORY_OPENABLE);
                upload_img.setType("image/*");
                startActivityForResult(Intent.createChooser(upload_img, "GET_IMAGE"), PICK_IMAGE);
                break;
            case R.id.sign_out:
                    googleSignInClient.signOut()
                            .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                }
                            });
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == PICK_IMAGE) {

                ((ImageView) findViewById(R.id.imageView)).setImageBitmap(null);

                Uri mediaUri = data.getData();

                try {
                    InputStream inputStream = getBaseContext().getContentResolver().openInputStream(mediaUri);
                    Bitmap bm = BitmapFactory.decodeStream(inputStream);
                    ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bm);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}