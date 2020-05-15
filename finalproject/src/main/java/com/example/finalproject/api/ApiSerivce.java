package com.example.finalproject.api;

import com.example.finalproject.bean.BannerBean;
import com.example.finalproject.bean.FoodBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiSerivce {
    String baseFoodUrl = "http://www.qubaobei.com/";
    String baseBannerUrl = "https://www.wanandroid.com/";

    @GET("ios/cf/dish_list.php?stage_id=1&limit=20&page=1")
    Observable<FoodBean> getHomeList();

    @GET("banner/json")
    Observable<BannerBean> getBannner();
}
