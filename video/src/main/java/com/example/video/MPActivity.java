package com.example.video;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MPActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_start;
    private Button btn_pause;
    private Button btn_stop;
    private Button btn_goon;
    private SurfaceView sf;
    private String[] videos;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_p);
        videos = new String[]{Environment.getExternalStorageDirectory() + "/360/a.mp4", Environment.getExternalStorageDirectory() + "/360/b.mp4"};
        initView();
    }

    private void initView() {
        mp = new MediaPlayer();
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_goon = (Button) findViewById(R.id.btn_goon);
        sf = (SurfaceView) findViewById(R.id.sf);

        btn_start.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_goon.setOnClickListener(this);

        SurfaceHolder holder = sf.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mp.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                play();
                break;
            case R.id.btn_pause:
                mp.pause();
                break;
            case R.id.btn_stop:
                mp.stop();
                break;
            case R.id.btn_goon:
                mp.start();
                break;
        }
    }

    private void play() {
        try {
            mp.reset();
            mp.setDataSource(videos[1]);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
