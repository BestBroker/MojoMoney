package com.mojomoney.mojomoney;

import android.app.ListActivity;
import android.app.SearchManager;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

public class SearchableActivity extends AppCompatActivity{

    List<Entry> entries;
    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(SplashActivity.theme == 1) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);

            doMySearch(query);
        }
    }

    public void doMySearch (String search_request){
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "entries")
                .allowMainThreadQueries()
                .build();


        RecyclerView rvEntries = findViewById(R.id.search_recycler_view);
        entries = db.SingleEntryDao().getSingleEntrybyName(search_request);
        if(entries.size()==0){
            Toast.makeText(SearchableActivity.this, "Eintrag nicht gefunden", Toast.LENGTH_SHORT).show();
        }
        adapter = new SearchAdapter(this, entries);
        rvEntries.setAdapter(adapter);
        rvEntries.setLayoutManager(new LinearLayoutManager(this));

    }

    public void deleteSearchQueries() {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
        suggestions.clearHistory();
    }
}
