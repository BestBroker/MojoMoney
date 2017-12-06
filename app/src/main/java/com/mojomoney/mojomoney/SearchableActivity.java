package com.mojomoney.mojomoney;

import android.app.SearchManager;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SearchableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);

            }
        }

        public Entry doMySearch (String search_request){
            final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "entries")
                    .allowMainThreadQueries()
                    .build();
        Entry entry = db.SingleEntryDao().getSingleEntrybyName(search_request);
        return entry;
        }

        //TODO: Method for presenting the results is still missing

}
