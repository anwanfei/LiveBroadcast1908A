package com.anfly.livebroadcast1908a;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_get_en;
    private String footUrl = "http://www.qubaobei.com/ios/cf/dish_list.php";
    private String jikeUrl = "https://api.apiopen.top/getJoke?page=1&count=2&type=video";
    private String paramas = "?stage_id=1&limit=20&page=1";
    private Button btn_post_en;
    private ArrayList<FoodBean.DataBean> list;
    private OkAdapter adapter;
    private Button btn_project;

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
        }
    }

    private void postEn() {
        //1.获取ok对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        //2.创建请求体
        FormBody body = new FormBody.Builder()
                .add("stage_id", "1")
                .add("limit", "2")
                .add("page", "1")
                .build();

        //3.获取请求对象
        Request requst = new Request.Builder()
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
                String json = response.body().string();
                final FoodBean foodBean = new Gson().fromJson(json, FoodBean.class);
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
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        //2.构建请求对象
        final Request request = new Request.Builder()
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
}
