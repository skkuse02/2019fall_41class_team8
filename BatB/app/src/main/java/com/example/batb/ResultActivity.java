package com.example.batb;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {
    private Button changeButton, saveButton;
    private boolean isBefore=false;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final ImageView imageView = findViewById(R.id.resultImage);
        uri = getIntent().getParcelableExtra("uri");

        changeButton = findViewById(R.id.changeButton);
        saveButton = findViewById(R.id.savebutton);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBefore) {
                    imageView.setImageResource(R.mipmap.ic_launcher_round);
                    isBefore = !isBefore;
                }
                else {
                    imageView.setImageURI(uri);
                    isBefore = !isBefore;
                }
            }
        });
    }

    private File createImageFile() throws IOException {

        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "BatB_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름 ( blackJin )
        File storageDir = new File(Environment.DIRECTORY_DCIM + "/BatB/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 빈 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }
}
