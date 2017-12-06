package com.mojomoney.mojomoney;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewSingleEntryActivity extends AppCompatActivity {

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_entry);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "entries")
                .allowMainThreadQueries()
                .build();

        TextView view = findViewById(R.id.id_view);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        if (id == 0) {

        } else {

            Entry tempEntry = db.SingleEntryDao().getSingleEntry(id);



        }
    }
}
