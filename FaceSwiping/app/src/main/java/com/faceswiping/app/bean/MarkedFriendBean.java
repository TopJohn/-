package com.faceswiping.app.bean;

import java.io.Serializable;

/**
 * Created by wanglin on 15/12/13.
 */
public class MarkedFriendBean implements Serializable {

    private String name;

    private String headImageUrl;

    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
