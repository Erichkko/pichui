package com.pichui.news.uitil;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class PublicFunction {
    /**
     * 用来判断服务是否运行.
     *
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context context,String className) {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        assert activityManager != null;
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
