package com.wzm.baselib.net;

import com.google.gson.JsonElement;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface LkTokenApi {
    @FormUrlEncoded
    @POST
    Call<JsonElement> login(@Url String url, @FieldMap Map<String, Object> paramMap);
}
