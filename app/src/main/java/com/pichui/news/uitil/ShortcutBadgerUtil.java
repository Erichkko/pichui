package com.pichui.news.uitil;

import android.content.Context;

import me.leolin.shortcutbadger.ShortcutBadger;

public class ShortcutBadgerUtil {
    public static void setBadgeCount(Context context, int count) {
        int badgeCount = 1;
        ShortcutBadger.applyCount(context, badgeCount); //1.1.4版本添加数字“徽章”的方法
    }

    public static void removeBadgeCount(Context context, int count) {

        ShortcutBadger.applyCount(context, count); //for 1.1.4
    }

    public static void clearBadgeCount(Context context) {

        ShortcutBadger.removeCount(context); //1.1.4版本删除数字“徽章”的方法
    }
}
