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
        int code = getIntent().getIntExtra("code",-1);

        if(code == 1){ // album
            String filepath = getIntent().getStringExtra("file");
            tempFile = new File(filepath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
            imageView.setImageBitmap(bitmap);
        }else if (code == 0){ // camera
            Bundle bundle = getIntent().getBundleExtra("bundle");
            Bitmap bitmap = (Bitmap)bundle.get("data");
            imageView.setImageBitmap(bitmap);
        }

    }
}
