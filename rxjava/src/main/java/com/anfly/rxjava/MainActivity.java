package com.anfly.rxjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_rxjava1;
    private Button btn_rxjava2;
    private Button btn_rxjava3;
    private Button btn_rxjava_retrofit;
    private Button btn_fromarrary;
    private Button btn_just;
    private Button btn_range;
    private Button btn_interval;
    private long newLong;
    private long count;
    private Disposable disposable;
    private Button btn_filter;
    private Button btn_map;
    private Button btn_flatmap;
    private Button btn_concatmap;

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
        btn_fromarrary = (Button) findViewById(R.id.btn_fromarrary);
        btn_fromarrary.setOnClickListener(this);
        btn_just = (Button) findViewById(R.id.btn_just);
        btn_just.setOnClickListener(this);
        btn_range = (Button) findViewById(R.id.btn_range);
        btn_range.setOnClickListener(this);
        btn_interval = (Button) findViewById(R.id.btn_interval);
        btn_interval.setOnClickListener(this);
        btn_filter = (Button) findViewById(R.id.btn_filter);
        btn_filter.setOnClickListener(this);
        btn_map = (Button) findViewById(R.id.btn_map);
        btn_map.setOnClickListener(this);
        btn_flatmap = (Button) findViewById(R.id.btn_flatmap);
        btn_flatmap.setOnClickListener(this);
        btn_concatmap = (Button) findViewById(R.id.btn_concatmap);
        btn_concatmap.setOnClickListener(this);
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
            case R.id.btn_fromarrary:
                fromArray();
                break;
            case R.id.btn_just:
                just();
                break;
            case R.id.btn_range:
                range();
                break;
            case R.id.btn_interval:
                inerval();
                break;
            case R.id.btn_filter:
                filter();
                break;
            case R.id.btn_map:
                map();
                break;
            case R.id.btn_flatmap:
                flatmap();
                break;
            case R.id.btn_concatmap:
                zip();
                break;
        }
    }

    private void zip() {
        Integer[] a = {1, 2, 3};
        Integer[] b = {4, 5, 6};
        Observable<Integer> observableA = Observable.fromArray(a);
        Observable<Integer> observableB = Observable.fromArray(b);

        Observable.zip(observableA, observableB, new BiFunction<Integer, Integer, String>() {
            @Override
            public String apply(Integer integer, Integer integer2) throws Exception {
                return integer + " - " + integer2;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("TAG", "accept()：" + s);
            }
        });
    }

    private void flatmap() {
        Integer[] arr = {1, 2, 3, 4, 5};
        Observable.fromArray(arr).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                String[] arr = new String[3];
                arr[0] = "1";
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = integer + arr[i];
                }
                return Observable.fromArray(arr);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("TAG", "accept()：" + s);
            }
        });
    }

    private void map() {
        Integer[] arr = {1, 2, 3, 4, 5};
        Observable.fromArray(arr).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return integer + "好听";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("TAG", "accept()：" + s);
            }
        });
    }

    private void filter() {
        Integer[] arr = {1, 2, 3, 4, 5};
        Observable.fromArray(arr).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                if (integer > 2 && integer < 5) {
                    return true;
                }
                return false;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("TAG", "accept()：" + integer);
            }
        });
    }

    private void inerval() {
        count = 2;
        Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        newLong = count - aLong;
                        if (newLong <= 0) {
                            disposable.dispose();
                        }
                        btn_interval.setText("倒计时：" + newLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void range() {
        Observable.range(9, 10)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("TAG", "accept()：" + integer);
                    }
                });
    }

    private void just() {
        Integer[] a = {1, 2, 3};
        Integer[] b = {9, 8, 7};

        Observable.just(a, b).subscribe(new Consumer<Integer[]>() {
            @Override
            public void accept(Integer[] integers) throws Exception {
                for (Integer integer : integers) {
                    Log.e("TAG", "accept()：" + integer);
                }
            }
        });
    }

    private void fromArray() {
        Integer[] arr = {1, 2, 3, 4, 5};
        Observable.fromArray(arr).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("TAG", "accept()：" + integer);
            }
        });
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
