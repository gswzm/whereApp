package com.wzm.baselib.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.wzm.baselib.utils.MD5;
import com.wzm.baselib.utils.StringHelper;
import com.wzm.baselib.utils.aes.AESUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.annotations.NonNull;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.wzm.baselib.net.RetrofitHelper.LOGIN_URL;
import static com.wzm.baselib.net.RetrofitHelper.accessToken;


public class TokenInterceptor implements Interceptor {

    private static final String TAG = "TokenInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF-8"));
        }

        //获取响应体的字符串
        String bodyString = buffer.clone().readString(charset);
        //将字符串解析成bean，然后判断bean里面的字段status
        ResponseModel responseB = new Gson().fromJson(bodyString, ResponseModel.class);
        //根据和服务端的约定判断token过期
        if (isTokenExpired(responseB)) {
            Log.d(TAG, "自动刷新Token,然后重新请求数据");
            //同步请求方式，获取最新的Token
            String newToken = getNewToken();
            accessToken = newToken;

            Request newRequest = request.newBuilder()
                    .header("Authorization", accessToken)
                    .method(request.method(), request.body()).build();
            //重新请求
            return chain.proceed(newRequest);
        }
        return response;
    }

    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(ResponseModel response) {
        if (response.getCode() == 401 || response.getCode() == 500) {//token失效或者token为空都请求登录接口获取新的token
            return true;
        }
        return false;
    }

    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    private String getNewToken() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LOGIN_URL + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //密码md5加密
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", RetrofitHelper.getUserName());
        paramMap.put("password", new MD5().getMD5ofStr(RetrofitHelper.getUserPd()));
        paramMap.put("client_id", "1b8a0531bfaa40ebb4261761b3f7df67");
        paramMap.put("client_secret", "40681dfaf98b4a9a9937c8d2985050c2");

        //参数加密只加密上面四个参数，其中密码先MD5加密，然后再AES加密
        Map<String, Object> encryptedMap = getEncryptedMap(paramMap);
        encryptedMap.put("grant_type", "password");
        encryptedMap.put("scope", "read");

        retrofit2.Response<JsonElement> tokenJson = retrofit.create(LkTokenApi.class).login("", encryptedMap).execute();
        String headerToken = tokenJson.body().toString();
        TokenInfo tokenInfo = new Gson().fromJson(headerToken, TokenInfo.class);
        return tokenInfo.getAccess_token();
    }

    /**
     * 参数加密方法
     *
     * @param paramMap
     * @return
     */
    @NonNull
    private static Map<String, Object> getEncryptedMap(Map<String, Object> paramMap) {
        Map<String, Object> encryptedMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (!StringHelper.convertToString(entry.getValue()).equals("")) {
                encryptedMap.put(entry.getKey(), AESUtils.encrypt(StringHelper.convertToString(entry.getValue()), AESUtils.AUTHCODE));
            } else {
                encryptedMap.put(entry.getKey(), "");
            }
        }
        return encryptedMap;
    }
}
