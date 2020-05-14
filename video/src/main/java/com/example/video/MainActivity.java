package com.example.video;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_system;
    private String[] videos;
    private VideoView vv;
    private Button btn_vv;
    private int i = 0;
    private Button btn_mp;
    private Button btn_retriever;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
        initView();
        videos = new String[]{Environment.getExternalStorageDirectory() + "/360/a.mp4", Environment.getExternalStorageDirectory() + "/360/b.mp4"};
    }

    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        btn_system = (Button) findViewById(R.id.btn_system);
        btn_system.setOnClickListener(this);
        vv = (VideoView) findViewById(R.id.vv);


        btn_vv = (Button) findViewById(R.id.btn_vv);
        btn_vv.setOnClickListener(this);
        btn_mp = (Button) findViewById(R.id.btn_mp);
        btn_mp.setOnClickListener(this);
        btn_retriever = (Button) findViewById(R.id.btn_retriever);
        btn_retriever.setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_system:
                system();
                break;
            case R.id.btn_vv:
                vv();
                break;
            case R.id.btn_mp:
                Intent intent = new Intent(this, MPActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_retriever:
                retriever();
                break;
        }
    }

    private void retriever() {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videos[0]);
        Bitmap frameAtTime = retriever.getFrameAtTime(7000*1000);
        iv.setImageBitmap(frameAtTime);
    }

    private void vv() {
        MediaController mediaController = new MediaController(this);
        vv.setMediaController(mediaController);
        vvPaly();
        mediaController.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //下一个
                i++;
                if (i > videos.length - 1) {
                    i = 0;
                }
                vvPaly();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上一个
                i--;
                if (i < 0) {
                    i = videos.length - 1;
                }
                vvPaly();
            }
        });

    }

    private void vvPaly() {
        vv.setVideoPath(videos[i]);
        vv.start();
    }

    private void system() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(videos[0]), "video/mp4");
        startActivity(intent);
    }
}
