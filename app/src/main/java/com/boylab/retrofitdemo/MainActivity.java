package com.boylab.retrofitdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.boylab.retrofitdemo.retrofit.JsonConverterFactory;
import com.boylab.retrofitdemo.retrofit.RetrofitApi;
import com.boylab.retrofitdemo.retrofit.bean.RequestModel;
import com.boylab.retrofitdemo.retrofit.bean.ResponseModel;
import com.boylab.retrofitdemo.retrofit.model.LoginUserRequest;
import com.boylab.retrofitdemo.retrofit.model.LoginUserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    /**
     * retrofit-converters
     * @param savedInstanceState
     */

    public static final String URL_CLOUD = "http://www.weighingcloud.com:8080/iot_server/clientapi/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String API_URL = URL_CLOUD;
        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(API_URL)
                        .addConverterFactory(JsonConverterFactory.create())
                        .build();

        // Create an instance of our Retrofit API interface.
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);


        LoginUserRequest loginUserRequest = new LoginUserRequest("test", "Yaohua807");
        RequestModel<LoginUserRequest> loginUser = new RequestModel<>(loginUserRequest);

        /*retrofitApi.loginUser(loginUser).enqueue(new Callback<ResponseModel<LoginUserResponse>>() {
            @Override
            public void onResponse(Call<ResponseModel<LoginUserResponse>> call, Response<ResponseModel<LoginUserResponse>> response) {
                Log.i("___boylab>>>___", "onResponse:  code = "+response.code());
                Log.i("___boylab>>>___", "onResponse: "+response.body().toString());

            }

            @Override
            public void onFailure(Call<ResponseModel<LoginUserResponse>> call, Throwable t) {

            }
        });*/

        retrofitApi.loginUser1(loginUser.getSeed(), loginUser.getSalt(), loginUser.getUuid(), loginUser.getData(), loginUser.getChk()).enqueue(new Callback<ResponseModel<LoginUserResponse>>() {
            @Override
            public void onResponse(Call<ResponseModel<LoginUserResponse>> call, Response<ResponseModel<LoginUserResponse>> response) {
                Log.i("___boylab>>>___", "onResponse: "+response.toString());
                Log.i("___boylab>>>___", "onResponse:  code = "+response.code());
                Log.i("___boylab>>>___", "onResponse: "+response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseModel<LoginUserResponse>> call, Throwable t) {
                Log.i("___boylab>>>___", "onFailure: 123");
            }
        });

    }
}