package com.anfly.retrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_get1;
    private Button btn_get2;
    private Button btn_get3;

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
        }
    }

    private void get3() {
        //retrofit对象
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiServer.baseUrl).build();

        //获取服务对象
        ApiServer apiServer = retrofit.create(ApiServer.class);

        //服务对象调用方法获得call对象
        HashMap<String, String> map = new HashMap<>();
        map.put("stage_id", "1");
        map.put("limit", "20");
        map.put("page", "1");
        Call<ResponseBody> call = apiServer.get3(map);

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
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiServer.baseUrl).build();

        //获取服务对象
        ApiServer apiServer = retrofit.create(ApiServer.class);

        //服务对象调用方法获得call对象
        Call<ResponseBody> call = apiServer.get2("1", "20", "1");

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
                .baseUrl(ApiServer.baseUrl)//必须要有
                .build();

        //获取接口服务对象
        ApiServer apiServer = retrfit.create(ApiServer.class);

        //接口服务对象调用接口中的方法得到call
        Call<ResponseBody> call = apiServer.get1();

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
}
