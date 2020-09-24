package com.boylab.retrofitdemo.retrofit.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexUtil {
    public static byte hexToAsc(int hex) {
        byte asc = 0;
        if (hex <= 9) {
            asc = (byte) (hex + 0x30);
        } else if (hex <= 15) {
            asc = (byte) (hex + 0x37);
        }
        return asc;
    }

    public static byte ascToHex(int asc) {
        byte hex = 0;
        if (asc >= 0x30 && asc <= 0x39) {
            hex = (byte) (asc - 0x30);
        } else if (asc >= 0x41 && asc <= 0x46) {
            hex = (byte) (asc - 0x37);
        } else if (asc >= 0x61 && asc <= 0x66) {
            hex = (byte) (asc - 0x57);
        }
        return hex;
    }

    public static String ascToChar(final byte[] buffer, int index, boolean isHex) {
        byte hex = 0;
        if (isHex) {
            hex = (byte) (ascToHex(buffer[index]) + ascToHex(buffer[index + 1]) * 16);
        } else {
            hex = (byte) (ascToHex(buffer[index]) + ascToHex(buffer[index + 1]) * 10);
        }
        return hex >= 20 ? String.valueOf((char) hex) : "";
    }

    public static byte[] hexToMultiAsc(int hex, int length, boolean isHex) {
        byte[] bytes = new byte[length];
        if (isHex) {
            for (int i = 0; i < length; i++) {
                bytes[i] = hexToAsc((hex >> (i * 4)) & 0x0f);
            }
        } else {
            for (int i = 0; i < length; i++) {
                bytes[i] = hexToAsc((int) (hex / Math.pow(10, i) % 10));
            }
        }
        return bytes;
    }

    public static byte[] longHexToMultiAsc(long hex, int length, boolean isHex) {
        byte[] bytes = new byte[length];
        if (isHex) {
            for (int i = 0; i < length; i++) {
                bytes[i] = hexToAsc((int) ((hex >> (i * 4)) & 0x0f));
            }
        } else {
            for (int i = 0; i < length; i++) {
                bytes[i] = hexToAsc((int) (hex / Math.pow(10, i) % 10));
            }
        }
        return bytes;
    }

    public static byte[] bigHexToMultiAsc(BigInteger hex, int length, boolean isHex) {
        byte[] bytes = new byte[length];
        if (isHex) {
            for (int i = 0; i < length; i++) {
                bytes[i] = hexToAsc(((hex.shiftRight(i * 4)).and(BigInteger.valueOf(0x0f))).intValue());
            }
        } else {
            for (int i = 0; i < length; i++) {
                bytes[i] = hexToAsc((hex.divide(BigInteger.valueOf(10).pow(i))).mod(BigInteger.valueOf(10)).intValue());
            }
        }
        return bytes;
    }


    public static int multiAscToHex(final byte[] buffer, int index, int length, boolean isHex) {
        long hex = 0;
        if ((index + length - 1) < buffer.length) {
            for (int i = 0; i < length; i++) {
                hex += (long) (ascToHex(buffer[index + i]) * Math.pow(isHex ? 16 : 10, i));
            }
        }
        return (int) hex;
    }

    public static long multiAscToLongHex(final byte[] buffer, int index, int length, boolean isHex) {
        long hex = 0;
        if ((index + length - 1) < buffer.length) {
            for (int i = 0; i < length; i++) {
                hex += (long) (ascToHex(buffer[index + i]) * Math.pow(isHex ? 16 : 10, i));
            }
        }
        return hex;
    }

    public static BigInteger multiAscToBigHex(final byte[] buffer, int index, int length, boolean isHex) {
        BigInteger hex = new BigInteger("0");
        if ((index + length - 1) < buffer.length) {
            for (int i = 0; i < length; i++) {
                hex = hex.add(BigInteger.valueOf(ascToHex(buffer[index + i])).multiply(BigInteger.valueOf(isHex ? 16 : 10).pow(i)));
            }
        }
        return hex;
    }

    public static String multiAscToLongString(final byte[] buffer, int index, int length, boolean isHex) {
        long hex = 0;
        if ((index + length - 1) < buffer.length) {
            for (int i = 0; i < length; i++) {
                hex += (long) (ascToHex(buffer[index + i]) * Math.pow(isHex ? 16 : 10, i));
            }
        }
        return String.valueOf(hex);
    }

    public static String multiAscToLongHexString(final byte[] buffer, int index, int length, boolean isHex) {
        long hex = 0;
        if ((index + length - 1) < buffer.length) {
            for (int i = 0; i < length; i++) {
                hex += (long) (ascToHex(buffer[index + i]) * Math.pow(isHex ? 16 : 10, i));
            }
        }
        return String.format("%x", hex);
    }

    public static String multiAscToString(final byte[] buffer, int index, int length, boolean isHex) {
        String value = "";
        if ((index + length - 1) < buffer.length) {
            for (int i = 0; i < length / 2; i++) {
                value += ascToChar(buffer, index + i * 2, isHex);
            }
        }
        return value;
    }

    public static byte[] dateHexToAsc(int hex) {
        byte[] bytes = new byte[6];
        bytes[0] = hexToMultiAsc(hex % 100, 2, true)[0];
        bytes[1] = hexToMultiAsc(hex % 100, 2, true)[1];
        bytes[2] = hexToMultiAsc(hex / 100 % 100, 2, true)[0];
        bytes[3] = hexToMultiAsc(hex / 100 % 100, 2, true)[1];
        bytes[4] = hexToMultiAsc(hex / 10000 % 100, 2, true)[0];
        bytes[5] = hexToMultiAsc(hex / 10000 % 100, 2, true)[1];
        return bytes;
    }

    private static String inputToPointString(String data, int pos) {
        String[] para = {"0", ""};
        if (data.contains(".")) {
            para[0] = data.split("\\.")[0];
            para[1] = data.split("\\.")[1];
        } else {
            para[0] = data;
            para[1] = "";
        }
        if (para[1].length() > pos) {
            para[1] = para[1].substring(0, pos);
        } else if (para[1].length() < pos) {
            int add = pos - para[1].length();
            for (int i = 0; i < add; i++) {
                para[1] += "0";
            }
        }
        return para[0] + (pos > 0 ? ("." + para[1]) : "");
    }

    public static String toNonePointString(String data, int pos) {              //input not weight to none point string
        return inputToPointString(data, pos).replace(".", "");
    }

    private static String dimNonePointString(String data, int division) {
        int value = stringToInt(data.replace(".", ""));
        if (value >= 0) {
            value = (int) ((value + 0.5 * division) / division) * division;
        }
        return String.valueOf(value);
    }

    public static String toDimPoint(String data, int division, int pos) {       //weight dim to point string
        return addPoint(dimNonePointString(data, division), pos);
    }

    public static String toDimNonePoint(String data, int division, int pos) {   //weight dim to none point string
        return addPoint(dimNonePointString(data, division), pos).replace(".", "");
    }

    public static String inputToDimPoint(String data, int division, int pos) {          //input weight dim to point string
        return addPoint(dimNonePointString(toNonePointString(data, pos), division), pos);
    }

    public static String inputToDimNonePoint(String data, int division, int pos) {      //input weight dim to none point string
        return inputToDimPoint(data, division, pos).replace(".", "");
    }

    public static String addPoint(int value, int point) {               //int add point only
        String sign = Math.abs(value) != value ? "-" : "";
        int data = Math.abs(value);
        if (point > 0) {
            int high = data / (int) Math.pow(10, point);
            int low = data % (int) Math.pow(10, point);
            return sign + high + "." + String.format("%0" + point + "d", low);
        } else {
            return String.valueOf(value);
        }
    }

    public static String addPoint(String value, int point) {            //string add point only
        int data = stringToInt(value.replace(".", ""));
        String sign = Math.abs(data) != data ? "-" : "";
        data = Math.abs(data);
        if (point > 0) {
            int high = data / (int) Math.pow(10, point);
            int low = data % (int) Math.pow(10, point);
            return sign + high + "." + String.format("%0" + point + "d", low);
        } else {
            return value;
        }
    }

    public static String roundValue(float value, int point) {
        int data = (int) Math.round(value * Math.pow(10, point));
        String sign = Math.abs(value) != value ? "-" : "";
        data = Math.abs(data);
        if (point > 0) {
            int high = data / (int) Math.pow(10, point);
            int low = data % (int) Math.pow(10, point);
            return sign + high + "." + String.format("%0" + point + "d", low);
        } else {
            return String.valueOf(data);
        }
    }


    public static String roundValue(String value, int point) {
        int data = (int) Math.round(stringToFloat(value) * Math.pow(10, point));
        String sign = Math.abs(data) != data ? "-" : "";
        data = Math.abs(data);
        if (point > 0) {
            int high = data / (int) Math.pow(10, point);
            int low = data % (int) Math.pow(10, point);
            return sign + high + "." + String.format("%0" + point + "d", low);
        } else {
            return value;
        }
    }

    public static String getRound(double data, int point) {
        BigDecimal d = new BigDecimal(data);
        return d.setScale(point, RoundingMode.HALF_UP).toPlainString();
    }

    public static String getRound(double data) {
        String value = getRound(data, 6);
        byte[] b = value.getBytes();
        int count = 0;
        for (int i = 0; i < b.length; i++) {
            if (b[b.length - i - 1] == '0') {
                b[b.length - i - 1] = 0;
                count++;
            } else if (b[b.length - i - 1] == '.') {
                b[b.length - i - 1] = 0;
                count++;
                break;
            } else {
                break;
            }
        }
        return new String(b, 0, b.length - count);
    }


    public static int getIntIndex(int[] array, int value) {
        int index = -1;
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == value) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    public static int getIntIndex(String[] array, String value) {
        int index = -1;
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(value)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    public static byte[] byteReverse(byte[] para) {
        int length = para.length;
        byte[] value = new byte[length];
        for (int i = 0; i < length; i++) {
            value[length - i - 1] = para[i];
        }
        return value;
    }

    public static byte[] byteSplit(byte[] para) {
        byte[] value = new byte[para.length * 2];
        for (int i = 0; i < para.length; i++) {
            value[i * 2] = hexToAsc(para[i] & 0x0f);
            value[i * 2 + 1] = hexToAsc((para[i] >> 4) & 0x0f);
        }
        return value;
    }

    /**
     * 两字节合并一字节
     *
     * @param para
     * @return
     */
    public static byte[] byteUnion(byte[] para) {
        byte[] value = new byte[para.length / 2];
        for (int i = 0; i < value.length; i++) {
            value[i] = (byte) (ascToHex(para[i * 2]) + (ascToHex(para[i * 2 + 1]) << 4));
        }
        return value;
    }

    /**
     * 2n字节合并成n字节
     */
    public static byte[] byteUnion(final byte[] buffer, int index, int length) {
        byte[] value = new byte[length / 2];
        for (int i = 0; i < value.length; i++) {
            value[i] = (byte) (ascToHex(buffer[index + i * 2]) + (ascToHex(buffer[index + i * 2 + 1]) << 4));
        }
        return value;
    }

    public static String padLeft(String src, String ch, int count) {
        int chLen = src.length();
        String value = src;
        if (chLen < count) {
            for (int i = 0; i < count - chLen; i++) {
                value = ch + value;
            }
        } else if (chLen > count) {
            value = value.substring(0, count);
        }
        return value;
    }

    public static byte[] bytePadRight(byte[] src, byte ch, int count) {
        int len = src.length;
        byte[] value = new byte[count];
        if (len > count) {
            System.arraycopy(src, 0, value, 0, count);
        } else if (len == count) {
            value = src;
        } else {
            System.arraycopy(src, 0, value, 0, len);
            for (int i = len; i < count; i++) {
                value[i] = ch;
            }
        }
        return value;
    }

    public static byte[] byteAddRight(byte[] src, byte ch, int count) {
        int len = src.length;
        byte[] value = new byte[len + count];
        System.arraycopy(src, 0, value, 0, len);
        for (int i = 0; i < count; i++) {
            value[len + i] = ch;
        }
        return value;
    }

    public static String padRight(String src, String ch, int count) {
        int chLen = count - src.getBytes().length;
        String value = src;
        if (chLen < count) {
            for (int i = 0; i < count - chLen; i++) {
                value = value + ch;
            }
        } else if (chLen > count) {
            value = value.substring(0, count);
        }
        return value;
    }

    public static String addToString(String base, String value, int count) {
        if ("".equals(value)) {
            return base;
        }
        if (base.contains(",")) {
            ArrayList<String> list = new ArrayList<String>();
            list.add(value);
            String[] strings = base.split(",");
            for (String str : strings) {
                if (!list.contains(str)) {
                    list.add(str);
                }
                if (list.size() >= count) {
                    break;
                }
            }
            return listToString(list);
        } else {
            if (!"".equals(base) && count != -1 && !base.equals(value)) {
                return value + "," + base;
            } else {
                return value;
            }
        }
    }

    public static String[] splitWithComma(String value) {
        String[] strings = new String[]{""};
        if (value.contains(",")) {
            strings = value.split(",");
        } else if (!"".equals(value)) {
            strings = new String[]{value};
        }
        return strings;
    }

    public static int[] stringToIntArray(String value) {
        String[] strings = splitWithComma(value);
        int[] ints = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            ints[i] = HexUtil.stringToInt(strings[i]);
        }
        return ints;
    }

    public static long[] stringToLongArray(String value) {
        String[] strings = splitWithComma(value);
        long[] longs = new long[strings.length];
        for (int i = 0; i < strings.length; i++) {
            longs[i] = HexUtil.stringToLong(strings[i]);
        }
        return longs;
    }

    public static String arrayToString(String[] strings) {
        String value = "";
        for (Object obj : strings) {
            value += String.valueOf(obj) + ",";
        }
        if (!"".equals(value)) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    public static String listToString(List list) {
        String value = "";
        for (Object obj : list) {
            value += String.valueOf(obj) + ",";
        }
        if (!"".equals(value)) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    public static int indexOfArray(String[] strings, String value) {
        int index = -1;
        for (int i = 0; i < strings.length; i++) {
            if (value.equalsIgnoreCase(strings[i])) {
                index = i;
            }
        }
        return index;
    }

    /*
    public static String floatToString(float value) {
        DecimalFormat df = new DecimalFormat("#.#########");
        String data = String.valueOf(value);
        try {
            data = df.format(value);
        } catch (Exception e) {
            e.printStackTrace();
            Sys.saveError(e);
        }
        return data;
    }*/

    public static long stringToLong(String value) {
        long data = 0;
        try {
            if (!TextUtils.isEmpty(value.trim())) {
                data = Long.valueOf(value);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return data;
    }

    public static int stringToInt(String value) {
        int data = -1;
        try {
            if (!TextUtils.isEmpty(value.trim())) {
                data = Integer.valueOf(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static int parseInt(String parsedStr) {
        return parseInt(parsedStr, -1);
    }

    public static int parseInt(String parsedStr, int defaultInt) {
        int parseInt = defaultInt;
        try {
            if (!TextUtils.isEmpty(parsedStr)) {
                parseInt = Integer.valueOf(parsedStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parseInt;
    }

    public static float stringToFloat(String value) {
        float data = 1;
        try {
            if (!TextUtils.isEmpty(value.trim())) {
                data = Float.valueOf(value);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return data;
    }

    public static boolean stringToBoolean(String value) {
        boolean data = false;
        try {
            if (!TextUtils.isEmpty(value.trim())) {
                data = Boolean.valueOf(value);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return data;
    }

    public static boolean arrayContains(int[] collection, int value) {
        boolean isContain = false;
        for (int i : collection) {
            if (i == value) {
                isContain = true;
                break;
            }
        }
        return isContain;
    }

    public static boolean arrayContains(String[] collection, String value) {
        boolean isContain = false;
        for (String i : collection) {
            if (i.equals(value)) {
                isContain = true;
                break;
            }
        }
        return isContain;
    }

    public static int[] ipToDatas(String ip) {
        int[] datas = new int[4];
        String[] ips = ip.split(".");
        if (ips.length == 4) {
            for (int i = 0; i < 4; i++) {
                datas[i] = (byte) stringToInt(ips[i]);
            }
        }
        return datas;
    }

    public static String datasToIp(int[] datas) {
        String ip = "";
        for (int i = 0; i < 4; i++) {
            ip += datas[i];
            if (i < 3) {
                ip += ".";
            }
        }
        return ip;
    }

    public static int[] arrayToInt(String[] array) {
        int[] value = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            value[i] = HexUtil.stringToInt(array[i]);
        }
        return value;
    }

    public static String setArrayToString(String separator, String[] array) {
        String value = "";
        for (int i = 0; i < array.length; i++) {
            value += array[i];
            if (i < array.length - 1) {
                value += separator;
            }
        }
        return value;
    }

    public static int searchIndex(byte[] array, byte value) {
        int index = -1;
        if (array != null && array.length > 0) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == value) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    public static String getMatchFromString(String data, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return (matcher.group(1));
        }
        return null;
    }

    public static int getJsonStringEnd(byte[] data, int start) {
        int end = -1;
        if (data[start] == '{' || data[start] == '[') {
            byte startChar, endChar;
            if (data[start] == '{') {
                startChar = '{';
                endChar = '}';
            } else {
                startChar = '[';
                endChar = ']';
            }

            int count = 0;
            boolean complete = true;
            for (int i = start; i < data.length; i++) {
                if (data[i] == startChar) {
                    count++;
                } else if (data[i] == endChar) {
                    count--;
                }
                if (count <= 0) {
                    end = i;
                    break;
                }
                if (i == data.length - 1) {
                    complete = false;
                }
            }
            if (!complete) {
                end = 0;
            }
        }
        return end;
    }

    /**
     3      * 提供精确加法计算的add方法
     4      * @param value1 被加数
     5      * @param value2 加数
     6      * @return 两个参数的和
     7      */
        public static float add(float value1,float value2){
                 BigDecimal b1 = new BigDecimal(Float.valueOf(value1));
                 BigDecimal b2 = new BigDecimal(Float.valueOf(value2));
                 return b1.add(b2).floatValue();
             }

                 /**
     15      * 提供精确减法运算的sub方法
     16      * @param value1 被减数
     17      * @param value2 减数
     18      * @return 两个参数的差
     19      */
                public static double sub(double value1,double value2){
                BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
                    BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
                return b1.subtract(b2).floatValue();
            }

                 /**
           * 提供精确乘法运算的mul方法
     28      * @param value1 被乘数
     29      * @param value2 乘数
     30      * @return 两个参数的积
     31      */
                 public static double mul(double value1,double value2){
                 BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
                 BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
                 return b1.multiply(b2).floatValue();
             }

                 /**
     39      * 提供精确的除法运算方法div
     40      * @param value1 被除数
     41      * @param value2 除数
     42      * @param scale 精确范围
     43      * @return 两个参数的商
     44      * @throws IllegalAccessException
     45      */
                public static double div(double value1,double value2,int scale) throws IllegalAccessException{
                 //如果精确范围小于0，抛出异常信息
                 if(scale<0){
                        throw new IllegalAccessException("精确度不能小于0");
                     }
                 BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
                 BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
                 return b1.divide(b2, scale).floatValue();
             }


    /**
     * byte[] 转  HexString
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    /**
     * hexString 转 ASCII字符串
     * @param hexString
     * @return
     */
    public static String hexStringToAsc(String hexString){
        int length = hexString.length();
        int counts = length / 2;
        byte[] bytes=new byte[counts];
        for (int i = 0; i < counts; i ++){
            bytes[i] = (byte) Integer.parseInt(hexString.substring(length - 2 * i - 2, length - 2 * i), 0x10);
        }
        String ascString = new String(bytes, Charset.forName("GB18030"));
        return ascString.trim();
    }

    public static String ascStringToHex(String ascString){
        byte[] bytes = ascString.getBytes(Charset.forName("GB18030"));
        bytes = byteReverse(bytes);
        String hexString = bytesToHexString(bytes);
        return hexString;
    }



    public static boolean checkPass(String value) {
        return (value != null && value.matches("^[A-Za-z0-9]{6,}$"));
    }

    public static boolean checkCompPass(String value) {
        return (value != null && value.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{6,}$"));
    }

    public static boolean checkNameInput(String value) {
        return (value != null && value.matches("^[A-Za-z0-9\\(\\)\\-_（）\\u4E00-\\u9FA5]{2,}$"));
    }

    public static boolean checkInputWithSplit(String value) {
        return (value != null && value.matches("^[A-Za-z0-9\\(\\)\\-_，。？！（）、…,\\.\\?!\\s\\|\\u4E00-\\u9FA5]*$"));
    }

    public static boolean checkInputWithPunc(String value) {
        return (value != null && value.matches("^[A-Za-z0-9\\(\\)\\-_，。？！（）、…,\\.\\?!\\s\\u4E00-\\u9FA5]*$"));
    }
}