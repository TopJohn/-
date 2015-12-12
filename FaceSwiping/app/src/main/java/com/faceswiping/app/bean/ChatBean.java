package com.faceswiping.app.bean;

import java.io.Serializable;

/**
 * Created by John on 15/12/12.
 */
public class ChatBean implements Serializable {

    private String name;

    private String headImageUrl;

    private String content;

    private String date;


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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
