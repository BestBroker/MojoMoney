package com.mojomoney.mojomoney;

import android.arch.persistence.room.Room;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class ViewEntriesActivity extends AppCompatActivity {

    List<Entry> entries;
    EntryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(SplashActivity.theme == 1) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entries);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        RecyclerView rvEntries = findViewById(R.id.rvEntries);

        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "entries")
                .allowMainThreadQueries()
                .build();
        entries = db.EntryDao().getAllEntries();

        adapter = new EntryAdapter(this, entries);
        rvEntries.setAdapter(adapter);
        rvEntries.setLayoutManager(new LinearLayoutManager(this));

        List<EntryAmount> amountList = db.SingleEntryDao().loadAmount();

        TextView sum_view = findViewById(R.id.gesamt);
        String sum = addSum(amountList);
        sum_view.setText(sum + " €");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_entries_menu, menu);

       return true;
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
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
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

    @Override
    public void onRestart() {
        super.onRestart();
        recreate();
    }

    public void deleteAllFiles(View view) {

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "entries")
                .allowMainThreadQueries()
                .build();

        List<Entry> entries = db.EntryDao().getAllEntries();
        db.EntryDao().deleteAll(entries);

        for(int i = 0; i < entries.size(); i++) {
            entries.get(i).deleteImage();
        }

        recreate();
    }

    String addSum(List<EntryAmount> amountList) {
        float sum = 0;

        for (int i = 0; i < amountList.size(); i++) {
            sum = sum + amountList.get(i).betrag;
        }

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        return df.format(sum);
    }
}
