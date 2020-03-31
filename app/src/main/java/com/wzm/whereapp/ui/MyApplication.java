package com.wzm.whereapp.ui;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.wzm.baselib.net.RetrofitHelper;

/**
 * 描述：
 * 创建者： wangzm
 * 时间：2020/3/30
 * 修改者：
 * 时间：
 */
public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitHelper.mapUrl="https://tsapi.amap.com/v1/track/service/";
    }
}
