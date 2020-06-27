package com.codehub.marvelheroesapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codehub.marvelheroesapp.DatabaseFiles.NewDbUsers;
import com.codehub.marvelheroesapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class ConfirmPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pass);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        final NewDbUsers db;
        db = new NewDbUsers(this);

        Button submit = findViewById(R.id.submit_button);
        Button backToLogin = findViewById(R.id.backToLogin);
        final TextInputLayout usr_email, usr_pass, confirm_pass;
        usr_email = findViewById(R.id.email);
        usr_pass = findViewById(R.id.new_password);
        confirm_pass = findViewById(R.id.confirm_pass);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = usr_email.getEditText().getText().toString().trim();
                String password = usr_pass.getEditText().getText().toString().trim();
                String confirm = confirm_pass.getEditText().getText().toString().trim();
                boolean chkemail = db.check_email(email);

                if (email.equals("") || password.equals("") || confirm.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();

                } else if (chkemail == false) {
                    if (password.equals(confirm)) {
                        db.updateEntry(email, password);
                        Toast.makeText(getApplicationContext(), "Your Password change Successfully", Toast.LENGTH_SHORT).show();
                        gotoActivity(LoginActivity.class);
                    } else {
                        Toast.makeText(getApplicationContext(), "Password mismatch", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Check your Email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(LoginActivity.class);
            }
        });
    }

    private void gotoActivity(Class activityName) {
        Intent intent = new Intent(ConfirmPassActivity.this, activityName);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
