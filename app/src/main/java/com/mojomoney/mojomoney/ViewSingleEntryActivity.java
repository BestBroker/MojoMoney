package com.mojomoney.mojomoney;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.net.URI;
import java.net.URL;

public class ViewSingleEntryActivity extends AppCompatActivity {

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_entry);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "entries")
                .allowMainThreadQueries()
                .build();

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        if (id == 0) {

        } else {

            Entry tempEntry = db.SingleEntryDao().getSingleEntry(id);

            TextView name = findViewById(R.id.Single_name);
            TextView betrag = findViewById(R.id.Single_betrag_text);
            TextView datum = findViewById(R.id.Single_datum_text);
            ImageView image = findViewById(R.id.Single_image);

            float betrag_float = tempEntry.getBetrag();

            String betrag_text = Float.toString(betrag_float);
            String datum_text = tempEntry.getDatum();

            name.setText(tempEntry.getName());
            betrag.setText(betrag_text + " €");
            datum.setText(datum_text);

            int px = image.getHeight();
            String path = tempEntry.getPath();

            File imageFile = new File(path);

            Glide.with(this).load(imageFile).into(image);

            //image.setImageBitmap(ImageHandler.loadImageFromStorage(path, px));

        }
    }
}
