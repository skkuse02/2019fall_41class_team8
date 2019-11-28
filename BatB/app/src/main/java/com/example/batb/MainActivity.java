package com.example.batb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.provider.MediaStore;
import android.view.View;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button cameraButton, albumButton, quitButton, helpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openCamera();
        openablum();
        gobacktodesk();
    }

    private void openCamera() {
        cameraButton = (Button) findViewById(R.id.camera);
        cameraButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }
        });
    }

    private void openablum(){
        albumButton = (Button) findViewById(R.id.album);
        albumButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void gobacktodesk(){
        quitButton = (Button) findViewById(R.id.quit);
        quitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

