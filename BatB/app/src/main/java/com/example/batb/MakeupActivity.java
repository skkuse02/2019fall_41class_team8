package com.example.batb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MakeupActivity extends AppCompatActivity {
    private String celebName;
    private String celebPosition;
    private Uri photoUri;

    private ImageView imageView;
    private Context context;

    private boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeup);

        context = getApplicationContext();

        celebName = getIntent().getStringExtra("celebName");
        celebPosition = getIntent().getStringExtra("celebPosition");
        photoUri = getIntent().getParcelableExtra("uri");

        imageView = findViewById(R.id.loadingImage2);
        Glide.with(this).load(R.raw.loading).into(imageView);

        connectServer();
    }

    void connectServer(){
        String ipv4Address = "192.168.0.30";
        String portNumber = "8000";
        String getUrl = "http://" + ipv4Address + ":" + portNumber + "/makeup/" + celebName + "&" + celebPosition;

        postRequest(getUrl);
    }

    void postRequest(String getUrl) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(getUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Cancel the post on failure.
                call.cancel();

                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView responseText = findViewById(R.id.loadingTextview2);
                        responseText.setText("Failed to Connect to Server");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            response.body().string();
                            success = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                while (!success);
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("uri", photoUri);
                startActivity(intent);
                finish();
            }
        });
    }
}
