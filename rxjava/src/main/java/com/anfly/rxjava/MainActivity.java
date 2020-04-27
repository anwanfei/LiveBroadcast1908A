package com.anfly.rxjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_rxjava1;
    private Button btn_rxjava2;
    private Button btn_rxjava3;
    private Button btn_rxjava_retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_rxjava1 = (Button) findViewById(R.id.btn_rxjava1);

        btn_rxjava1.setOnClickListener(this);
        btn_rxjava2 = (Button) findViewById(R.id.btn_rxjava2);
        btn_rxjava2.setOnClickListener(this);
        btn_rxjava3 = (Button) findViewById(R.id.btn_rxjava3);
        btn_rxjava3.setOnClickListener(this);
        btn_rxjava_retrofit = (Button) findViewById(R.id.btn_rxjava_retrofit);
        btn_rxjava_retrofit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rxjava1:
                rxjava1();
                break;
            case R.id.btn_rxjava2:
                rxjava2();
                break;
            case R.id.btn_rxjava3:
                rxjava3();
                break;
            case R.id.btn_rxjava_retrofit:
                net();
                break;
        }
    }

    private void net() {

        //获取retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        //获取服务接口对象
        ApiService apiService = retrofit.create(ApiService.class);

        //获取被观察
        Observable<FoodBean> observable = apiService.getData();

        //被观察者订阅观察者
        observable
                .subscribeOn(Schedulers.io())//事件产生线程
                .observeOn(AndroidSchedulers.mainThread())//消费事件的线程
                .subscribe(new Observer<FoodBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FoodBean foodBean) {
                        btn_rxjava_retrofit.setText(foodBean.getData().get(0).getTitle());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "onError()" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void rxjava3() {
        Observable.just(123).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer s) throws Exception {
                Log.e("TAG", "accept()" + s);
            }
        });
    }

    private void rxjava2() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("积云教育");
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.e("TAG", "onNext()" + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void rxjava1() {
        //1.创建被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("123");
                emitter.onNext("456");
                emitter.onComplete();
//                emitter.onError(new Throwable());
                emitter.onNext("燃放");
            }
        });

        //2.创建观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("TAG", "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.e("TAG", "onNext()" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "onError()");
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "onComplete()");
            }
        };

        //3.观察者订阅被观察者
        observable.subscribe(observer);
    }
}
