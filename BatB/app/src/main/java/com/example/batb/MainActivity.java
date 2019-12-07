package com.example.batb;


import android.Manifest;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.batb.utils.ImageResizeUtils;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import androidx.core.content.FileProvider;

public class MainActivity extends AppCompatActivity {
    private boolean isCamera=false;
    private File tempFile;
    private int PHOTO_FROM_CAMERA=0, PHOTO_FROM_ALBUM=1;
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
                Intent intent = new Intent(v.getContext(), ImageList.class);
                startActivity(intent);
            }
        });
    }

    private void openCamera() {
        cameraButton = (Button) findViewById(R.id.camera);
        cameraButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(takePictureIntent, PHOTO_FROM_CAMERA);
                isCamera=true;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                try {
                    tempFile = createImageFile();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    finish();
                    e.printStackTrace();
                }
                if (tempFile != null) {

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                        Uri photoUri = FileProvider.getUriForFile(getApplicationContext(),
                                "com.example.batb.provider", tempFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(intent, PHOTO_FROM_CAMERA);

                    } else {

                        Uri photoUri = Uri.fromFile(tempFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(intent, PHOTO_FROM_CAMERA);

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
                isCamera=false;
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
            //Bitmap photo = (Bitmap) data.getExtras().get("data");
            ImageResizeUtils.resizeFile(tempFile,tempFile,1280,isCamera);
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap photo = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), photo);

            Intent intent = new Intent(this, PhotoCheckActivity.class);
            intent.putExtra("uri",tempUri);
            startActivity(intent);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {

        Bitmap OutImage = Bitmap.createScaledBitmap(inImage, inImage.getWidth(), inImage.getHeight(),true);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), OutImage, "Title", null);
        return Uri.parse(path);
    }

    private File createImageFile() throws IOException {

        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "BatB_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름 ( blackJin )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/BatB/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 빈 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }



}