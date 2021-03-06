package com.example.finalproject.api;

import com.example.finalproject.bean.BannerBean;
import com.example.finalproject.bean.FoodBean;
import com.example.finalproject.bean.RxDataBean;
import com.example.finalproject.bean.PublicBean;
import com.example.finalproject.bean.SmartBean;
import com.example.finalproject.bean.TabBean;
import com.example.finalproject.bean.UploadBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiSerivce {
    String baseFoodUrl = "http://www.qubaobei.com/";
    String baseBannerUrl = "https://www.wanandroid.com/";
    String baseUploadUrl = "http://yun918.cn/";
    //    String baseDownUrl = "http://cdn.banmi.com/";
    String baseDownUrl = "https://dl.hdslb.com/";
    String baseSmartUrl = "http://gank.io";

    String baseComposeUrl = "https://gitee.com/";


    @GET("ios/cf/dish_list.php?stage_id=1&limit=20&page=1")
    Observable<FoodBean> getHomeList();

    @GET("banner/json")
    Observable<BannerBean> getBannner();


    @POST("study/public/file_upload.php")
    @Multipart
    Observable<UploadBean> upload(@Part("key") RequestBody requestBody, @Part MultipartBody.Part file);

    //    @GET("banmiapp/apk/banmi_330.apk")
    @GET("mobile/latest/iBiliPlayer-bili.apk?t=1589783162000&spm_id_from=333.47.b_646f776e6c6f61642d6c696e6b.1")
    @Streaming
    Observable<ResponseBody> downLoad();

    @GET("project/tree/json")
    Observable<TabBean> getTabs();

    @GET("project/list/1/json")
    Observable<PublicBean> getPublicFragmentList(@Query("cid") int id);

    @GET("/api/data/福利/20/{page}")
    Observable<SmartBean> getSmarts(@Path("page") int page);

    @GET("AnDrFly/LiveBroadcast/raw/master/homedata")
    Observable<RxDataBean> getRxData();

    @GET()
    Observable<BannerBean> getBannner(@Url String baseBannerUrl);

    @GET()
    Observable<PublicBean> getArticleList(@Url String articleUrl);

}
