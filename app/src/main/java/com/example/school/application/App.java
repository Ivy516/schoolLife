package com.example.school.application;

import android.app.Application;

import io.rong.imkit.RongIM;

public class App extends Application {

    private static App context;
    String appKey="cpj2xarlchkkn";

    public static App getContext(){
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this,appKey);
    }
}
