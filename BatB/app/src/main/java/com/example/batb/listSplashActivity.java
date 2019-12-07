package com.example.batb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.annotation.Nullable;

public class listSplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_list);

        ImageView imageView = findViewById(R.id.loadingImage);
        Glide.with(this).load(R.raw.loading).into(imageView);
        
        startLoading();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), ImageList.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
