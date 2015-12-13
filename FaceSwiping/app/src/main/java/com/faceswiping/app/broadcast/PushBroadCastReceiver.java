package com.faceswiping.app.broadcast;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.faceswiping.app.AppConfig;
import com.faceswiping.app.AppContext;
import com.faceswiping.app.R;
import com.faceswiping.app.activity.MainActivity;
import com.faceswiping.app.api.remote.FaceSwipingApi;
import com.faceswiping.app.util.StringUtils;
import com.igexin.sdk.PushConsts;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;


public class PushBroadCastReceiver extends BroadcastReceiver {


    private static final int NOTIFY_ID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();


        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                // String appid = bundle.getString("appid");

                // String taskid = bundle.getString("taskid");
                // String messageid = bundle.getString("messageid");

                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                // boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
                // System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));

                byte[] payload = bundle.getByteArray("payload");

                String sticker = "您收到了一个好友请求～！";

                String contentTitle = "好友请求";

                String contentText = new String(payload);

                System.out.println(contentText);


                Intent notifyIntent = new Intent(context, MainActivity.class);
                notifyIntent.putExtra("NOTICE", true);
                PendingIntent pi = PendingIntent.getActivity(context, 1000, notifyIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(
                        context).setTicker(sticker).setContentTitle(contentTitle)
                        .setContentText(contentText).setAutoCancel(true)
                        .setContentIntent(pi).setSmallIcon(R.drawable.ic_notification);

                if (AppContext.get(AppConfig.KEY_NOTIFICATION_SOUND, true)) {
                    builder.setSound(Uri.parse("android.resource://"
                            + AppContext.getInstance().getPackageName() + "/"
                            + R.raw.notificationsound));
                }
                if (AppContext.get(AppConfig.KEY_NOTIFICATION_VIBRATION, true)) {
                    long[] vibrate = {0, 10, 20, 30};
                    builder.setVibrate(vibrate);
                }

                Notification notification = builder.build();

                NotificationManagerCompat.from(context).notify(
                        NOTIFY_ID, notification);

                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String clientId = bundle.getString("clientid");

                if (!StringUtils.isEmpty(clientId)) {

                    AppContext.getInstance().setProperty("user.clientId", clientId);

                    FaceSwipingApi.sendClientId(clientId, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            AppContext.showToast("绑定成功～～！！");

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            AppContext.showToast("绑定失败～～！！");

                        }
                    });

                }



                break;

            case PushConsts.THIRDPART_FEEDBACK:
                /*
                 * String appid = bundle.getString("appid"); String taskid =
                 * bundle.getString("taskid"); String actionid = bundle.getString("actionid");
                 * String result = bundle.getString("result"); long timestamp =
                 * bundle.getLong("timestamp");
                 *
                 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo", "taskid = " +
                 * taskid); Log.d("GetuiSdkDemo", "actionid = " + actionid); Log.d("GetuiSdkDemo",
                 * "result = " + result); Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
                 */
                break;

            default:
                break;
        }
    }
}
