package com.anfly.collectionmvp.app;

import android.app.Application;

public class CollectionApplication extends Application {
    private static CollectionApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static CollectionApplication getApp() {
        return app;
    }
}
