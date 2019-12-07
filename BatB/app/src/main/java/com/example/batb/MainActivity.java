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
        builder.setMessage("1. CAMERA 또는 ALBUM 버튼을 클릭하여 사용자의 사진을 선택합니다.\n\n" +
                            "2. 선택한 사진을 바탕으로 닮은 꼴의 연예인을 4명 보여줍니다.\n\n" +
                            "3. 연예인 목록에서 마음에 드는 화장을 선택합니다.\n\n" +
                            "4. 해당 화장을 1번에서 선택한 사진에 적용시켜 줍니다. \n\n");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();

    }
}