package com.anfly.livebroadcast1908a;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProjectActivity extends AppCompatActivity implements OnRefreshLoadMoreListener {

    private RecyclerView rv_ok;
    private SmartRefreshLayout srl_ok;
    private ArrayList<FoodBean.DataBean> list;
    private OkAdapter adapter;
    private String footUrl = "http://www.qubaobei.com/ios/cf/dish_list.php";
    private String paramas = "?stage_id=1&limit=20&page=";
    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        initView();
        initData();
    }

    private void initView() {
        rv_ok = (RecyclerView) findViewById(R.id.rv_ok);
        srl_ok = (SmartRefreshLayout) findViewById(R.id.srl_ok);

        rv_ok.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new OkAdapter(list, this);
        rv_ok.setAdapter(adapter);

        srl_ok.setOnRefreshLoadMoreListener(this);

    }

    private void initData() {
        //ok对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        //创建请求体
//        FormBody doby = new FormBody.Builder()
//                .add("stage_id", "1")
//                .add("limit", "20")
//                .add("page", "1")
//                .build();

        //构建请求
        Request requet = new Request.Builder()
                .get()
                .url(footUrl + paramas + page)
                .build();

        //获取call对象
        Call call = okHttpClient.newCall(requet);

        //call执行请求
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
                        List<FoodBean.DataBean> data = foodBean.getData();
                        list.addAll(data);
                        adapter.notifyDataSetChanged();
                        srl_ok.finishRefresh();
                        srl_ok.finishLoadMore();
                    }
                });
            }
        });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        initData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        if (list != null && list.size() > 0) {
            initData();
        }
    }
}
