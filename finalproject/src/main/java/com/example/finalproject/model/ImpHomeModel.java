package com.example.finalproject.model;

import com.example.finalproject.acallback.HomeCallBack;
import com.example.finalproject.api.ApiSerivce;
import com.example.finalproject.bean.FoodBean;

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
    public void getHomeList(HomeCallBack homeCallBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiSerivce.baseFoodUrl)
                .build();

        ApiSerivce apiSerivce = retrofit.create(ApiSerivce.class);
        Observable<FoodBean> observable = apiSerivce.getHomeList();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FoodBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FoodBean foodBean) {
                        homeCallBack.onSuccess(foodBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        homeCallBack.onFail("net error:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
