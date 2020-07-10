package com.codehub.marvelheroesapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codehub.marvelheroesapp.Adapters.TabsAdapter;
import com.codehub.marvelheroesapp.DatabaseFiles.NewDbUsers;
import com.codehub.marvelheroesapp.DatabaseFiles.User;
import com.codehub.marvelheroesapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.tabs.TabLayout;

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
    TabsAdapter tabsAdapter;
    ViewPager viewPager;
    private String intent_username;
    private static final int PICK_IMAGE = 1;
    private NotificationManager notificationManager;
    GoogleSignInClient googleSignInClient;
    private static final int PERMISSION_REQUEST_CODE = 100;
    User userInfo;
    private NewDbUsers db;
    Dialog communication, signοut_dlg, no_internet, switchOnOff;
    BadgeDrawable badgeDrawable;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new NewDbUsers(getApplicationContext());

        communication = new Dialog(this);
        signοut_dlg = new Dialog(this);
        no_internet = new Dialog(this);
        switchOnOff = new Dialog(this);

        progress = new ProgressDialog();
        progress.progressDialog(MainActivity.this);

        noConnection();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        displayData();

        bottomNav = findViewById(R.id.bottom_navigation);
        badgeDrawable = bottomNav.getOrCreateBadge(R.id.notifications);
        badgeDrawable.setBackgroundColor(Color.RED);
        badgeDrawable.setBadgeTextColor(Color.WHITE);
        badgeDrawable.setMaxCharacterCount(2000);
        badgeDrawable.setNumber(3);
        badgeDrawable.setVisible(true);
     /*      if(badgeDrawable !=null){
            badgeDrawable.setVisible(false);
            badgeDrawable.clearNumber();
        }*/
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
                        gotoActivity(SearchActivity.class);
                        break;
                    case R.id.myfavoriteList:
                        gotoActivity(FavoritesList.class);
                        break;
                    case R.id.notifications:
                        Intent intent = new Intent(MainActivity.this, NotificationsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        final PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

                        String title = "Marvel App";
                        String message = "There is no Notifications";
                        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
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

    public void noConnection() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            no_internet.show();
            no_internet.setCancelable(false);
            no_internet.setContentView(R.layout.no_internet_dialog);
            no_internet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            no_internet.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

            Button tryAgain = no_internet.findViewById(R.id.try_again);
            tryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            });

        } else {
            progress.progressDialog(MainActivity.this);
        }
    }

    private void displayData() {
        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);

        try {
            intent_username = getIntent().getStringExtra("TAKE_USERNAME");
            userInfo = db.getUser(intent_username);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

        if (intent_username != null) {
            ((TextView) header.findViewById(R.id.user_name)).setText(userInfo.getName());
            ((TextView) header.findViewById(R.id.user_email)).setText(userInfo.getEmail());

        } else {
            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("799808644087-nbq5nju84r2f7i83lm6rgbkmpvgmbdvb.apps.googleusercontent.com")
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
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "You should grant permission", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{
                                Manifest.permission.CAMERA
                        }, PERMISSION_REQUEST_CODE);
                    }
                    return;
                } else {
                    Intent upload_img = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(upload_img, 0);
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.gallery:
                Intent upload_img = new Intent(Intent.ACTION_GET_CONTENT);
                upload_img.addCategory(Intent.CATEGORY_OPENABLE);
                upload_img.setType("image/*");
                startActivityForResult(Intent.createChooser(upload_img, "GET_IMAGE"), PICK_IMAGE);
                break;
            case R.id.sign_out:
                signOutPopup();
                break;
            case R.id.communication:
                communicationPopup();
                break;
            case R.id.about:
                gotoActivity(About.class);
                break;
            case R.id.settings:
                settings();
                break;
            default:
                break;
        }
        return true;
    }

    public void communicationPopup() {
        communication.setContentView(R.layout.communication_dialog);
        ImageButton cancel = communication.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communication.dismiss();
            }
        });
        communication.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        communication.show();
    }

    public void signOutPopup() {
        signοut_dlg.setContentView(R.layout.logout_dialog);
        signοut_dlg.setCancelable(false);
        Button cancelbtn, soutbtn;
        cancelbtn = signοut_dlg.findViewById(R.id.cancel_btn);
        soutbtn = signοut_dlg.findViewById(R.id.sign_out_btn);

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signοut_dlg.dismiss();
            }
        });

        soutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent_username != null) {
                    gotoActivity(LoginActivity.class);
                    finish();
                } else {
                    googleSignInClient.signOut()
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    gotoActivity(LoginActivity.class);
                                    finish();
                                }
                            });
                }
            }
        });
        signοut_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        signοut_dlg.show();
    }

    public void settings() {
        switchOnOff.setContentView(R.layout.settings);
        switchOnOff.setCancelable(true);
        final SwitchMaterial switch_on;
        switch_on = switchOnOff.findViewById(R.id.switchOnOff);
        switch_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch_on.isChecked()) {

                }
            }
        });

        switchOnOff.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        switchOnOff.show();
    }

    private void gotoActivity(Class activityName) {
        Intent intent = new Intent(MainActivity.this, activityName);
        startActivity(intent);
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
            } else if (requestCode == 0) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}