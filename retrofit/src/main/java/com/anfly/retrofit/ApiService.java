package com.anfly.retrofit;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiService{
    //必须以 “/” 结尾
    String baseUrl = "http://www.qubaobei.com/ios/cf/";

    @GET("dish_list.php?stage_id=1&limit=20&page=1")
    Call<ResponseBody> get1();

    @GET("dish_list.php")
    Call<ResponseBody> get2(@Query("stage_id") String stage_id, @Query("limit") String limit, @Query("page") String page);

    @GET("dish_list.php")
    Call<ResponseBody> get3(@QueryMap Map<String, String> map);

    @POST("dish_list.php")
    @FormUrlEncoded
    Call<ResponseBody> post1(@Field("stage_id") String stage_id, @Field("limit") String limit, @Field("page") String page);

    @POST("dish_list.php")
    @FormUrlEncoded
    Call<ResponseBody> post2(@FieldMap Map<String, String> map);

    @POST("dish_list.php")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Call<ResponseBody> post3(@Body RequestBody requestBody);

    @GET("dish_list.php?stage_id=1&limit=20&page=1")
    Call<FoodBean> get4();

    @GET
    Call<FoodBean> get5(@Url() String url);

    @GET
    Call<FoodBean> get6(@Url() String url, @Query("stage_id") String stage_id, @Query("limit") String limit, @Query("page") String page);

    @GET("{dish_list}.php?stage_id=1&limit=20&page=1")
    Call<FoodBean> get7(@Path("dish_list") String dish_list);

    @POST("dish_list.php")
    @Headers("Content-Type:application/json")
    Call<ResponseBody> post4(@Body RequestBody requestBody);

    @POST
    Call<ResponseBody> post5(@Url String url, @Body RequestBody requestBody, @Header("Content-Type")String type);

}
