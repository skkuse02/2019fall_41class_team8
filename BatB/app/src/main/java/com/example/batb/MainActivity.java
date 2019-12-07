package com.example.batb;


import android.Manifest;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import androidx.core.content.FileProvider;

public class MainActivity extends AppCompatActivity {
    private int PHOTO_FROM_CAMERA=0, PHOTO_FROM_ALBUM=1;
    Uri photoURI;
    private Button cameraButton, albumButton, quitButton, helpButton, listtmpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tedPermission();

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
                        photoURI = FileProvider.getUriForFile(v.getContext(), getPackageName(), photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, PHOTO_FROM_CAMERA);
                    }
                }
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
        String imageFileName = "BatB_" + timeStamp + "_";
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