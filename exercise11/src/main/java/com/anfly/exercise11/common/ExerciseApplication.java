package com.anfly.exercise11.common;

import android.app.Application;

public class ExerciseApplication extends Application {
    private static ExerciseApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static ExerciseApplication getApp() {
        return app;
    }
}
