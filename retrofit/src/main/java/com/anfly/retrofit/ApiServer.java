package com.anfly.retrofit;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiServer {
    //必须以 “/” 结尾
    String baseUrl = "http://www.qubaobei.com/ios/cf/";

    @GET("dish_list.php?stage_id=1&limit=20&page=1")
    Call<ResponseBody> get1();

    @GET("dish_list.php")
    Call<ResponseBody> get2(@Query("stage_id") String stage_id, @Query("limit") String limit, @Query("page") String page);

    @GET("dish_list.php")
    Call<ResponseBody> get3(@QueryMap Map<String, String> map);

}
