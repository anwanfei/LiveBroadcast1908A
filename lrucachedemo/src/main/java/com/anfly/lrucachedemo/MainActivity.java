package com.anfly.lrucachedemo;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private String mUrl = "https://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/";
    private String footUrl = "http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=";
    private int page = 1;
    private RecyclerView mRlv;
    private RefreshLayout mRefreshLayout;
    private OkHttpClient mClient;
    private MyAdatper mMyAdatper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClient = new OkHttpClient();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            initData();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
        initView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initData();
        }
    }

    private void initView() {
        mRlv = findViewById(R.id.rlv);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRlv.setLayoutManager(manager);
        List<FoodBean.DataBean> data = new ArrayList<>();
        mMyAdatper = new MyAdatper(this, data);

        mRlv.setAdapter(mMyAdatper);
        mRlv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mRefreshLayout = findViewById(R.id.srl);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mMyAdatper.clearData();
                initData();

            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });

        mRefreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.FixedBehind));
    }

    private void initData() {
        final Request request = new Request.Builder()
                .url(footUrl + page)
                .build();
        okhttp3.Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.e(TAG, "onFailure:" + e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                Gson gson = new Gson();
                FoodBean bean = gson.fromJson(response.body().string(), FoodBean.class);
                Log.e(TAG, "run: " + bean.toString());
                if (bean != null && bean.getData() != null) {
                    Message obtain = Message.obtain();
                    obtain.obj = bean.getData();
                    mHandler.sendMessage(obtain);
                }

            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mRefreshLayout.finishRefresh();
            mRefreshLayout.finishLoadMore();
            List<FoodBean.DataBean> obj = (List<FoodBean.DataBean>) msg.obj;
            mMyAdatper.addData(obj);
            page++;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyAdatper.clearCache();
    }

}
