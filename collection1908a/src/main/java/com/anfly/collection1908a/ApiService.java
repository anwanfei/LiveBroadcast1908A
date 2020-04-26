package com.anfly.collection1908a;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    String baseUrl = "http://www.qubaobei.com/ios/cf/";

    @GET("dish_list.php?stage_id=1&limit=20&page=1")
    Call<FoodBean> getDadta();
}
