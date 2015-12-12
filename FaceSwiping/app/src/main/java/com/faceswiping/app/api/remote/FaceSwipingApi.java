package com.faceswiping.app.api.remote;

import com.faceswiping.app.api.ApiHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;


public class FaceSwipingApi {

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @param handler
     */
    public static void login(String username, String password,
                             AsyncHttpResponseHandler handler) {

        String url = "api/login";

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {

            jsonObject.put("username", username);
            jsonObject.put("password", password);

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.post(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //绑定推送ClientId
    public static void sendClientId(String clientId, AsyncHttpResponseHandler handler) {
        String url = "push/bind";
        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {
            jsonObject.put("push_id", clientId);
            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
            ApiHttpClient.post(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
