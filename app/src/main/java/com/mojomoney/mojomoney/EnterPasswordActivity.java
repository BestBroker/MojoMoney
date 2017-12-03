package com.mojomoney.mojomoney;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EnterPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);
    }
    public void loginAttempt (View view) {
        String passwort;

        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        passwort = settings.getString("password", "");

        Intent intent = new Intent(this, MainMenuActivity.class);
        EditText editText = (EditText) findViewById(R.id.loginTextView);
        String message = editText.getText().toString();

        if (message.equals(passwort)) {
            startActivity(intent);
            finish();
        } else {
            TextView error = (TextView) findViewById(R.id.loginTextView);
            TextView errorMessage = (TextView) findViewById(R.id.textView);

            error.setText("");
            errorMessage.setText("Falsches Passwort! Erneut versuchen.");
        }
    }
}
