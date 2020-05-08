package com.anfly.fresco;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;

public class MainActivity extends AppCompatActivity {

    private String netUrl = "http://www.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg?setmkt=zh-CN";
    private SimpleDraweeView sdv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        sdv = (SimpleDraweeView) findViewById(R.id.sdv);
        sdv.setImageURI(netUrl);
    }
}
