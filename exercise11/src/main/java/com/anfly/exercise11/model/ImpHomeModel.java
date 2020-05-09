package com.anfly.exercise11.model;


import com.anfly.exercise11.api.ApiService;
import com.anfly.exercise11.bean.FoodBean;
import com.anfly.exercise11.callback.HomeCallback;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImpHomeModel implements HomeModel {
    @Override
    public void getFoodData(HomeCallback homeCallback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<FoodBean> foodData = apiService.getFoodData();
        foodData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FoodBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FoodBean foodBean) {
                        homeCallback.onSuccess(foodBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        homeCallback.onFail("net error:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
