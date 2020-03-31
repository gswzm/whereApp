package com.wzm.whereapp.http;

import com.google.gson.JsonElement;
import com.wzm.baselib.net.ResponseModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 描述：
 * 创建者： wangzm
 * 时间：2020/3/30
 * 修改者：
 * 时间：
 */
public interface UrlApi {

    @FormUrlEncoded
    @POST("add")
    Observable<ResponseModel<JsonElement>> getService(@FieldMap Map<String,Object> map);

}
