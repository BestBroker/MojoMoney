package com.mojomoney.mojomoney;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

public class MainMenuActivity extends AppCompatActivity implements ThemePickerDialogFragment.NoticeDialogListener {

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        SharedPreferences preferences = getSharedPreferences("theme_toggle", MODE_PRIVATE);
        SplashActivity.theme = preferences.getInt("theme_toggle", 0);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(SplashActivity.theme == 1) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

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
            case R.id.action_search:

                //onSearchRequested();
                return true;

            case R.id.action_settings:

                startActivity(intent);
                return true;

            case R.id.action_settings2:

                startActivity(intent2);
                return true;

            case R.id.action_settings3:

                DialogFragment newFragment = new ThemePickerDialogFragment();
                newFragment.show(getSupportFragmentManager(), "theme");
                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    public void new_entry(View view) {
        startActivity(new Intent(getApplicationContext(), NewEntryActivity.class));
    }
}
