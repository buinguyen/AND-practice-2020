package com.alan.asm.vnexpressdemo;

import android.app.Application;

import com.alan.asm.vnexpressdemo.utils.SharedPrefs;
import com.facebook.drawee.backends.pipeline.Fresco;

public class VnExpressDemoApp extends Application {

    private static VnExpressDemoApp instance;
    private SharedPrefs sharedPrefs;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        sharedPrefs = new SharedPrefs(getApplicationContext());

        Fresco.initialize(this);
    }

    public static VnExpressDemoApp getInstance() {
        return instance;
    }

    public SharedPrefs getSharedPrefs() {
        return sharedPrefs;
    }
}