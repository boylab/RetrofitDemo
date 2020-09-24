package com.boylab.retrofitdemo.retrofit.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 服务器返回data的父类
 */
public abstract class RespDataBean implements IHttpParameter {

    //检测字段是否为纯数字
    public boolean isNumber(String fileValue){
        if (fileValue != null){
            return fileValue.matches("^d{1,}$");
        }
        return false;
    }

    protected boolean isPositiveInt(String fileValue){
        if (fileValue != null){
            return fileValue.matches("^d{1,}$");
        }
        return false;

    }

    /**
     * @param ba 获得的仪表参数，格式000 000 000 000
     * @return
     */
    public boolean checkBa(String ba) {
        Pattern pattern = Pattern.compile("^((\\d{3}\\s){3}\\d{3})$");
        Matcher matcher = pattern.matcher(ba);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * @param ba     获得的仪表参数，格式000 000 000 000|000 000 000 000...
     * @param number ba包含000 000 000 000的个数
     * @return
     */
    public boolean checkBa(String ba, int number) {
        Pattern pattern = Pattern.compile("^((\\d{3}\\s){3}\\d{3}\\|){" + String.valueOf(number - 1) + ",}((\\d{3}\\s){3}\\d{3})$");
        Matcher matcher = pattern.matcher(ba);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }


}
