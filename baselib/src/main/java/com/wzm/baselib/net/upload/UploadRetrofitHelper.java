package com.wzm.baselib.net.upload;

import android.os.Handler;
import android.os.Looper;


import com.wzm.baselib.manager.ThreadPoolManager;
import com.wzm.baselib.net.netlistener.FileCallBackLis;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 上传文件帮助类 单例
 */
public class UploadRetrofitHelper {

    private static volatile UploadRetrofitHelper instance;

    private Retrofit retrofit;
    public static String upLoadUrl;

    private UploadRetrofitHelper() {

    }

    public static UploadRetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (UploadRetrofitHelper.class) {
                if (instance == null) {
                    instance = new UploadRetrofitHelper();
                }
            }
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            //日志拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(upLoadUrl)
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    /**
     * 上传单个文件
     *
     * @param url
     * @param file
     * @param callBackLis
     */
    public void uploadFile(String url, File file, final FileCallBackLis callBackLis) {
        UploadRequestBody requestBody = new UploadRequestBody(file, callBackLis);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        final Call<ResponseBody> call = getRetrofit().create(UploadApi.class).uploadFile(url, part);
        httpCall(callBackLis, call);
    }

    /**
     * 上传单个文件附带信息
     *
     * @param url
     * @param file
     * @param info
     */
    public void uploadFileWithInfo(String url, File file, Map<String, String> info, final FileCallBackLis callBackLis) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        UploadRequestBody requestBody = new UploadRequestBody(file, callBackLis);
        builder.addFormDataPart("file", file.getName(), requestBody);
        //遍历Map 键值对
        for (Map.Entry<String, String> entry : info.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        MultipartBody body = builder.build();
        final Call<ResponseBody> call = getRetrofit().create(UploadApi.class).uploadFileWithInfo(url, body);
        httpCall(callBackLis, call);

    }

    /**
     * 多文件上传
     *
     * @param url
     * @param files
     * @param callBackLis
     */
    public void uploadFiles(String url, List<File> files, final FileCallBackLis callBackLis) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //添加附件
        for (File file : files) {
            UploadRequestBody requestBody = new UploadRequestBody(file, callBackLis);
            //TODO "file" 要和服务端匹配
            builder.addFormDataPart("file", file.getName(), requestBody);
        }
        MultipartBody body = builder.build();
        final Call<ResponseBody> call = getRetrofit().create(UploadApi.class).uploadFiles(url, body);
        httpCall(callBackLis, call);
    }

    /**
     * 上传多文件附带信息
     *
     * @param url
     * @param files
     * @param info
     * @param callBackLis
     */
    public void uploadFilesWithInfo(String url, List<File> files, Map<String, String> info, final FileCallBackLis callBackLis) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //添加附件
        for (File file : files) {
            UploadRequestBody requestBody = new UploadRequestBody(file, callBackLis);
            //TODO "file" 要和服务端匹配
            builder.addFormDataPart("file", file.getName(), requestBody);
        }
        //添加信息
        for (Map.Entry<String, String> entry : info.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        MultipartBody body = builder.build();
        final Call<ResponseBody> call = getRetrofit().create(UploadApi.class).uploadFilesWithInfo(url, body);
        httpCall(callBackLis, call);
    }

    private void httpCall(final FileCallBackLis callBackLis, final Call<ResponseBody> call) {
        //线程池开启新线程
        ThreadPoolManager.getInstance().execute(() -> {
            try {
                Response<ResponseBody> execute = call.execute();
                if (execute.isSuccessful()) {
                    new Handler(Looper.getMainLooper()).post(() -> callBackLis.onSuccess("上传成功！"));
                } else {
                    new Handler(Looper.getMainLooper()).post(() -> callBackLis.onFailure("网络异常！"));
                }
            } catch (IOException e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() -> callBackLis.onFailure("上传失败！"));
            }
        });
    }
}
