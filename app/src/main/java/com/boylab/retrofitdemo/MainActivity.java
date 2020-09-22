package com.boylab.retrofitdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.boylab.retrofitdemo.retrofit.MyGsonConverterFactory;
import com.boylab.retrofitdemo.retrofit.RetrofitApi;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    /**
     * retrofit-converters
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String API_URL = "url";
        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(API_URL)
                        .addConverterFactory(MyGsonConverterFactory.create())
                        .build();

        // Create an instance of our Retrofit API interface.
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);


    }
}