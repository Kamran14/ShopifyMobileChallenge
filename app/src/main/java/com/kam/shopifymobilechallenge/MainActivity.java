package com.kam.shopifymobilechallenge;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.*;


public class MainActivity extends AppCompatActivity {
    private final String TAG = "API Call";
    final String shopifyUrl = "https://shopicruit.myshopify.com/admin/custom_collections.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";
    OkHttpClient client = null;
    private RecyclerView recyclerView;
    private ShopifyData shopifyData;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Make call to AsyncTask
        client = new OkHttpClient();
        new AsyncFetch().execute();
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {



        @Override
        protected String doInBackground(String... params) {
            JsonReader jsonReader;
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                Request request = new Request.Builder()
                        .url(shopifyUrl)
                        .build();
                try(Response response = client.newCall(request).execute()){

                    if(!response.isSuccessful())
                        throw new IOException("Unexpected code: " + response);
                    return response.body().string();
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }

        }

        @Override
        protected void onPostExecute(String result) {

            List<myData> data = new ArrayList<>();

            try {
                JSONObject jObj = new JSONObject(result);
                JSONArray jsonArray = jObj.getJSONArray("custom_collections");
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject json_data = jsonArray.getJSONObject(i);
                    myData shopData = new myData();
                    shopData.srcImage = json_data.getString("image").replaceAll("\\\\", "/");
                    shopData.myTitle = json_data.getString("title");
                    shopData.amountLeft =  json_data.getInt("id");
                    shopData.myDesc = json_data.getString("body_html");
                    data.add(shopData);
                }

                // Setup and Handover data to recyclerview
                recyclerView = (RecyclerView)findViewById(R.id.mRecycleView);
                shopifyData = new ShopifyData(MainActivity.this, data);
                recyclerView.setAdapter(shopifyData);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            } catch (JSONException e) {
                Log.e("KAM", e.toString());
//                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();

            }

        }

    }
}


/*protected void onPostExecute(String url) throws IOException {
        List<myData> data = new ArrayList<>();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);
            JSONArray Jarray = Jobject.getJSONArray("custom_collections");

            for (int i = 0; i < Jarray.length(); i++) {
                JSONObject object = Jarray.getJSONObject(i);
                Log.d("GOTCALL", object.toString());
            }

        }catch(Exception e) {

            e.printStackTrace();
        }
        shopifyData = (RecyclerView)findViewById(R.id.mRecycleView);
        LinearLayoutManager mLayout = new LinearLayoutManager(this);
        mData = new ShopifyData(this, data);
        shopifyData.setAdapter(mData);
        shopifyData.setLayoutManager(new LinearLayoutManager(MainActivity.this));


    }*/