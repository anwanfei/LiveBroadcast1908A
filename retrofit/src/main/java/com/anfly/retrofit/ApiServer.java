package com.anfly.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServer {
    //必须以 “/” 结尾
    String baseUrl = "http://www.qubaobei.com/ios/cf/";

    @GET("dish_list.php?stage_id=1&limit=20&page=1")
    Call<ResponseBody> get1();
}
