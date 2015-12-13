package com.faceswiping.app.bean;

import java.io.Serializable;

/**
 * Created by John on 15/12/11.
 */
public class User implements Serializable {

    private int id;

    private String userName;

    private String password;

    private String nickName;

    private String headImageUrl;

    private String certificationImageUrl;

    private String introduction;

    //0未开启，1是已开启
    private int secret;

    //0未认证，1是已认证
    private int certification;

    private String token;

    public String getCertificationImageUrl() {
        return certificationImageUrl;
    }

    public void setCertificationImageUrl(String certificationImageUrl) {
        this.certificationImageUrl = certificationImageUrl;
    }

    public int getCertification() {
        return certification;
    }

    public void setCertification(int certification) {
        this.certification = certification;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getSecret() {
        return secret;
    }

    public void setSecret(int secret) {
        this.secret = secret;
    }
}
