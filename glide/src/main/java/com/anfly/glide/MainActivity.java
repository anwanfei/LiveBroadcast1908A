package com.anfly.glide;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv1;
    private String localUrl = Environment.getExternalStorageDirectory() + "/Pictures/a.gif";
    private String netUrl = "http://www.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg?setmkt=zh-CN";
    private Button btn_download;
    private Button btn_download_show;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        iv1 = (ImageView) findViewById(R.id.iv1);
        btn_download = (Button) findViewById(R.id.btn_download);
        btn_download.setOnClickListener(this);
        btn_download_show = (Button) findViewById(R.id.btn_download_show);
        btn_download_show.setOnClickListener(this);

//        Glide.with(this).load(localUrl).into(iv1);
        Glide.with(this).load(R.drawable.ic_launcher_background).into(iv1);

        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.drawable.ic_launcher_background);
        requestOptions.error(R.drawable.ic_launcher_background);
        requestOptions.skipMemoryCache(false);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        requestOptions.override(200, 200);
        //圆型图片
//        requestOptions.circleCrop();
        //圆角
        RoundedCorners roundedCorners = new RoundedCorners(8);
        requestOptions.transform(roundedCorners);
        //禁止任何转换
//        requestOptions.dontTransform();

        Glide.with(this)
//                .asGif()
                .load(netUrl)
                .apply(requestOptions)
                .into(iv1);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download:
                download();
                break;
            case R.id.btn_download_show:
                Glide.with(MainActivity.this)
                        .load(file)
                        .into(iv1);
                break;
        }
    }

    private void download() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FutureTarget<File> fileFutureTarget = Glide.with(MainActivity.this)
                        .load(netUrl)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                try {
                    file = fileFutureTarget.get();
                    Log.e("TAG", "file:" + file.getAbsolutePath());

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
