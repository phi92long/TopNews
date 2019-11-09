package com.fisfam.topnews.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TopicList {
    private List<Topic> mTopics = new ArrayList<>();

    public TopicList(List<Topic> topics) {
        topics = topics;
    }

    public class Topic {
        private long id = -1;
        private String name = "";
        private String icon = "";
        private String color = "";
        private long priority = -1;
        private long featured = 0;
        private long created_at = -1;
        private long last_update = -1;

        public Topic(long id, String name) {
            id = id;
            name = name;
        }

    }
}
