package com.boylab.retrofitdemo.retrofit.bean;

/**
 * 返回称重记录实体类
 */
public class ResponseModel<T extends RespDataBean> implements IHttpParameter {

    protected int seed;
    protected int salt;
    protected String time;
    protected boolean resp;
    protected int code;
    protected int chk;
    protected T data;

    public ResponseModel() {
    }

    public ResponseModel(int seed, int salt, String time, boolean resp, int code, int chk, T data) {
        this.seed = seed;
        this.salt = salt;
        this.time = time;
        this.resp = resp;
        this.code = code;
        this.chk = chk;
        this.data = data;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isResp() {
        return resp;
    }

    public void setResp(boolean resp) {
        this.resp = resp;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getChk() {
        return chk;
    }

    public void setChk(int chk) {
        this.chk = chk;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespStandardBean{" +
                "seed=" + seed +
                ", salt=" + salt +
                ", time='" + time + '\'' +
                ", resp=" + resp +
                ", code=" + code +
                ", chk=" + chk +
                ", data=" + data +
                '}';
    }
}
