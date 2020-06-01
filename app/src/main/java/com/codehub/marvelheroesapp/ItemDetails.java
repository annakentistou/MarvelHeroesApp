package com.codehub.marvelheroesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class ItemDetails extends AppCompatActivity {

    TextView Title, subtitle;
    ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        getIncomingIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {

            Intent share_intent = new Intent(Intent.ACTION_SEND);
            share_intent.setType("plain/text");
            share_intent.putExtra(Intent.EXTRA_SUBJECT, "Hello"); //for subject take the Heros' name
            share_intent.putExtra(Intent.EXTRA_TEXT, "Hello too"); //for body take the image url
            startActivity(Intent.createChooser(share_intent, "Share"));
        }
        return true;
    }

    //get values from recycler view and set them to ItemDetails
    private void getIncomingIntent() {
        imageview = findViewById(R.id.thumbnail);
        Title = findViewById(R.id.title);
        subtitle = findViewById(R.id.subTitle);
        Intent intent = getIntent();

        if (getIntent().hasExtra("title") && getIntent().hasExtra("subtitle") && getIntent().hasExtra("image")) {
            String title = intent.getStringExtra("title");
            Title.setText(title);
            String subTitle = intent.getStringExtra("subtitle");
            subtitle.setText(subTitle);
            String image = intent.getStringExtra("image");
            Picasso.get().load(image).into(imageview);
        }
    }
}


