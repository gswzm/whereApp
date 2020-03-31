package com.wzm.baselib.net.download;




import com.wzm.baselib.net.netlistener.FileCallBackLis;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class DownloadInterceptor implements Interceptor {

    private FileCallBackLis callBackLis;

    public DownloadInterceptor(FileCallBackLis callBackLis) {
        this.callBackLis = callBackLis;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());

        return originalResponse.newBuilder()
                .body(new DownloadResponseBody(originalResponse.body(), callBackLis))
                .build();
    }
}
