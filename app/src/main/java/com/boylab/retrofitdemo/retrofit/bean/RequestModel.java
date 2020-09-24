package com.boylab.retrofitdemo.retrofit.bean;

import android.util.Log;

import com.boylab.retrofitdemo.retrofit.util.EncryptUtil;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * 标准实体类，进行网路请求
 */
public class RequestModel<T extends ReqDataBean> implements IHttpParameter {

    private int seed;       //加密算法类型
    private int salt;       //加密随机数
    private String uuid;    //本机唯一编号，如mac地址，需加密           
    private String data;    //具体数据，需加密
    private int chk;        //数据校验，需加密，将加密前的uuid数据及data数据分别校验后数值相加

    public RequestModel() {
    }

    public RequestModel(T data) {
        setData(data);
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public int getSalt() {
        return salt;
    }

    public void setSalt(int salt) {
        this.salt = salt;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getData() {
        // TODO: 2020/9/23 获得原json，需解密
        return data;
    }

    public void setData(T data) {
        encryptData(data.formJson());
    }

    public void encryptData(String jsonData) {
        Random random = new Random(System.currentTimeMillis());
        this.seed = random.nextInt(256);
        this.salt = random.nextInt(65536);

        setUuid(EncryptUtil.encodeString(seed, salt, mUuid));
        this.data = EncryptUtil.encodeString(seed, salt, jsonData);
        setChk(EncryptUtil.getCheck(uuid, getData()));
    }

    public int getChk() {
        return chk;
    }

    public void setChk(int chk) {
        this.chk = chk;
    }

    public RequestBody requestBody(){
        String strEntity = new Gson().toJson(this);
        Log.i("___boylab>>>___", "requestBody: "+strEntity);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        return body;
    }

    @Override
    public String toString() {
        return "ReqStandardBean{" +
                "seed=" + seed +
                ", salt=" + salt +
                ", uuid='" + uuid + '\'' +
                ", data='" + data + '\'' +
                ", chk=" + chk +
                '}';
    }

}
