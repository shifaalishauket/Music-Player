package com.example.musiclisting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;

import android.provider.MediaStore;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
        ArrayList<AudioModel> audioList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioList=new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_MEDIA_AUDIO
        )
                != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.READ_MEDIA_AUDIO},
                    1
            );
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    getAudioFiles();
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the feature requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    Toast.makeText(this, "Please grant the permission", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(
                            this,
                            new String[]{android.Manifest.permission.READ_MEDIA_AUDIO},
                            1
                    );
                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }





    private  void getAudioFiles(){
        StringBuilder selection = new StringBuilder("is_music != 0 AND title != ''");

// Display audios in alphabetical order based on their display name.
        String sortOrder = MediaStore.Audio.Media.DISPLAY_NAME + " ASC";
        Cursor cursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                selection.toString(),
                null,
                sortOrder
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                int size = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                AudioModel file=new AudioModel(id,title,duration,size,artist);
                audioList.add(file);
                Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
                // Your logic for handling the retrieved data goes here

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        AudioFilesAdapter filesAdapter=new AudioFilesAdapter(audioList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(filesAdapter);
    }
}