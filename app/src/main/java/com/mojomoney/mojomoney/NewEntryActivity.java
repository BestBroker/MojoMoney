package com.mojomoney.mojomoney;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewEntryActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final String NO_IMAGE_TAKEN = "no_image";

    String mtimeStamp;
    ImageButton myButton;
    File photoFile = null;
    Bitmap myMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_entry);

        final String timeStamp = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss_").format(new Date());
        mtimeStamp = timeStamp;

        myButton = findViewById(R.id.add_photo_button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dispatchTakePictureIntent(mtimeStamp);
            }
        });
    }

    private void dispatchTakePictureIntent(String timeStamp) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile;
            photoFile = ImageHandler.getPicFile(getApplicationContext(), timeStamp);
            this.photoFile = photoFile;
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.mojomoney.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            myMap = ImageHandler.setPic(photoFile);
            myButton.setClickable(false);
            myButton.setImageBitmap(myMap);
            slideAnimator();
        }
    }

    public void newEntry(View view) {

        EditText eingabe_name = findViewById(R.id.eingabe_name);
        EditText eingabe_betrag = findViewById(R.id.eingabe_betrag);

        String name =  eingabe_name.getText().toString();
        String betrag = eingabe_betrag.getText().toString();
        String datum = new SimpleDateFormat("dd.MM.yyyy '('HH:mm 'Uhr)'").format(new Date());

        String path;

        if (photoFile != null) {
            path = ImageHandler.saveToInternalStorage(this, photoFile, mtimeStamp);
        } else {
            path = NO_IMAGE_TAKEN;
        }

        Entry entry = new Entry(name, betrag, datum, mtimeStamp, path);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "entries")
                .allowMainThreadQueries()
                .build();

        db.EntryDao().insertAll(entry);



        // make Toast
        CharSequence text = "Eintrag erstellt!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();

        finish();
    }

    public void cancelEntry(View view) {
        finish();
    }

    private void slideAnimator() {

        RelativeLayout mRootView = findViewById(R.id.root_layout);
        AutoTransition mTransition = new AutoTransition();

        TextView alert = findViewById(R.id.view_camera);

        RelativeLayout.LayoutParams myParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        myParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        myParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

        TransitionManager.beginDelayedTransition(mRootView, mTransition);

        mRootView.removeView(alert);
        myButton.setLayoutParams(myParams);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (photoFile != null){
            try {
                photoFile.delete();
            } catch (Exception e) {
                e.printStackTrace();

                CharSequence text = "Fehler (deleteTempFile)";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
            }
        }
    }

}
