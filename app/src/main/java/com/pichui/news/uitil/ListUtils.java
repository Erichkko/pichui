package com.pichui.news.uitil;

import java.util.List;

/**
 * @description: 和List相关的工具方法
 */

public class ListUtils {

    public static boolean isEmpty(List list){
        if (list == null){
            return true;
        }
        return list.size() == 0;
    }
}
