package com.example.batb;


import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button cameraButton, albumButton, quitButton, helpButton, listtmpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openCamera();
        openAblum();
        quit();
        help();
        listtmp();
    }

    private void listtmp() {
        listtmpButton = (Button) findViewById(R.id.listtmp);
        listtmpButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), listSplashActivity.class);
                //Intent intent = new Intent(v.getContext(), ImageList.class);
                startActivity(intent);
            }
        });
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

    private void openAblum(){
        albumButton = (Button) findViewById(R.id.album);
        albumButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivity(intent);
            }
        });
    }

    private void quit(){
        quitButton = (Button) findViewById(R.id.quit);
        quitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity(0);
                System.runFinalization();
                System.exit(0);
            }
        });
    }

    private void help(){
        helpButton = findViewById(R.id.info);
        helpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("HELP");
        builder.setMessage("Give us your picture!");
        builder.setPositiveButton("I know",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();

    }
}