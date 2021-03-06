package com.anfly.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_get1;
    private Button btn_get2;
    private Button btn_get3;
    private Button btn_psot1;
    private Button btn_psot2;
    private Button btn_psot3;
    private Button btn_get4;
    private Button btn_get5;
    private Button btn_get6;
    private Button btn_get7;
    private Button btn_post4;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //调到activity
            //handler.removeCallbacksAndMessages(null);
            //handler = null;
            //finish;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_get1 = (Button) findViewById(R.id.btn_get1);

        btn_get1.setOnClickListener(this);
        btn_get2 = (Button) findViewById(R.id.btn_get2);
        btn_get2.setOnClickListener(this);
        btn_get3 = (Button) findViewById(R.id.btn_get3);
        btn_get3.setOnClickListener(this);
        btn_psot1 = (Button) findViewById(R.id.btn_psot1);
        btn_psot1.setOnClickListener(this);
        btn_psot2 = (Button) findViewById(R.id.btn_psot2);
        btn_psot2.setOnClickListener(this);
        btn_psot3 = (Button) findViewById(R.id.btn_psot3);
        btn_psot3.setOnClickListener(this);
        btn_get4 = (Button) findViewById(R.id.btn_get4);
        btn_get4.setOnClickListener(this);
        btn_get5 = (Button) findViewById(R.id.btn_get5);
        btn_get5.setOnClickListener(this);
        btn_get6 = (Button) findViewById(R.id.btn_get6);
        btn_get6.setOnClickListener(this);
        btn_get7 = (Button) findViewById(R.id.btn_get7);
        btn_get7.setOnClickListener(this);
        btn_post4 = (Button) findViewById(R.id.btn_post4);
        btn_post4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get1:
                get1();
                break;
            case R.id.btn_get2:
                get2();
                break;
            case R.id.btn_get3:
                get3();
                break;
            case R.id.btn_psot1:
                post1();
                break;
            case R.id.btn_psot2:
                post2();
                break;
            case R.id.btn_psot3:
                post3();
                break;
            case R.id.btn_get4:
                get4();
                break;
            case R.id.btn_get5:
                get5();
                break;
            case R.id.btn_get6:
                get6();
                break;
            case R.id.btn_get7:
                get7();
                break;
            case R.id.btn_post4:
                psot4();
                break;
        }
    }

    private void psot4() {
        //retrofit对象
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.baseUrl).build();

        //接口服务对象
        ApiService apiService = retrofit.create(ApiService.class);

        //获取call
        FoodJsonBean foodJsonBean = new FoodJsonBean();
        foodJsonBean.setLimit("20");
        foodJsonBean.setPage("1");
        foodJsonBean.setStage_id("1");
        String json = new Gson().toJson(foodJsonBean);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json);
        Call<ResponseBody> call = apiService.post5("dish_list.php", body, "application/json");

        //call执行请求
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String json = response.body().string();
                    FoodBean foodBean = new Gson().fromJson(json, FoodBean.class);
                    btn_post4.setText(foodBean.getData().get(0).getTitle());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "网络错误：" + t.getMessage());
            }
        });
    }

    private void get7() {
        //retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.baseUrl)
                .build();

        //接口服务对象
        ApiService apiService = retrofit.create(ApiService.class);

        //获取call
        Call<FoodBean> call = apiService.get7("dish_list");

        //call执行请求
        call.enqueue(new Callback<FoodBean>() {
            @Override
            public void onResponse(Call<FoodBean> call, Response<FoodBean> response) {
                FoodBean foodBean = response.body();
                btn_get7.setText(foodBean.getData().get(0).getTitle());
            }

            @Override
            public void onFailure(Call<FoodBean> call, Throwable t) {
                Log.e("TAG", "网络错误：" + t.getMessage());
            }
        });
    }

    private void get6() {
        //retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.baseUrl)
                .build();

        //接口服务对象
        ApiService apiService = retrofit.create(ApiService.class);

        //获取call
        Call<FoodBean> call = apiService.get6("dish_list.php", "1", "20", "1");

        //call执行请求
        call.enqueue(new Callback<FoodBean>() {
            @Override
            public void onResponse(Call<FoodBean> call, Response<FoodBean> response) {
                FoodBean foodBean = response.body();
                btn_get6.setText(foodBean.getData().get(0).getTitle());
            }

            @Override
            public void onFailure(Call<FoodBean> call, Throwable t) {
                Log.e("TAG", "网络错误：" + t.getMessage());
            }
        });
    }

    private void get5() {
        //retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.baseUrl)
                .build();

        //接口服务对象
        ApiService apiService = retrofit.create(ApiService.class);

        //获取call
        Call<FoodBean> call = apiService.get5("dish_list.php?stage_id=1&limit=20&page=1");

        //call执行请求
        call.enqueue(new Callback<FoodBean>() {
            @Override
            public void onResponse(Call<FoodBean> call, Response<FoodBean> response) {
                FoodBean foodBean = response.body();
                btn_get5.setText(foodBean.getData().get(0).getTitle());
            }

            @Override
            public void onFailure(Call<FoodBean> call, Throwable t) {
                Log.e("TAG", "网络错误：" + t.getMessage());
            }
        });
    }

    private void get4() {
        //retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.baseUrl)
                .build();

        //接口服务对象
        ApiService apiService = retrofit.create(ApiService.class);

        //获取call
        Call<FoodBean> call = apiService.get4();

        //call执行请求
        call.enqueue(new Callback<FoodBean>() {
            @Override
            public void onResponse(Call<FoodBean> call, Response<FoodBean> response) {
                FoodBean foodBean = response.body();
                btn_get4.setText(foodBean.getData().get(0).getTitle());
            }

            @Override
            public void onFailure(Call<FoodBean> call, Throwable t) {
                Log.e("TAG", "网络错误：" + t.getMessage());
            }
        });

    }

    private void post3() {
        //retrofit对象
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.baseUrl).build();

        //接口服务对象
        ApiService apiService = retrofit.create(ApiService.class);

        //获取call
        FormBody formBody = new FormBody.Builder()
                .add("stage_id", "1")
                .add("limit", "20")
                .add("page", "1")
                .build();
        Call<ResponseBody> call = apiService.post3(formBody);

        //call执行请求
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String json = response.body().string();
                    FoodBean foodBean = new Gson().fromJson(json, FoodBean.class);
                    btn_psot3.setText(foodBean.getData().get(0).getTitle());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "网络错误：" + t.getMessage());
            }
        });
    }

    private void post2() {
        //retrofit对象
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.baseUrl).build();

        //接口服务对象
        ApiService apiService = retrofit.create(ApiService.class);

        //获取call
        HashMap<String, String> map = new HashMap<>();
        map.put("stage_id", "1");
        map.put("limit", "20");
        map.put("page", "1");
        Call<ResponseBody> call = apiService.post2(map);

        //call执行请求
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String json = response.body().string();
                    FoodBean foodBean = new Gson().fromJson(json, FoodBean.class);
                    btn_psot2.setText(foodBean.getData().get(0).getTitle());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "网络错误：" + t.getMessage());
            }
        });
    }

    private void post1() {
        //retrofit对象
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.baseUrl).build();

        //接口服务对象
        ApiService apiService = retrofit.create(ApiService.class);

        //获取call
        Call<ResponseBody> call = apiService.post1("5", "20", "1");

        //call执行请求
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String json = response.body().string();
                    FoodBean foodBean = new Gson().fromJson(json, FoodBean.class);
                    btn_psot1.setText(foodBean.getData().get(0).getTitle());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "网络错误：" + t.getMessage());
            }
        });
    }

    private void get3() {
        //retrofit对象
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.baseUrl).build();

        //获取服务对象
        ApiService apiService = retrofit.create(ApiService.class);

        //服务对象调用方法获得call对象
        HashMap<String, String> map = new HashMap<>();
        map.put("stage_id", "1");
        map.put("limit", "20");
        map.put("page", "1");
        Call<ResponseBody> call = apiService.get3(map);

        //call执行请求
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String json = response.body().string();
                    FoodBean foodBean = new Gson().fromJson(json, FoodBean.class);
                    btn_get3.setText(foodBean.getData().get(0).getTitle());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "网络错误：" + t.getMessage());
            }
        });
    }

    private void get2() {
        //retrofit对象
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.baseUrl).build();

        //获取服务对象
        ApiService apiService = retrofit.create(ApiService.class);

        //服务对象调用方法获得call对象
        Call<ResponseBody> call = apiService.get2("1", "20", "1");

        //call执行请求
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String json = response.body().string();
                    FoodBean foodBean = new Gson().fromJson(json, FoodBean.class);
                    btn_get2.setText(foodBean.getData().get(0).getTitle());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "网络错误：" + t.getMessage());
            }
        });

    }

    private void get1() {
        //获取retrofit对象
        Retrofit retrfit = new Retrofit.Builder()
                .baseUrl(ApiService.baseUrl)//必须要有
                .client(findOK())
                .build();

        //获取接口服务对象
        ApiService apiService = retrfit.create(ApiService.class);

        //接口服务对象调用接口中的方法得到call
        Call<ResponseBody> call = apiService.get1();

        //call执行请求
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                //当前所在线程：main
                Log.e("TAG", "当前线程：" + Thread.currentThread().getName());
                try {
                    String json = response.body().string();
                    Log.e("TAG", "responseBody:" + json);
                    FoodBean foodBean = new Gson().fromJson(json, FoodBean.class);
                    btn_get1.setText(foodBean.getData().get(0).getTitle());
                    Toast.makeText(MainActivity.this, foodBean.getData().get(0).getTitle(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "网络错误：" + t.getMessage());
            }
        });
    }

    private OkHttpClient findOK() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new RetrfitCacheInterceptor())
                .cache(new Cache(new File(getCacheDir(), "retrfit"), 1024 * 1024 * 10))
                .build();
    }

    private class RetrfitCacheInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            //请求前
            Request request = chain.request();

            //判断如果无网时，设置缓存协议
            if (!isNetworkAvailable()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }

            //网络请求
            okhttp3.Response response = chain.proceed(request);

            //是否有网
            if (isNetworkAvailable()) {
                int maxAge = 0;
                response = response.newBuilder().removeHeader("Pragma")
                        .addHeader("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
                return response;
            } else {
                int maxStale = 60 * 60 * 24;
                response = response.newBuilder().removeHeader("Pragma")
                        .addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
                return response;
            }
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            return info.isAvailable();
        }
        return false;
    }
}
