package com.example.batb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class PhotoCheckActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button yesButton;
    private Button noButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_check);

        imageView = (ImageView)findViewById(R.id.imageView);
        Uri uri = getIntent().getParcelableExtra("uri");
        imageView.setImageURI(uri);

        yesButton = (Button)findViewById(R.id.yesButton);
        noButton = (Button)findViewById(R.id.nobutton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RequestActivity.class);
                startActivity(intent);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}