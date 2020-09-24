package com.boylab.retrofitdemo.retrofit;

import android.util.Log;

import com.boylab.retrofitdemo.retrofit.util.EncryptUtil;
import com.boylab.retrofitdemo.retrofit.util.HexUtil;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
  private final Gson gson;
  private final TypeAdapter<T> adapter;

  JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
    this.gson = gson;
    this.adapter = adapter;
  }

  @Override
  public T convert(ResponseBody value) throws IOException {
    // 对 value 提前进行解析操作
    String response = value.string();
    Log.i("___boylab>>>___", "convert: "+response);
    byte[] respBytes = response.getBytes();
    String json = null;
    int seed = Integer.valueOf(HexUtil.getMatchFromString(response, "\"seed\":(\\d+)"));
    int salt = Integer.valueOf(HexUtil.getMatchFromString(response, "\"salt\":(\\d+)"));
    int code = Integer.valueOf(HexUtil.getMatchFromString(response, "\"code\":(\\d+)"));
    int chk = Integer.valueOf(HexUtil.getMatchFromString(response, "\"chk\":(\\d+)"));

    String title = "\"data\":";
    int index = response.indexOf(title);
    int start, end;
    if (index != -1) {
      start = index + title.length();
      end = HexUtil.getJsonStringEnd(respBytes, start);
      if (end == -1) {
        json = response;
      } else if (end > start) {
        if (EncryptUtil.check(chk, respBytes, start, end - start + 1)) {
          for (int i = start; i < end + 1; i++) {
            respBytes[i] = EncryptUtil.decodeByte(respBytes[i], seed, salt, i - start);
          }
          json = new String(respBytes, EncryptUtil.TYPE_CODE);
        }
      } else {
        json = null;
      }
    } else {
      json = response;
    }

    Reader reader = new StringReader(json);
    JsonReader jsonReader = gson.newJsonReader(reader);
    try {
      T result = adapter.read(jsonReader);
      if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
        throw new JsonIOException("JSON document was not fully consumed.");
      }
      return result;
    } finally {
      //value.close();
      reader.close();
      jsonReader.close();
    }
  }

}
