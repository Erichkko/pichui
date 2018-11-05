package com.pichui.news.uitil;

import android.content.Context;

import com.google.gson.Gson;
import com.pichui.news.ui.adapter.test.TestEntity;

public class JsonUtils {

    public static TestEntity analysisJsonFile(Context context, String fileName){
        String content = FileUtils.readJsonFile(context,fileName);
        Gson gson = new Gson();
        TestEntity entity = gson.fromJson(content, TestEntity.class);
        return  entity;

    }
}
