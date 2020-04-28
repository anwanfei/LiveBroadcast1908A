package com.anfly.broadcastreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_normal;
    private BigflyReceiver bigflyReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_normal = (Button) findViewById(R.id.btn_normal);

        btn_normal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_normal:
                normal();
                break;
        }
    }

    private void normal() {
        Intent intent;
        intent = new Intent("com.anfly.broadcastreceiver.AnflyReceiver");
        sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取广播对象
        bigflyReceiver = new BigflyReceiver();

        //添加过滤器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.anfly.broadcastreceiver.AnflyReceiver");

        //注册广播
        registerReceiver(bigflyReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(bigflyReceiver);
    }
}
