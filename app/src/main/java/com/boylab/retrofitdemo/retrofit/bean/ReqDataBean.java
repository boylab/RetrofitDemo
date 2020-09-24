package com.boylab.retrofitdemo.retrofit.bean;

import com.google.gson.Gson;

public abstract class ReqDataBean implements IHttpParameter {

    /*protected String BaseUrl() {
       return "http://192.168.1.0:8080/clientapi/";
    }
    public abstract String getReqestUrl();*/

    public String formJson(){
        return new Gson().toJson(this);
    }

}
