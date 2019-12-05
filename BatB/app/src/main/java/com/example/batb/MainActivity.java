package com.example.batb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
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
    Button button;
    Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tedPermission();

        cameraButton = (Button) findViewById(R.id.camera);
        albumButton = (Button) findViewById(R.id.album);
        quitButton = (Button) findViewById(R.id.quit);
        helpButton = findViewById(R.id.info);

        cameraButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(intent,PHOTO_FROM_CAMERA);
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {}
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoURI = FileProvider.getUriForFile(v.getContext(),
                                getPackageName(),
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, PHOTO_FROM_CAMERA);
                    }
                }
            }
        });

        albumButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent,PHOTO_FROM_ALBUM);
            }
        });

        quitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity(0);
                System.runFinalization();
                System.exit(0);
            }
        });

        helpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HelpActivity.class);
                startActivity(intent);
            }
        });

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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            Toast.makeText(this,"취소되었습니다.",Toast.LENGTH_SHORT).show();
            return;
        }
        if(requestCode==PHOTO_FROM_ALBUM){
            Uri uri = data.getData();

            Intent intent = new Intent(this, PhotoCheckActivity.class);
            intent.putExtra("uri",uri);
            startActivity(intent);
        }
        else if (requestCode==PHOTO_FROM_CAMERA){
            Intent intent = new Intent(this, PhotoCheckActivity.class);
            intent.putExtra("uri",photoURI);
            startActivity(intent);

        }
    }

}

