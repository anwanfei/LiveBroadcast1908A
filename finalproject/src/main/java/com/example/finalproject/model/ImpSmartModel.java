package com.example.finalproject.model;

import com.example.finalproject.api.ApiSerivce;
import com.example.finalproject.bean.SmartBean;
import com.example.finalproject.callback.SmartCallBack;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImpSmartModel implements SmartModel {
    @Override
    public void getSmart(int page, SmartCallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiSerivce.baseSmartUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        ApiSerivce apiSerivce = retrofit.create(ApiSerivce.class);
        apiSerivce.getSmarts(page).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<SmartBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SmartBean smartBean) {
                        callBack.onSuccess(smartBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFail("error:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
