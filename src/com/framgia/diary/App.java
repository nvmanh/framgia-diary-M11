package com.framgia.diary;

import com.orm.SugarContext;

import android.app.Application;

public class App extends Application {
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
        instance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
