package com.faceswiping.app.api;

import android.content.Context;
import android.util.Log;

import com.faceswiping.app.AppContext;
import com.faceswiping.app.util.TLog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpEntity;
import org.apache.http.client.params.ClientPNames;

import java.util.Locale;


public class ApiHttpClient {

    public final static String HOST = "115.159.152.203:8080";
    private static String API_URL = "http://115.159.152.203:8080/%s";

//    public final static String HOST = "172.20.10.4:8080";
//    private static String API_URL = "http://172.20.10.4:8080/%s";

//    public final static String HOST = "10.73.198.56:8080";
//    private static String API_URL = "http://10.73.198.56:8080/%s";

//    public final static String HOST = "120.26.53.217";
//    private static String API_URL = "http://120.26.53.217/api/v1.0/%s";

    // private static String API_URL = "http://www.inyouzi.com:5000/api/v1.0/%s";


//    public final static String HOST = "192.168.31.150:5000";
//    private static String API_URL = "http://192.168.31.150:5000/api/v1.0/%s";

//    public final static String HOST = "192.168.11.101:5000";
//    private static String API_URL = "http://192.168.11.101:5000/api/v1.0/%s";

    public static final String DELETE = "DELETE";
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static AsyncHttpClient client;

    public ApiHttpClient() {
    }

    public static AsyncHttpClient getHttpClient() {
        return client;
    }

    public static void cancelAll(Context context) {
        client.cancelRequests(context, true);
    }

    public static void clearUserCookies(Context context) {
        // (new HttpClientCookieStore(context)).a();
    }

    public static void delete(String partUrl, AsyncHttpResponseHandler handler) {
        client.delete(getAbsoluteApiUrl(partUrl), handler);
        log(new StringBuilder("DELETE ").append(partUrl).toString());
    }

    public static void get(String partUrl, AsyncHttpResponseHandler handler) {
        client.get(getAbsoluteApiUrl(partUrl), handler);
        log(new StringBuilder("GET ").append(partUrl).toString());
    }

    public static void get(String partUrl, RequestParams params,
                           AsyncHttpResponseHandler handler) {
        client.get(getAbsoluteApiUrl(partUrl), params, handler);
        log(new StringBuilder("GET ").append(partUrl).append("&")
                .append(params).toString());
    }

    public static String getAbsoluteApiUrl(String partUrl) {
        String url = String.format(API_URL, partUrl);
        Log.d("BASE_CLIENT", "request:" + url);
        return url;
    }

    public static String getApiUrl() {
        return API_URL;
    }

    public static void getDirect(String url, AsyncHttpResponseHandler handler) {
        client.get(url, handler);
        log(new StringBuilder("GET ").append(url).toString());
    }

    public static void log(String log) {
        Log.d("BaseApi", log);
        TLog.log("Test", log);
    }

    public static void post(String partUrl, AsyncHttpResponseHandler handler) {
        client.post(getAbsoluteApiUrl(partUrl), handler);
        log(new StringBuilder("POST ").append(partUrl).toString());
    }

    public static void post(String partUrl, RequestParams params,
                            AsyncHttpResponseHandler handler) {
        client.post(getAbsoluteApiUrl(partUrl), params, handler);
        log(new StringBuilder("POST ").append(partUrl).append("&")
                .append(params).toString());
    }

    public static void postDirect(String url, RequestParams params,
                                  AsyncHttpResponseHandler handler) {
        client.post(url, params, handler);
        log(new StringBuilder("POST ").append(url).append("&").append(params)
                .toString());
    }

    public static void post(String partUrl, HttpEntity httpEntity, String contentType, AsyncHttpResponseHandler handler) {
        client.post(null, getAbsoluteApiUrl(partUrl), httpEntity, contentType, handler);
        log(new StringBuilder("POST ").append(partUrl).toString());
    }


    public static void put(String partUrl, AsyncHttpResponseHandler handler) {
        client.put(getAbsoluteApiUrl(partUrl), handler);
        log(new StringBuilder("PUT ").append(partUrl).toString());
    }

    public static void put(String partUrl, RequestParams params,
                           AsyncHttpResponseHandler handler) {
        client.put(getAbsoluteApiUrl(partUrl), params, handler);
        log(new StringBuilder("PUT ").append(partUrl).append("&")
                .append(params).toString());
    }

    public static void put(String partUrl, HttpEntity httpEntity, String contentType, AsyncHttpResponseHandler handler) {
        client.put(null, getAbsoluteApiUrl(partUrl), httpEntity, contentType, handler);
        log(new StringBuilder("PUT ").append(partUrl).toString());
    }

    public static void setApiUrl(String apiUrl) {
        API_URL = apiUrl;
    }

    public static void setHttpClient(AsyncHttpClient c) {
        client = c;
        client.addHeader("Accept-Language", Locale.getDefault().toString());
        client.addHeader("Host", HOST);
        client.addHeader("Connection", "Keep-Alive");
        //client.addHeader("Content-Type","application/json");
        client.getHttpClient().getParams()
                .setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);

        setUserAgent(ApiClientHelper.getUserAgent(AppContext.getInstance()));
    }

    public static void setUserAgent(String userAgent) {
        client.setUserAgent(userAgent);
    }

    public static void setCookie(String cookie) {
        client.addHeader("Cookie", cookie);
    }

    private static String appCookie;

    public static void cleanCookie() {
        appCookie = "";
    }

    public static String getCookie(AppContext appContext) {
        if (appCookie == null || appCookie == "") {
            appCookie = appContext.getProperty("cookie");
        }
        return appCookie;
    }


}
