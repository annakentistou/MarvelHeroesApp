package com.codehub.marvelheroesapp.Activities;

import android.content.Context;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.codehub.marvelheroesapp.R;

public class ProgressDialog extends AppCompatActivity {

    public void progressDialog(Context activityName){
        final android.app.ProgressDialog progressDialog = new android.app.ProgressDialog(activityName);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 3000);
    }
}
