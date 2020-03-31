package com.wzm.baselib.net.netlistener;

public interface FileCallBackLis {

    void onSuccess(String info);

    void onFailure(String error);

    void onProgress(int progress);
}
