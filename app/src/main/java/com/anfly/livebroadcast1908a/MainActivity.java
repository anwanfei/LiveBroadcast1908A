package com.anfly.livebroadcast1908a;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_get_en;
    private String footUrl = "http://www.qubaobei.com/ios/cf/dish_list.php";
    private String jikeUrl = "https://api.apiopen.top/getJoke?page=1&count=2&type=video";
    private String paramas = "?stage_id=1&limit=20&page=1";
    private Button btn_post_en;
    private ArrayList<FoodBean.DataBean> list;
    private OkAdapter adapter;
    private Button btn_project;
    private String formType = "application/x-www-form-urlencoded;charset=utf-8";
    private String jsonType = "application/json;charset=utf-8";
    private String content = "stage_id=1&limit=20&page=1";
    private Button btn_post_string;
    private Button btn_post_json;
    private Button btn_psot_stream;
    private Button btn_redpackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("TAG", "onCreate当前线程：" + Thread.currentThread().getName());
        initView();
    }


    private void initView() {
        btn_get_en = (Button) findViewById(R.id.btn_get_en);
        btn_get_en.setOnClickListener(this);
        btn_post_en = (Button) findViewById(R.id.btn_post_en);
        btn_post_en.setOnClickListener(this);
        btn_project = (Button) findViewById(R.id.btn_project);
        btn_project.setOnClickListener(this);
        btn_post_string = (Button) findViewById(R.id.btn_post_string);
        btn_post_string.setOnClickListener(this);
        btn_post_json = (Button) findViewById(R.id.btn_post_json);
        btn_post_json.setOnClickListener(this);
        btn_psot_stream = (Button) findViewById(R.id.btn_psot_stream);
        btn_psot_stream.setOnClickListener(this);
        btn_redpackage = (Button) findViewById(R.id.btn_redpackage);
        btn_redpackage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_en:
                getEn();
                break;
            case R.id.btn_post_en:
                postEn();
                break;
            case R.id.btn_project:
                Intent intent = new Intent(this, ProjectActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_post_string:
                postString();
                break;
            case R.id.btn_post_json:
                postJson();
                break;
            case R.id.btn_psot_stream:
                postStream();
                break;
            case R.id.btn_redpackage:
                startActivity(new Intent(this, RedPackageActivity.class));
                break;
        }
    }

    private void postStream() {
        //ok对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        //请求体
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return MediaType.parse(formType);
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8(content);
            }
        };

        //request
        Request requst = new Request.Builder()
                .post(requestBody)
                .url(footUrl)
                .build();

        //call
        Call call = okHttpClient.newCall(requst);

        //call执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "网络错误：" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                FoodBean foodBean = new Gson().fromJson(json, FoodBean.class);

                Log.e("TAG", "title" + foodBean.getData().get(0).getTitle());
            }
        });
    }

    private void postJson() {
        //ok对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        //请求体
        FoodJsonBean foodJsonBean = new FoodJsonBean();
        foodJsonBean.setLimit("20");
        foodJsonBean.setPage("1");
        foodJsonBean.setStage_id("1");
        Gson gson = new Gson();
        String json = gson.toJson(foodJsonBean);

        MediaType mediaType = MediaType.parse(jsonType);

        RequestBody requestBody = RequestBody.create(mediaType, json);

        //request
        Request requst = new Request.Builder()
                .post(requestBody)
                .url(footUrl)
                .build();

        //call
        Call call = okHttpClient.newCall(requst);

        //call执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "网络错误：" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                FoodBean foodBean = new Gson().fromJson(json, FoodBean.class);

                Log.e("TAG", "title" + foodBean.getData().get(0).getTitle());
            }
        });
    }

    private void postString() {
        //ok对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        //请求体
        MediaType mediaType = MediaType.parse(formType);

        RequestBody requestBody = RequestBody.create(mediaType, content);

        //request
        Request requst = new Request.Builder()
                .post(requestBody)
                .url(footUrl)
                .build();

        //call
        Call call = okHttpClient.newCall(requst);

        //call执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "网络错误：" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                FoodBean foodBean = new Gson().fromJson(json, FoodBean.class);

                Log.e("TAG", "title" + foodBean.getData().get(0).getTitle());
            }
        });
    }

    private void postEn() {
        //1.获取ok对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(5, TimeUnit.SECONDS)
//                .readTimeout(5, TimeUnit.SECONDS)
//                .writeTimeout(5, TimeUnit.SECONDS)
//                .addInterceptor(new LogginInterceptor())
//                .addNetworkInterceptor(new LogginInterceptor())
                .addInterceptor(new CacheIntercetor())
                .addNetworkInterceptor(new CacheIntercetor())
                .cache(new Cache(getCacheDir(), 60 * 1024 * 1024))
                .build();

        //2.创建请求体
        FormBody body = new FormBody.Builder()
                .add("stage_id", "1")
                .add("limit", "2")
                .add("page", "1")
                .build();

        //3.获取请求对象
        Request requst = new Request.Builder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .header("User-Agent", "an ok")
                .url(footUrl)
                .post(body)
                .build();

        //4.获取call对象
        Call call = okHttpClient.newCall(requst);

        //5.call执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "网络错误：" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //响应行
                Log.e("ok", "响应行：protocol=" + response.protocol() + "code=" + response.code() + "message=" + response.message());

                //响应头
                Headers headers = response.headers();

                //响应体
                String json = response.body().string();
                final FoodBean foodBean = new Gson().fromJson(json, FoodBean.class);
                for (int i = 0; i < headers.size(); i++) {
                    Log.e("ok", headers.name(i) + ":" + headers.value(i));
                }
                Log.e("TAG", "resopense" + json);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_post_en.setText(foodBean.getData().get(0).getTitle());
                    }
                });
            }
        });
    }

    private void getEn() {
        //1.创建okHttpCLient对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new CacheIntercetor())
                .addNetworkInterceptor(new CacheIntercetor())
                .cache(new Cache(getCacheDir(), 1024 * 1024 * 60))
                .build();

        //2.构建请求对象
        Request request = new Request.Builder()
                .url(footUrl + paramas)
                .get()
                .build();

        //3.过去call对象
        Call call = okHttpClient.newCall(request);

        //4.call执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "网络错误：" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("TAG", "onResponse当前线程：" + Thread.currentThread().getName());
                String json = response.body().string();

                Gson gson = new Gson();
                final FoodBean foodBean = gson.fromJson(json, FoodBean.class);


                Log.e("TAG", "json:" + json.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        btn_get_en.setText(foodBean.getData().get(0).getTitle());
                    }
                });
            }
        });
    }

    /**
     * 日志拦截器
     */
    class LogginInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            //响应前信息
            Request request = chain.request();
            long startTime = System.nanoTime();
            Log.e("TAG", String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));


            //响应中
            Response response = chain.proceed(request);

            //响应后结果打印
            long endTime = System.nanoTime();
            Log.e("TAG", String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (endTime - startTime) / 1e6d, response.headers()));
            return response;
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

    class CacheIntercetor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            //请求前
            Request request = chain.request();

            //判断如果无网时，设置缓存协议
            if (!isNetworkAvailable()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }

            //网络请求
            Response response = chain.proceed(request);

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

}
