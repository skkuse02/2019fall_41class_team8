package com.example.batb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ResultActivity extends AppCompatActivity {
    private Button changeButton, saveButton;
    private ImageView imageView;

    private Bitmap madeupImage;
    private Uri beforePhotoUri;

    private boolean isBefore = false;
    private boolean isDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        beforePhotoUri = getIntent().getParcelableExtra("uri");

        imageView = findViewById(R.id.resultImage);
        changeButton = findViewById(R.id.changeButton);
        saveButton = findViewById(R.id.savebutton);

        Glide.with(this).load(R.raw.loading).into(imageView);

        String ipv4Address = "192.168.0.30";
        String portNumber = "8000";
        String pathToFile = "http://" + ipv4Address + ":" + portNumber + "/download";

        DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask();
        downloadTask.execute(pathToFile);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDone) {
                    if (isBefore) {
                        imageView.setImageBitmap(madeupImage);
                        isBefore = !isBefore;
                    } else {
                        imageView.setImageURI(beforePhotoUri);
                        isBefore = !isBefore;
                    }
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createImageFile();
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            String pathToFile = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(pathToFile).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", Objects.requireNonNull(e.getMessage()));
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap result) {
            madeupImage = result;
            imageView.setImageBitmap(madeupImage);
            isDone = true;
        }
    }

    private void createImageFile() throws IOException {

        // 이미지 파일 이름
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "BatB_" + timeStamp + ".jpg";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/BatB/";

        // 이미지가 저장될 폴더 이름
        File storageDir = new File(path);
        if (!storageDir.exists()) storageDir.mkdirs();


        File file = new File(path, imageFileName);
        OutputStream fOut = new FileOutputStream(file);
        madeupImage.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        fOut.close();

        MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());

        Toast toast = Toast.makeText(getApplicationContext(), path + " 에 저장 되었습니다.", Toast.LENGTH_SHORT);
        toast.show();

        // 빈 파일 생성
        //File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        //return image;
    }
}
