package com.codehub.marvelheroesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.codehub.marvelheroesapp.MainActivity;
import com.codehub.marvelheroesapp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button register_now = findViewById(R.id.register_now);
        register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(RegisterActivity.class);
            }
        });

        Button login = findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(MainActivity.class);
            }
        });

        Button forgot_pass = findViewById(R.id.forgot_pass);
        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(ConfirmPassActivity.class);
            }
        });
    }

    private void gotoActivity(Class activityName) {
        Intent intent = new Intent(LoginActivity.this, activityName);
        startActivity(intent);
    }
}
