package com.pichui.news.uitil;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pi.core.uikit.bottomdialog.model.Item;
import com.pichui.news.R;
import com.pichui.news.ui.adapter.test.TestEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static TestEntity analysisJsonFile(Context context, String fileName){
        String content = FileUtils.readJsonFile(context,fileName);
        Gson gson = new Gson();
        TestEntity entity = gson.fromJson(content, TestEntity.class);
        return  entity;

    }
    public static List<Item> jsonToShareData(Context context, String fileName) {
        String content = FileUtils.readJsonFile(context,fileName);
        List<Item> datas = new ArrayList<>();
        try {

            JSONArray array = new JSONArray(content);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Item item = new Item();
                if (object.has("id")){
                    item.setId(object.optInt("id"));
                }

                if (object.has("name")){
                    item.setName(object.optString("name"));
                }
                if (object.has("icon")){
                     int resID = R.mipmap.share_qq_solid;
                     switch (object.optString("icon")){
                         case "share_wechat_solid":
                             resID = R.mipmap.share_wechat_solid;
                             break;
                         case "share_weixin_friends_solid":
                             resID = R.mipmap.share_weixin_friends_solid;
                             break;
                         case "share_weibo_solid":
                             resID = R.mipmap.share_weibo_solid;
                             break;
                         case "share_qq_solid":
                             resID = R.mipmap.share_qq_solid;
                             break;

                     }
                    item.setIcon(resID);
                }
                datas.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  datas;

    }
}
