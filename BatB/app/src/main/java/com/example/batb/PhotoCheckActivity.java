package com.example.batb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class PhotoCheckActivity extends AppCompatActivity {
    private Uri uri;
    private ImageView imageView;
    private File tempFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_check);

        imageView = (ImageView)findViewById(R.id.imageView);
        Uri uri = getIntent().getParcelableExtra("uri");
        imageView.setImageURI(uri);

    }
}
