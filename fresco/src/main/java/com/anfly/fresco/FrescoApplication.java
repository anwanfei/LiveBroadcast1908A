package com.anfly.fresco;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class FrescoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化fresco，一需要一次即可
        Fresco.initialize(this);
    }
}
