package com.codehub.marvelheroesapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codehub.marvelheroesapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.codehub.marvelheroesapp.CreateNotificationChannel.CHANNEL_ID;

public class ItemDetails extends AppCompatActivity {

    TextView Title, subtitle;
    ImageView imageview;

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
    }

    //Inflate what top_app_bar menu has
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
                //sharing as text
                Intent intent = getIntent();
                Intent share_intent = new Intent(Intent.ACTION_SEND);
                share_intent.setType("text/plain");
                share_intent.putExtra(Intent.EXTRA_SUBJECT, "Heroes Name: ");
                share_intent.putExtra(Intent.EXTRA_TEXT, intent.getStringExtra("image"));
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

        /*imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BitmapDrawable drawable = (BitmapDrawable) imageview.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                File directory = Environment.getExternalStorageDirectory();
                File dir = new File(directory.getAbsolutePath() + "/Download/");
                dir.mkdirs();

                // Encode the file as a PNG image.
                FileOutputStream outStream;
                File file = new File(dir, System.currentTimeMillis() + "jpg");
                try {
                    outStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    Toast.makeText(getApplicationContext(), "Image save successfully"
                            , Toast.LENGTH_SHORT).show();
                    outStream.flush();
                    outStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/

    }
}
