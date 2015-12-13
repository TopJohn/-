package com.faceswiping.app.api.remote;

import com.faceswiping.app.api.ApiHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
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
        String url = "user/getui/" + clientId;

        ApiHttpClient.post(url, handler);

    }

    public static void updateUserProtrait(String[] keys, AsyncHttpResponseHandler handler) {

        String url = "user/faces";

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {

            JSONArray jsonArray = new JSONArray();

            for (String key : keys) {
                jsonArray.put(key);
            }

            jsonObject.put("keys", jsonArray);

            System.out.println(jsonObject);

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.put(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //获取七牛上传证书
    public static void getQiniuToken(AsyncHttpResponseHandler handler) {
        String url = "user/qiniu/token";

        ApiHttpClient.get(url, handler);

    }


    public static void updateSecret(int secret, AsyncHttpResponseHandler handler) {

        String url = "user/secret/" + secret;

        ApiHttpClient.put(url, handler);

    }

    public static void getFriends(AsyncHttpResponseHandler handler) {

        String url = "user/secret/";

        ApiHttpClient.get(url, handler);

    }

    public static void getMyInform(AsyncHttpResponseHandler handler) {

        String url = "user/secret/";

        ApiHttpClient.get(url, handler);

    }

}
