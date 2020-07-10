package com.codehub.marvelheroesapp.Activities;

import android.content.Intent;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    GoogleSignInClient googleSignInClient;
    SignInButton google_btn;
    private static final int RC_SIGN_IN = 100;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    AppCompatImageButton fingerPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("799808644087-nbq5nju84r2f7i83lm6rgbkmpvgmbdvb.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, signInOptions);

        fingerprint();
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

        final TextInputLayout usrname, pass;

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
                boolean check_mail = db.login(username, password);
                if (check_mail == true) {
                    Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("TAKE_USERNAME", username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Username or Password", Toast.LENGTH_SHORT).show();
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

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            gotoActivity(MainActivity.class);
        } else {
            Toast.makeText(LoginActivity.this, "Something goes wrong", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            gotoActivity(MainActivity.class);
            finish();
        }
    }

    private void fingerprint(){
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.e("MY_APP_TAG", "No biometric features available on this device.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Log.e("MY_APP_TAG", "The user hasn't associated " +
                        "any biometric credentials with their account.");
                break;
        }

        executor = ContextCompat.getMainExecutor(this);

            biometricPrompt = new BiometricPrompt(LoginActivity.this,
                    executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode,
                                                  @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    Toast.makeText(getApplicationContext(),
                            "Authentication message: " + errString, Toast.LENGTH_SHORT)
                            .show();
                }

                @Override
                public void onAuthenticationSucceeded(
                        @NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Toast.makeText(getApplicationContext(),
                            "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                    gotoActivity(MainActivity.class);
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Toast.makeText(getApplicationContext(), "Authentication failed",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            });


        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setSubtitle("Log in using your fingerprint")
                .setNegativeButtonText("Cancel")
                .build();

        fingerPrint = findViewById(R.id.fingerprt);
        fingerPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
