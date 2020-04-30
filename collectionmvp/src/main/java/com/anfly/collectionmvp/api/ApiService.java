package com.anfly.collectionmvp.api;

import com.anfly.collectionmvp.bean.FoodBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    String baseUrl = "http://www.qubaobei.com/ios/cf/";

    @GET("dish_list.php?stage_id=1&limit=20&page=1")
    Observable<FoodBean> getDadta();
}
