package com.faceswiping.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.faceswiping.app.activity.MainActivity;
import com.faceswiping.app.interf.BaseViewInterface;
import com.faceswiping.app.util.TDevice;
import com.faceswiping.app.util.UIHelper;

import org.kymjs.kjframe.utils.PreferenceHelper;

/**
 * 应用启动界面
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年12月22日 上午11:51:56
 */
public class AppStart extends Activity implements BaseViewInterface {


//    private AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
//        @Override
//        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//
//            String response = new String(responseBody);
//
//            try {
//
//                Result<String> result = new Gson().fromJson(response, new TypeToken<Result<String>>() {
//                }.getType());
//
//                if (result.getStatus().equals("ok")) {
//
//                    AppContext.getInstance().updateToken(result.getData());
//                   // AppContext.showToastShort("更新Token成功～！");
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                onFailure(statusCode, headers, responseBody, e);
//            }
//
//        }
//
//        @Override
//        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//        }
//
//
//    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 防止第三方跳转时出现双实例
        Activity aty = AppManager.getActivity(MainActivity.class);
        if (aty != null && !aty.isFinishing()) {
            aty.finish();
        }
        //SystemTool.gc(this); //针对性能好的手机使用，加快应用相应速度

        initView();
        initData();


    }

    @Override
    protected void onResume() {
        super.onResume();
        //如果当前版本号比预存的版本号大则覆盖版本号，清空缓存
        int cacheVersion = PreferenceHelper.readInt(this, "first_install",
                "first_install", -1);
        int currentVersion = TDevice.getVersionCode();
        if (cacheVersion < currentVersion) {
            PreferenceHelper.write(this, "first_install", "first_install",
                    currentVersion);
            //清空图片缓存
            // cleanImageCache();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    //    private void cleanImageCache() {
//        final File folder = FileUtils.getSaveFolder("OSChina/imagecache");
//        KJAsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                for (File file : folder.listFiles()) {
//                    file.delete();
//                }
//            }
//        });
//    }

    /**
     * 跳转到...
     */
    private void redirectTo() {


        if (AppContext.getInstance().isLogin()) {

            //FaceSwipingApi.updateToken(handler);

            UIHelper.showMainActivity(this);
            finish();
        } else {
            //跳到登录界面
            UIHelper.showLoginActivity(this);
            finish();
//                UIHelper.showMainActivity(this);
//                finish();

        }

    }


    @Override
    public void initView() {


        final View view = View.inflate(this, R.layout.app_start, null);

        setContentView(view);



        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(1500);
        view.startAnimation(aa);
        aa.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
    }

    @Override
    public void initData() {

    }

}
