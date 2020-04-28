package com.anfly.broadcastreceiver;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_normal;
    private BigflyReceiver bigflyReceiver;
    private NetReceiver netReceiver;
    private Button btn_ordered;
    private BigflyTwoReceiver bigflyTwoReceiver;
    private Button btn_local;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_normal = (Button) findViewById(R.id.btn_normal);

        btn_normal.setOnClickListener(this);
        btn_ordered = (Button) findViewById(R.id.btn_ordered);
        btn_ordered.setOnClickListener(this);
        btn_local = (Button) findViewById(R.id.btn_local);
        btn_local.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_normal:
                normal();
                break;
            case R.id.btn_ordered:
                ordered();
                break;
            case R.id.btn_local:
                local();
                break;
        }
    }

    private void local() {
        Intent intent = new Intent("com.anfly.broadcastreceiver.LocalReceiver");
        localBroadcastManager.sendBroadcast(intent);
    }

    private void ordered() {
        Intent intent = new Intent("com.anfly.broadcastreceiver.AnflyReceiver");
        sendOrderedBroadcast(intent, null);
    }

    private void normal() {
        Intent intent;
        intent = new Intent("com.anfly.broadcastreceiver.AnflyReceiver");
        intent.setComponent(new ComponentName(this, AnflyReceiver.class));
        sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        //获取广播对象
        bigflyReceiver = new BigflyReceiver();
        bigflyTwoReceiver = new BigflyTwoReceiver();
        netReceiver = new NetReceiver();
        localReceiver = new LocalReceiver();

        //添加过滤器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.anfly.broadcastreceiver.AnflyReceiver");
        intentFilter.setPriority(100);

        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.anfly.broadcastreceiver.AnflyReceiver");
        intentFilter2.setPriority(50);

        IntentFilter intentFilter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");

        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.anfly.broadcastreceiver.LocalReceiver");

        //注册广播
        registerReceiver(bigflyReceiver, intentFilter);
        registerReceiver(netReceiver, intentFilter1);
        registerReceiver(bigflyTwoReceiver, intentFilter2);
        localBroadcastManager.registerReceiver(localReceiver, intentFilter3);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(bigflyReceiver);
        unregisterReceiver(bigflyTwoReceiver);
        unregisterReceiver(netReceiver);
    }
}
