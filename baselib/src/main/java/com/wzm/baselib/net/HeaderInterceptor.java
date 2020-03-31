/**
 * Copyright (C), 2015-2018, 甘肃万维有限公司
 * FileName: BaseInterceptor
 * Author:   Administrator
 * Date:     2018/11/6 9:52
 * Description: 拦截器
 */
package com.wzm.baselib.net;


import com.wzm.baselib.utils.StringHelper;
import com.wzm.baselib.utils.aes.AESUtils;

import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 〈拦截器〉
 *
 * @author kangjh
 * @create 2019/07/16
 */
public class HeaderInterceptor implements Interceptor {

    private static boolean isGetExclude(String path) {
        if (path.indexOf("reverse_geocoding/v3") != -1 || path.indexOf("client/getClientInfo")!=-1) {
            return true;
        }
        return false;
    }


    private static boolean isExclude(String path) {
        return RetrofitHelper.LOGIN_URL.indexOf(path) != -1;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request().newBuilder()
                .header("Authorization", RetrofitHelper.getAccessToken())
                .build();


        if (request.method().equals("GET")) {
            String url = request.url().toString();
            if (url.contains("?")) {
                String basePath = url.substring(0, url.indexOf("?"));
                String params = url.substring(url.indexOf("?") + 1);
                String[] parameterArr = params.split("&");
                HttpUrl.Builder newBuilder = HttpUrl.parse(basePath).newBuilder();
                for (String parm : parameterArr) {
                    if (StringHelper.isNotBlank(parm)) {
                        String[] keys = parm.split("=");
                        if (keys.length > 1) {
                            //拦截器拿到的汉字，已经被编过码了,所以要转回去
                            String result= URLDecoder.decode(keys[1],"utf-8");
                            if (isGetExclude(url)) {
                                newBuilder.addEncodedQueryParameter(keys[0], result);
                            } else {
                                String value = AESUtils.encrypt(StringHelper.convertToString(result), AESUtils.AUTHCODE);
                                newBuilder.addQueryParameter(keys[0], value);
                            }
                        } else {
                            newBuilder.addQueryParameter(keys[0], "");
                        }
                    }
                }
                request = request.newBuilder().url(newBuilder.build()).build();
            }


        } else {
            String url = request.url().url().getPath();
            //参数就要针对body做操作,我这里针对From表单做操作
            if (request.body() instanceof FormBody) {
                // 构造新的请求表单
                FormBody.Builder builder = new FormBody.Builder();
                FormBody body = (FormBody) request.body();
                //将以前的参数添加
                for (int i = 0; i < body.size(); i++) {
                    String value = URLDecoder.decode(body.encodedValue(i));
                    if (!isExclude(url) && StringHelper.isNotBlank(value)) {
                        builder.add(body.encodedName(i), AESUtils.encrypt(value, AESUtils.AUTHCODE));
                    } else {
                        builder.add(body.encodedName(i), value);
                    }
                }
                request = request.newBuilder().post(builder.build()).build();//构造新的请求体
            }

        }
        Response response = chain.proceed(request);
        return response;
    }


}
