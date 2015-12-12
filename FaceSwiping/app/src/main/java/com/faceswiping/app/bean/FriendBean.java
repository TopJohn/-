package com.faceswiping.app.bean;

import java.io.Serializable;

/**
 * Created by John on 15/12/13.
 */
public class FriendBean implements Serializable {

    private String name;


    private String headerImageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public void setHeaderImageUrl(String headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
    }
}
