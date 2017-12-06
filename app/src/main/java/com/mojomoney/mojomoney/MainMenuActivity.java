package com.mojomoney.mojomoney;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_menu, menu);



            // Get the SearchView and set the searchable configuration
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        try {
            //TODO:Eine Ursache für Crash liegt in dieser Zeile
            SearchView searchView = (SearchView) menu.findItem(R.id.action_search_2).getActionView();


            // Assumes current activity is the searchable activity
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Suche nicht möglich", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    public void reset(View view) {
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("password", "");
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, NewEntryActivity.class);
        Intent intent2 = new Intent(getApplicationContext(), ViewEntriesActivity.class);
        switch (item.getItemId()) {
            case R.id.action_settings:

                startActivity(intent);
                return true;

            case R.id.action_settings2:

                startActivity(intent2);
                return true;

            case R.id.action_settings3:
                return true;

            case R.id.action_settings4:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            case R.id.action_settings5:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void new_entry(View view) {
        Intent intent = new Intent(getApplicationContext(), NewEntryActivity.class);
        startActivity(intent);
    }
}
