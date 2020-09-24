package com.boylab.retrofitdemo.retrofit.model;

import com.boylab.retrofitdemo.retrofit.bean.RespDataBean;

public class LoginUserResponse extends RespDataBean {

    private long userid;
    private String token;
    private int type;
    private String lastlogin;
    private String createtime;

    public LoginUserResponse() {

    }

    public LoginUserResponse(long userid, String token, int type, String lastlogin, String createtime) {
        this.userid = userid;
        this.token = token;
        this.type = type;
        this.lastlogin = lastlogin;
        this.createtime = createtime;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "userid=" + userid +
                ", token='" + token + '\'' +
                ", type=" + type +
                ", lastlogin='" + lastlogin + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
