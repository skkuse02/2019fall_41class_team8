package com.example.batb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class PhotoCheckActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button yesButton;
    private Button noButton;

    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_check);

        photoUri = getIntent().getParcelableExtra("uri");

        imageView = (ImageView)findViewById(R.id.imageView);

        imageView.setImageURI(photoUri);

        yesButton = (Button)findViewById(R.id.yesButton);
        noButton = (Button)findViewById(R.id.nobutton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RequestActivity.class);
                intent.putExtra("uri", photoUri);
                startActivity(intent);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button resultButton = findViewById(R.id.resultbutton);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                intent.putExtra("uri",photoUri);
                startActivity(intent);
            }
        });
    }
}