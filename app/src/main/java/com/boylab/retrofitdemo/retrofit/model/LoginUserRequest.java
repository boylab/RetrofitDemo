package com.boylab.retrofitdemo.retrofit.model;

import com.boylab.retrofitdemo.retrofit.bean.ReqDataBean;

public class LoginUserRequest extends ReqDataBean {
    private String username;            //string用户名
    private String password;            //string密码

    public LoginUserRequest() {
    }

    public LoginUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ReqDataLoginUserBean{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
