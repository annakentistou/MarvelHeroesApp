package com.codehub.marvelheroesapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codehub.marvelheroesapp.DatabaseFiles.NewDbUsers;
import com.codehub.marvelheroesapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

  GoogleSignInClient googleSignInClient;
  SignInButton google_btn;
  private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("799808644087-nbq5nju84r2f7i83lm6rgbkmpvgmbdvb.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, signInOptions);
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
        google_btn = findViewById(R.id.gmail);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usrname.getEditText().getText().toString().trim();
                String password = pass.getEditText().getText().toString().trim();
                boolean check_mail = db.login(username,password);
                boolean read_name = db.read_name(username);
                if (check_mail == true){
                    Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_SHORT).show();
                    gotoActivity(MainActivity.class);
                    finish();
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

        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    private void gotoActivity(Class activityName) {
        Intent intent = new Intent(LoginActivity.this, activityName);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

    private void updateUI(@Nullable GoogleSignInAccount account){
        if(account != null){
            gotoActivity(MainActivity.class);
        }else {
            Toast.makeText(LoginActivity.this, "Something goes wrong", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {
           gotoActivity(MainActivity.class);
           finish();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
