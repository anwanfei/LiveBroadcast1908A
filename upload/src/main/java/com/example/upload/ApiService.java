package com.example.upload;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    String baseUrl = "http://yun918.cn/";

    @POST("study/public/file_upload.php")
    @Multipart
    Observable<UploadBean> upload(@Part("key") RequestBody requestBody, @Part MultipartBody.Part body);
}
