package com.codehub.marvelheroesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ItemDetails extends AppCompatActivity {

    TextView Title, subtitle;
    ImageView imageview;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //display back button in toolbar
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        getIncomingIntent();

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                SharePhoto sharePhoto = new SharePhoto.Builder().
                        setBitmap(bitmap).
                        build();
                if (ShareDialog.canShow(SharePhotoContent.class)) {
                    SharePhotoContent photoContent = new SharePhotoContent.Builder().addPhoto(sharePhoto).build();
                    shareDialog.show(photoContent);
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent Home = new Intent(ItemDetails.this, MainActivity.class);
                startActivity(Home);
                return true;
            case R.id.share:
                Intent share_intent = new Intent(Intent.ACTION_SEND);
                share_intent.setType("image/*");
                share_intent.putExtra(Intent.EXTRA_SUBJECT, "Heroes Name: "); //for subject take the Heros' name
                share_intent.putExtra(Intent.EXTRA_TEXT, "image"); //for body take the image url
                startActivity(Intent.createChooser(share_intent, "Share"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //get values from View Holder and set them to ItemDetails layout
    private void getIncomingIntent() {
        imageview = findViewById(R.id.thumbnail);
        Title = findViewById(R.id.title);
        subtitle = findViewById(R.id.subtitle);
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


