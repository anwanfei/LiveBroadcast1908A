package com.example.finalproject.model;

import com.example.finalproject.api.ApiSerivce;
import com.example.finalproject.bean.BannerBean;
import com.example.finalproject.bean.ComPoseBean;
import com.example.finalproject.bean.PublicBean;
import com.example.finalproject.bean.RxDataBean;
import com.example.finalproject.callback.RxCallBack;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImpRxModel implements RxModel {
    @Override
    public void getRxData(RxCallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiSerivce.baseComposeUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiSerivce apiSerivce = retrofit.create(ApiSerivce.class);

        Observable<RxDataBean> rxData = apiSerivce.getRxData();

        rxData.flatMap(new Function<RxDataBean, ObservableSource<ComPoseBean>>() {
            @Override
            public ObservableSource<ComPoseBean> apply(RxDataBean rxDataBean) throws Exception {
                String articleUrl = rxDataBean.getArticleList();
                String bannerUrl = rxDataBean.getBannerUrl();
                Observable<PublicBean> articleListObservable = apiSerivce.getArticleList(articleUrl);
                Observable<BannerBean> bannnerObservable = apiSerivce.getBannner(bannerUrl);
                Observable<ComPoseBean> zip = Observable.zip(bannnerObservable, articleListObservable, new BiFunction<BannerBean, PublicBean, ComPoseBean>() {
                    @Override
                    public ComPoseBean apply(BannerBean bannerBean, PublicBean publicBean) throws Exception {
                        return new ComPoseBean(bannerBean, publicBean);
                    }
                });
                return zip;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ComPoseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ComPoseBean comPoseBean) {
                        callBack.onSuccess(comPoseBean);
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
