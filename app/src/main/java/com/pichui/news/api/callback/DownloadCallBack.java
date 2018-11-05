package com.pichui.news.api.callback;



public interface DownloadCallBack {

    void onProgress(int progress);

    void onCompleted();

    void onError(String msg);

}
