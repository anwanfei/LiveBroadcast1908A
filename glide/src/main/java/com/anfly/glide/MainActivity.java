package com.anfly.glide;

import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends AppCompatActivity {

    private ImageView iv1;
    private String localUrl = Environment.getExternalStorageDirectory() + "/Pictures/a.gif";
    private String netUrl = "http://www.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg?setmkt=zh-CN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        iv1 = (ImageView) findViewById(R.id.iv1);
//        Glide.with(this).load(localUrl).into(iv1);
        Glide.with(this).load(R.drawable.ic_launcher_background).into(iv1);

        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.drawable.ic_launcher_background);
        requestOptions.error(R.drawable.ic_launcher_background);
        requestOptions.skipMemoryCache(false);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        requestOptions.override(100,100);

        Glide.with(this)
                .load(netUrl)
                .apply(requestOptions)
                .into(iv1);
    }
}
