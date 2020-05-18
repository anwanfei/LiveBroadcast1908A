package com.example.finalproject.common;

import android.app.Application;

public class FinalApplication extends Application {
    private static FinalApplication app;

    public static FinalApplication getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        app = this;
        super.onCreate();
    }
}
