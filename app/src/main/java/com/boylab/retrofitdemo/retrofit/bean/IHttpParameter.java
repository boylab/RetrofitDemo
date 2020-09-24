package com.boylab.retrofitdemo.retrofit.bean;

import java.io.Serializable;
import java.util.UUID;

public interface IHttpParameter extends Serializable {

    String mUuid = UUID.randomUUID().toString().replace("-", "");

}
