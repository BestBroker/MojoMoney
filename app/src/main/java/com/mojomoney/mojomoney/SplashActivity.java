package com.mojomoney.mojomoney;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        password = settings.getString("password", "");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), SetPasswordActivity.class);
                Intent intent2 = new Intent(getApplicationContext(), EnterPasswordActivity.class);
                if (password.equals("")) {
                    //if there is no password

                    startActivity(intent);
                    finish();
                } else {
                    //if there is a password

                    startActivity(intent2);
                    finish();
                }
            }
        }, 100);
    }
}
