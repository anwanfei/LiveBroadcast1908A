package com.anfly.loninmvp.model;

import com.anfly.loninmvp.bean.LoginBean;
import com.anfly.loninmvp.api.APIService;
import com.anfly.loninmvp.callback.LoignCallback;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImpLoginModel implements LoginModel {
    @Override
    public void login(final LoignCallback loginCallback, String userName, String password) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIService.baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);
        Observable<LoginBean> observable = apiService.login(userName, password);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        int errorCode = loginBean.getErrorCode();
                        if (errorCode == 0) {
                            loginCallback.onSuccess(loginBean);
                        } else {
                            loginCallback.onFail(loginBean.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginCallback.onFail("登录失败：" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
