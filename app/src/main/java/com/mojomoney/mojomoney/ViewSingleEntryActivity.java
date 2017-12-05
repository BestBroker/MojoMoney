package com.mojomoney.mojomoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewSingleEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_entry);

        TextView view = findViewById(R.id.id_view);

        Intent intent = getIntent();
        String name = intent.getStringExtra("id");

        view.setText(name);

    }
}
