package com.example.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

public interface ApiService {
    String baseUrl = "http://cdn.banmi.com/";

    @GET("banmiapp/apk/banmi_330.apk")
    @Streaming
    Observable<ResponseBody> getApk();
}
