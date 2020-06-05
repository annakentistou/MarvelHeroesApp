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

    Database db;
    // EditText e1,e2,e3,e4,e5,e6;
    private TextInputLayout e1,e2,e3,e4,e5,e6;
    Button register_btn,sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new Database(this);
        e1=findViewById(R.id.name);
        e2=findViewById(R.id.surname);
        e3=findViewById(R.id.email);
        e4=findViewById(R.id.username);
        e5=findViewById(R.id.password);
        e6=findViewById(R.id.confirmpass);
        register_btn=findViewById(R.id.register_button);
        sign_in=findViewById(R.id.sign_in);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             gotoActivity(LoginActivity.class);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = e1.getEditText().getText().toString().trim();
                String s2 = e2.getEditText().getText().toString().trim();
                String s3 = e3.getEditText().getText().toString().trim();
                String s4 = e4.getEditText().getText().toString().trim();
                String s5 = e5.getEditText().getText().toString().trim();
                String s6 = e6.getEditText().getText().toString().trim();
                if(s3.equals("") || s5.equals("") || s6.equals("") || s1.equals("") || s2.equals("") || s4.equals(""))  {
                    Toast.makeText(getApplicationContext(),"Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(s5.equals(s6)){
                        Boolean chkuser = db.chkuser(s4);
                        Boolean chkemail = db.chkemail(s3);
                        if (chkuser == true){
                            if(chkemail==true) /*chkuser == true)*/{
                                Boolean insert = db.insert(s3,s5,s4);
                                if(insert==true){
                                    Toast.makeText(getApplicationContext(), "Registered Successfully",Toast.LENGTH_SHORT).show();
                                    gotoActivity(LoginActivity.class);
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Email Already exists",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Username Already exists",Toast.LENGTH_SHORT).show();
                        }
                    }

                    else if (s5!=s6)
                        Toast.makeText(getApplicationContext(),"Password do not match",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void gotoActivity(Class activityName) {
        Intent intent = new Intent(RegisterActivity.this, activityName);
        startActivity(intent);
    }
}

