package com.codehub.marvelheroesapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codehub.marvelheroesapp.DatabaseFiles.NewDbUsers;
import com.codehub.marvelheroesapp.R;
import com.google.android.material.textfield.TextInputLayout;

import static com.codehub.marvelheroesapp.CreateNotificationChannel.CHANNEL_ID;

public class RegisterActivity extends AppCompatActivity {

    private NewDbUsers db;
    private TextInputLayout full_name, mail, usrname, pass, cfrmpass;
    Button register_btn, sign_in;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        db = new NewDbUsers(this);
        full_name = findViewById(R.id.name);
        mail = findViewById(R.id.email);
        usrname = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        cfrmpass = findViewById(R.id.confirmpass);
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
                String name = full_name.getEditText().getText().toString().trim();
                String email = mail.getEditText().getText().toString().trim();
                String username = usrname.getEditText().getText().toString().trim();
                String password = pass.getEditText().getText().toString().trim();
                String confirmpass = cfrmpass.getEditText().getText().toString().trim();
                if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmpass.isEmpty()) {
                    full_name.setError("Field can't be empty");
                    mail.setError("Field can't be empty");
                    usrname.setError("Field can't be empty");
                    pass.setError("Field can't be empty");
                    cfrmpass.setError("Field can't be empty");

                } else {
                    full_name.setError(null);
                    mail.setError(null);
                    usrname.setError(null);
                    pass.setError(null);
                    cfrmpass.setError(null);

                    if (password.equals(confirmpass)) {
                        boolean check_user = db.check_user(username);
                        boolean check_email = db.check_email(email);
                        if (check_user == true) {
                            if (check_email == true) {
                                boolean insert = db.insert(0, name, username, password, email, null);
                                if (insert == true) {
                                    /*Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();*/
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    intent.putExtra("TAKE_FULLNAME", name);
                                    intent.putExtra("TAKE_USER_EMAIL", email);
                                    startActivity(intent);

                                    String title = "Account created";
                                    String message = "Thank you for registering!";
                                    Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                            .setSmallIcon(R.drawable.ic_check_circle_black_24dp)
                                            .setContentTitle(title)
                                            .setContentText(message)
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                            .setAutoCancel(true)
                                            .build();
                                    notificationManager.notify(1, notification);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Email Already exists", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Username Already exists", Toast.LENGTH_SHORT).show();
                        }
                    } else if (!password.equals(confirmpass))
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

