package com.example.finalproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.example.finalproject.adapter.SplashAdapter;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager vp_splash;
    private Button btn_jump;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initListener();
    }

    private void initListener() {
        vp_splash.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == list.size() - 1) {
                    btn_jump.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        vp_splash = (ViewPager) findViewById(R.id.vp_splash);
        btn_jump = (Button) findViewById(R.id.btn_jump);
        btn_jump.setOnClickListener(this);

        list = new ArrayList<>();
        list.add("欢迎来到北京");
        list.add("欢迎来到积云教育");
        list.add("欢迎来到1908A");

        vp_splash.setAdapter(new SplashAdapter(this, list));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jump:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
