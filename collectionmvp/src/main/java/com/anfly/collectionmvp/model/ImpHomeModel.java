package com.anfly.collectionmvp.model;

import com.anfly.collectionmvp.api.ApiService;
import com.anfly.collectionmvp.bean.ConllectionDbBean;
import com.anfly.collectionmvp.bean.FoodBean;
import com.anfly.collectionmvp.callback.HomeCallBack;
import com.anfly.collectionmvp.utils.DbHelper;

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
    public void homeData(final HomeCallBack homeCallBack) {
        //获取retrofit
        Retrofit retrfit = new Retrofit.Builder()
                .baseUrl(ApiService.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        //获取接口对象
        ApiService apiService = retrfit.create(ApiService.class);

        //获取被观察者
        Observable<FoodBean> observable = apiService.getDadta();

        //订阅
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
                        homeCallBack.onFail("网络错误:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void insert(ConllectionDbBean conllectionDbBean, HomeCallBack homeCallBack) {
        long insert = DbHelper.getInstancec().insert(conllectionDbBean);
        if (insert >= 0) {
            homeCallBack.onInsertSuccess("收藏成功");
        } else {
            homeCallBack.onInsertSuccess("收藏失败");
        }
    }
}
