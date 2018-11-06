package com.pichui.news.ui.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;


import com.pi.core.util.DebugLog;
import com.pichui.news.R;
import com.pichui.news.api.callback.DownloadCallBack;
import com.pichui.news.api.uploadAndDown.FileManager;
import com.pichui.news.app.Constant;
import com.pichui.news.uitil.PreUtils;

import java.io.File;
import java.util.Objects;


/**
 * 创建时间：2018/3/7
 * 编写人：damon
 * 功能描述 ：
 */

public class DownloadIntentService extends IntentService {

    private NotificationManager mNotifyManager;
    private String mDownloadFileName ;
    private String downloadUrl;
    private int downloadId;
    private Notification mNotification;

    public DownloadIntentService() {
        super("InitializeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

       downloadUrl = Objects.requireNonNull(intent.getExtras()).getString("download_url");

       downloadId = Objects.requireNonNull(intent.getExtras()).getInt("download_id");

       mDownloadFileName = intent.getExtras().getString("download_file");

        DebugLog.d( "download_url --" + downloadUrl);
        DebugLog.d( "download_id --" + downloadId);
        DebugLog.d( "download_file --" + mDownloadFileName);

        final File file = new File(Constant.APP_ROOT_PATH + Constant.DOWNLOAD_DIR + mDownloadFileName);
        long range = 0;
        int progress = 0;
        if (file.exists()) {
            range = PreUtils.getLong(downloadUrl, 0);
            progress = (int) (range * 100 / file.length());
            if (range == file.length()) {
                installApp(file);
                return;
            }
        }

        DebugLog.d("range = " + range);

        final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify_download);
        remoteViews.setProgressBar(R.id.pb_progress, 100, progress, false);
        remoteViews.setTextViewText(R.id.tv_progress, "已下载" + progress + "%");

        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setContent(remoteViews)
                        .setTicker("正在下载")
                        .setSmallIcon(R.mipmap.icon);

        mNotification = builder.build();

        mNotifyManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyManager.notify(downloadId, mNotification);

        FileManager.downloadFile(range, downloadUrl, mDownloadFileName, new DownloadCallBack() {
            @Override
            public void onProgress(int progress) {
                remoteViews.setProgressBar(R.id.pb_progress, 100, progress, false);
                remoteViews.setTextViewText(R.id.tv_progress, "已下载" + progress + "%");
                mNotifyManager.notify(downloadId, mNotification);
            }

            @Override
            public void onCompleted() {
                DebugLog.d( "下载完成");
                mNotifyManager.cancel(downloadId);
                installApp(file);
            }

            @Override
            public void onError(String msg) {
                mNotifyManager.cancel(downloadId);
                DebugLog.d("下载发生错误--" + msg);
            }
        });
    }

    private void installApp(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

}
