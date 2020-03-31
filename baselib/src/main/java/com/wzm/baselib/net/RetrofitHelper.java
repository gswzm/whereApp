package com.wzm.baselib.net;

import android.text.TextUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitHelper {

    static String accessToken;
    public static String applicationId;
    private static String userName;
    private static String userPd;
    public static String loginName;


    public static String LOGIN_URL;

    private static volatile RetrofitHelper instance;
    public static String authUrl="";
    public static String baseUrl="";
    public static String mapUrl="";
    private  Retrofit retrofit;

    private  Retrofit authRetrofit;

    private final Retrofit mapRetrofit;


    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        RetrofitHelper.accessToken = accessToken;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        RetrofitHelper.userName = userName;
    }

    public static String getUserPd() {
        return userPd;
    }

    public static void setUserPd(String userPd) {
        RetrofitHelper.userPd = userPd;
    }


    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Retrofit getAuthRetrofit() {
        return authRetrofit;
    }

    public Retrofit getMapRetrofit() {
        return mapRetrofit;
    }

    private RetrofitHelper() {


        //日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //构建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
//                .addInterceptor(getHeaderInterceptor())
//                .addInterceptor(new HeaderInterceptor())
//                .addInterceptor(new TokenInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)//超时时间15S
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        ;
        //构建并返回retrofit对象
//        retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .client(okHttpClient)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
        // 认证服务retrofit对象
//        authRetrofit = new Retrofit.Builder()
//                .baseUrl(authUrl)
//                .client(okHttpClient)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();

        // 文件下载上传retrofit对象
        mapRetrofit = new Retrofit.Builder()
                .baseUrl(mapUrl)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    @NonNull
    private Interceptor getHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                //判断是不是登录验证接口
                String url = originalRequest.url().toString();
                if (TextUtils.equals(url, LOGIN_URL)) {
                    return chain.proceed(originalRequest);
                }
                Request request = originalRequest.newBuilder()
                        .header("Authorization", getAccessToken())
                        .method(originalRequest.method(), originalRequest.body()).build();
                return chain.proceed(request);
            }
        };
    }


    /**
     * 添加公用的 query 参数
     */
    @NonNull
    private Interceptor getRequestInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();

                // 添加新的参数
                HttpUrl.Builder authorizedUrlBuilder = originalRequest.url()
                        .newBuilder()
                        .scheme(originalRequest.url().scheme())
                        .host(originalRequest.url().host())
                        /*.addQueryParameter("key", "value")
                         */;

                // 新的请求
                Request newRequest = originalRequest.newBuilder()
                        .method(originalRequest.method(), originalRequest.body())
                        .url(authorizedUrlBuilder.build())
                        .build();

                return chain.proceed(newRequest);
            }
        };
    }
}

