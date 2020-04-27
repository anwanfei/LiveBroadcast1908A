package com.anfly.livebroadcast1908a;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class RedPackageActivity extends AppCompatActivity {

    private FallingSurfaceView red_package;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_package);
        initView();
    }

    private void initView() {
        red_package = (FallingSurfaceView) findViewById(R.id.red_package);
        red_package.run();

    }
}
