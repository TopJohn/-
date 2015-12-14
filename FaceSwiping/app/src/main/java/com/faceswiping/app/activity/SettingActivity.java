package com.faceswiping.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faceswiping.app.AppConfig;
import com.faceswiping.app.AppContext;
import com.faceswiping.app.AppManager;
import com.faceswiping.app.R;
import com.faceswiping.app.base.BaseActivity;
import com.faceswiping.app.util.FileUtil;
import com.faceswiping.app.util.MethodsCompat;
import com.faceswiping.app.util.UIHelper;
import com.faceswiping.app.widget.ToggleButton;

import org.kymjs.kjframe.bitmap.BitmapConfig;

import java.io.File;

import butterknife.InjectView;

public class SettingActivity extends BaseActivity {


    @InjectView(R.id.ll_update)
    LinearLayout ll_update;

    @InjectView(R.id.ll_about)
    LinearLayout ll_about;

    @InjectView(R.id.tb_double_click_exit)
    ToggleButton tb_double_click_exit;

    @InjectView(R.id.btn_logout)
    Button btn_logout;

    @InjectView(R.id.ll_double_click_exit)
    LinearLayout ll_double_click_exit;

    @InjectView(R.id.ll_clean_cache)
    LinearLayout ll_clean_cache;

    @InjectView(R.id.tv_cache_size)
    TextView tv_cache_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar.setTitle(R.string.menu_setting);
    }


    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {

        btn_logout.setOnClickListener(this);
        ll_update.setOnClickListener(this);
        ll_about.setOnClickListener(this);
        ll_double_click_exit.setOnClickListener(this);
        ll_clean_cache.setOnClickListener(this);
        tb_double_click_exit.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                AppContext.set(AppConfig.KEY_DOUBLE_CLICK_EXIT, on);
            }
        });

    }

    @Override
    public void initData() {

        if (AppContext.get(AppConfig.KEY_DOUBLE_CLICK_EXIT, true)) {
            tb_double_click_exit.setToggleOn();
        } else {
            tb_double_click_exit.setToggleOff();
        }

        caculateCacheSize();

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {

            case R.id.ll_clean_cache:


                break;

            case R.id.ll_update:


                break;

            case R.id.ll_about:


                break;

            case R.id.btn_logout:

                AppContext.getInstance().Logout();
                AppManager.getAppManager().finishAllActivity();
                UIHelper.showLoginActivity(this);


                break;

            case R.id.ll_double_click_exit:
                tb_double_click_exit.toggle();
                break;

            default:

                break;

        }

    }


    /**
     * 计算缓存的大小
     */
    private void caculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = getFilesDir();
        File cacheDir = getCacheDir();

        fileSize += FileUtil.getDirSize(filesDir);
        fileSize += FileUtil.getDirSize(cacheDir);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (AppContext.isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            File externalCacheDir = MethodsCompat
                    .getExternalCacheDir(SettingActivity.this);
            fileSize += FileUtil.getDirSize(externalCacheDir);
            fileSize += FileUtil.getDirSize(new File(
                    org.kymjs.kjframe.utils.FileUtils.getSDCardPath()
                            + File.separator + BitmapConfig.CACHEPATH));
        }
        if (fileSize > 0)
            cacheSize = FileUtil.formatFileSize(fileSize);
        tv_cache_size.setText(cacheSize);
    }


//    private void onClickCleanCache() {
//        DialogHelp.getConfirmDialog(this, "是否清空缓存?", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                UIHelper.clearAppCache(SettingActivity.this);
//                tv_cache_size.setText("0KB");
//            }
//        }).show();
//    }

//    private void onClickUpdate() {
//
//        try {
//            if (AppContext.getInstance().getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_META_DATA).applicationInfo.metaData.get("UMENG_CHANNEL").equals("official")) {
//                new UpdateManager(SettingActivity.this, false).checkUpdate();
//            } else {
//
//                UmengUpdateAgent.setUpdateOnlyWifi(false);
//
//                UmengUpdateAgent.setUpdateAutoPopup(false);
//                UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
//                    @Override
//                    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
//                        switch (updateStatus) {
//                            case UpdateStatus.Yes: // has update
//                                UmengUpdateAgent.showUpdateDialog(getApplicationContext(),
//                                        updateInfo);
//                                break;
//                            case UpdateStatus.No: // has no update
//                                break;
//                            case UpdateStatus.NoneWifi: // none wifi
//                                break;
//                            case UpdateStatus.Timeout: // time out
//                                break;
//                        }
//                    }
//                });
//                UmengUpdateAgent.update(getApplicationContext());
//
//
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

}
