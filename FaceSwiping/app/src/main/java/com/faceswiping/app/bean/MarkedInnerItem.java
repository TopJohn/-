package com.faceswiping.app.bean;

import java.io.Serializable;

/**
 * Created by John on 15/12/13.
 */
public class MarkedInnerItem implements Serializable {

    private double confidence;

    private String faceImageUrl;

    private String headImageUrl;

    private int id;

    private int isFriends;

    private String userName;


    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getFaceImageUrl() {
        return faceImageUrl;
    }

    public void setFaceImageUrl(String faceImageUrl) {
        this.faceImageUrl = faceImageUrl;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int isFriends() {
        return isFriends;
    }

    public void setIsFriends(int isFriends) {
        this.isFriends = isFriends;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
