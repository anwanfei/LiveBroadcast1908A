package com.anfly.greendao;

import android.app.Application;

public class GreenDaoApplication extends Application {
    private static GreenDaoApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static GreenDaoApplication getApp() {
        return app;
    }
}
