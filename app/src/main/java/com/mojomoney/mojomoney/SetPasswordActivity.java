package com.mojomoney.mojomoney;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void setPassword (View view) {

        EditText feld1 = (EditText) findViewById(R.id.feld1);
        EditText feld2 = (EditText) findViewById(R.id.feld2);
        String pw1 = feld1.getText().toString();
        String pw2 = feld2.getText().toString();
        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
        if(pw1.equals("") || pw2.equals("")) {
            TextView error = (TextView) findViewById(R.id.text1);
            TextView leer = (TextView) findViewById(R.id.text2);


            error.setText("Kein Passwort oder keine Übereinstimmung.");
            leer.setText("");
        } else {
            if (pw1.equals(pw2)) {

                SharedPreferences settings = getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("password", pw1);
                editor.apply();

                startActivity(intent);
                finish();
            } else {
                TextView error = (TextView) findViewById(R.id.text1);
                TextView leer = (TextView) findViewById(R.id.text2);


                error.setText("Kein Passwort oder keine Übereinstimmung. Erneut versuchen.");
                leer.setText("");
            }
        }
    }
}
