package com.codehub.marvelheroesapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codehub.marvelheroesapp.DatabaseFiles.Database;
import com.codehub.marvelheroesapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private Database db;
    // EditText e1,e2,e3,e4,e5,e6;
    private TextInputLayout e1, e2, e3, e4, e5, e6;
    Button register_btn, sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new Database(this);
        e1 = findViewById(R.id.name);
        /*e2=findViewById(R.id.surname);*/
        e3 = findViewById(R.id.email);
        e4 = findViewById(R.id.username);
        e5 = findViewById(R.id.password);
        e6 = findViewById(R.id.confirmpass);
        register_btn = findViewById(R.id.register_button);
        sign_in = findViewById(R.id.sign_in);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(LoginActivity.class);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = e1.getEditText().getText().toString().trim();
                /*String s2 = e2.getEditText().getText().toString().trim();*/
                String email = e3.getEditText().getText().toString().trim();
                String username = e4.getEditText().getText().toString().trim();
                String password = e5.getEditText().getText().toString().trim();
                String confirmpass = e6.getEditText().getText().toString().trim();
                if (email.equals("") || password.equals("") || confirmpass.equals("") || name.equals("") || username.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(confirmpass)) {
                        boolean chkuser = db.chkuser(username);
                        boolean chkemail = db.chkemail(email);
                        if (chkuser == true) {
                            if (chkemail == true) /*chkuser == true)*/ {
                                boolean insert = db.insert(null, email, password, username, name);
                                if (insert == true) {
                                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    intent.putExtra("TAKE_FULLNAME", name);
                                    intent.putExtra("TAKE_USER_EMAIL", email);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Email Already exists", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Username Already exists", Toast.LENGTH_SHORT).show();
                        }
                    } else if (password != confirmpass)
                        Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void gotoActivity(Class activityName) {
        Intent intent = new Intent(RegisterActivity.this, activityName);
        startActivity(intent);
    }
}

