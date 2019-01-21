package com.kam.shopifymobilechallenge;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private final String TAG = "API Call";
    private final String url = "https://shopicruit.myshopify.com/admin/custom_collections.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";
    OkHttpClient client = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new OkHttpClient();
        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    showData();
                }catch(IOException e){
                    Log.e(TAG, e.toString());
                    e.printStackTrace();
                }
            }
        });
        myThread.start();
    }



    public void showData() throws IOException{
        Log.d(TAG,run(url));
    }

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
