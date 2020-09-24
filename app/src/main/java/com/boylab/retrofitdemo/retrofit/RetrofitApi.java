package com.boylab.retrofitdemo.retrofit;

import com.boylab.retrofitdemo.retrofit.bean.RequestModel;
import com.boylab.retrofitdemo.retrofit.bean.ResponseModel;
import com.boylab.retrofitdemo.retrofit.model.LoginUserRequest;
import com.boylab.retrofitdemo.retrofit.model.LoginUserResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitApi {

    // TODO: 2020/9/23 第一种、暂不可用
    @POST("LoginUser?")
    Call<ResponseModel<LoginUserResponse>> loginUser(@Body RequestModel loginUser);

    // TODO: 2020/9/23 第二种、目前可用
    @POST("LoginUser?")
    Call<ResponseModel<LoginUserResponse>> loginUser1(@Query("seed") int seed, @Query("salt") int salt, @Query("uuid") String uuid, @Query("data") String data, @Query("chk") int chk);

    // TODO: 2020/9/24 第三种，暂不用
    @POST("LoginUser?")
    Call<ResponseModel<LoginUserResponse>> loginUser2(@Body RequestBody requestBody);
    
}
