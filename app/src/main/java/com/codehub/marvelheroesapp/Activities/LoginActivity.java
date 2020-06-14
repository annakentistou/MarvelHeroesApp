package com.codehub.marvelheroesapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codehub.marvelheroesapp.DatabaseFiles.NewDbUsers;
import com.codehub.marvelheroesapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        Button register_now = findViewById(R.id.register_now);
        register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(RegisterActivity.class);
            }
        });

        final TextInputLayout usrname,pass;

        Button login_btn;
        final NewDbUsers db;
        db = new NewDbUsers(this);
        usrname = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_button);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usrname.getEditText().getText().toString().trim();
                String password = pass.getEditText().getText().toString().trim();
                boolean check_mail = db.login(username,password);
                if (check_mail == true){
                    Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("TAKE_USERNAME",username);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Wrong Username or Password",Toast.LENGTH_SHORT).show();
                }
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

//just take KeyHash for Facebook Developers (put this on manifest provider)
    /*private void printKeyHash() {
        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.codehub.marvelheroesapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }*/