package com.pichui.news.ui.adapter.test;

import java.util.ArrayList;



public class TestEntity {

    public ArrayList<TagsEntity> allTagsList;

    public class TagsEntity {
        public String tagsName;
        public ArrayList<TagInfo> tagInfoList;

        public class TagInfo {
            public String tagName;
        }
    }

}
