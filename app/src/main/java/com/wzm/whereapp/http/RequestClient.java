package com.wzm.whereapp.http;

import android.util.JsonReader;

import com.google.gson.JsonElement;
import com.wzm.baselib.net.ActivityLifeCycleEvent;
import com.wzm.baselib.net.HttpCall;
import com.wzm.baselib.net.ResponseModel;
import com.wzm.baselib.net.RetrofitHelper;
import com.wzm.baselib.net.netlistener.CallBackLis;
import com.wzm.whereapp.util.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * 描述：
 * 创建者： wangzm
 * 时间：2020/3/30
 * 修改者：
 * 时间：
 */
public class RequestClient {


    public static void getService(String name, PublishSubject<ActivityLifeCycleEvent> lifeCycleSubject, CallBackLis<JsonElement> callBackLis) {
        Map<String, Object> params = new HashMap<>();
        params.put("key", Constants.GD_WEB_KEY);
        params.put("name", name);
        Observable<ResponseModel<JsonElement>> observable = getUrlApi().getService(params);
        HttpCall.doCall(observable, callBackLis, null, lifeCycleSubject);
    }

    public static UrlApi getUrlApi() {
        return RetrofitHelper.getInstance().getMapRetrofit().create(UrlApi.class);
    }
}
