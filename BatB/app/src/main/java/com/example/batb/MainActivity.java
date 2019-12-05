package com.example.batb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private int PHOTO_FROM_CAMERA=0, PHOTO_FROM_ALBUM=1;
    private Button cameraButton, albumButton, quitButton, helpButton;
    ImageView imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tedPermission();

        openCamera();
        openAblum();
        quit();
        help();

        imageView = (ImageView)findViewById(R.id.logo);
        button = findViewById(R.id.button2);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ResultActivity.class));
            }
        });
    }

    private void tedPermission(){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("사진 및 파일을 저장하기 위하여 접근 권한이 필요합니다.")
                .setDeniedMessage("설정 > 권한 에서 권한을 허용할 수 있습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    private void openCamera() {
        cameraButton = (Button) findViewById(R.id.camera);
        cameraButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,PHOTO_FROM_CAMERA);
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
                startActivityForResult(intent,PHOTO_FROM_ALBUM);
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
                Intent intent = new Intent(view.getContext(), HelpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PHOTO_FROM_ALBUM){
            Uri uri = data.getData();
            Cursor cursor = null;
            String filepath;

            try{
                String[] proj={MediaStore.Images.Media.DATA};
                cursor = getContentResolver().query(uri,proj,null,null,null);
                int col_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                filepath = cursor.getString(col_index);
            }finally {
                if(cursor!= null) cursor.close();
            }

            Intent intent = new Intent(this, PhotoCheckActivity.class);
            intent.putExtra("code",PHOTO_FROM_ALBUM);
            intent.putExtra("file",filepath);
            startActivity(intent);
        }
        else if (requestCode==PHOTO_FROM_CAMERA){
            Bundle bundle = data.getExtras();
            Intent intent = new Intent(this, PhotoCheckActivity.class);
            intent.putExtra("code",PHOTO_FROM_CAMERA);
            intent.putExtra("bundle",bundle);
            startActivity(intent);

        }
    }

}

