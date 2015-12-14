package com.faceswiping.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;

import com.faceswiping.app.api.ApiHttpClient;
import com.faceswiping.app.base.BaseApplication;
import com.faceswiping.app.bean.User;
import com.faceswiping.app.cache.DataCleanManager;
import com.faceswiping.app.util.MethodsCompat;
import com.faceswiping.app.util.StringUtils;
import com.faceswiping.app.util.TLog;
import com.igexin.sdk.PushManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.Zone;
import com.qiniu.android.storage.persistent.FileRecorder;

import org.kymjs.kjframe.utils.KJLoger;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import static com.faceswiping.app.AppConfig.KEY_FRITST_START;
import static com.faceswiping.app.AppConfig.KEY_LOAD_IMAGE;
import static com.faceswiping.app.AppConfig.KEY_NIGHT_MODE_SWITCH;
import static com.faceswiping.app.AppConfig.KEY_TWEET_DRAFT;


/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 *
 * @author 火蚁 (http://my.oschina.net/LittleDY)
 * @version 1.0
 * @created 2014-04-22
 */
public class AppContext extends BaseApplication {

    public static final int PAGE_SIZE = 20;// 默认分页大小

    private static AppContext instance;

    private int loginUid;

    private static UploadManager uploadManager;

    //推送的Id
    private String clientId;

    private boolean login;

    //用于发布朋友圈后刷新
    private boolean refreshFriendCircle;

    @Override
    public void onCreate() {
        super.onCreate();

//        if (getProperty("user.token") == null) {
//            System.out.println("kong-----------");
//        }

      /*  {

            System.out.println(this.getCacheDir());
            System.out.println(this.getFilesDir());
            System.out.println(new File("/data/data/"
                    + this.getPackageName() + "/databases").getAbsolutePath());

            System.out.println(new File("/data/data/"
                    + this.getPackageName() + "/shared_prefs").getAbsolutePath());


            System.out.println(this.getExternalCacheDir().getAbsolutePath());




        }*/


        instance = this;
        init();
        initLogin();

//        Thread.setDefaultUncaughtExceptionHandler(AppException
//                .getAppExceptionHandler(this));
        // UIHelper.sendBroadcastForNotice(this);
    }

    private void init() {
        // 初始化网络请求
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        client.setCookieStore(myCookieStore);
        ApiHttpClient.setHttpClient(client);
        ApiHttpClient.setCookie(ApiHttpClient.getCookie(this));

        // Log控制器
        KJLoger.openDebutLog(true);
        TLog.DEBUG = BuildConfig.DEBUG;

        //效率太低，弃用KJBitmap
        // Bitmap缓存地址
        // BitmapConfig.CACHEPATH = "OSChina/imagecache";

        //初始化Imageoader
        initImageLoader(this);


        //推送初始化
        if (AppContext.get(AppConfig.KEY_NOTIFICATION_ACCEPT, true)) {
            PushManager.getInstance().initialize(this.getApplicationContext());

        }
        //七牛


        //分片上传纪录器

        Recorder recorder = null;
        try {
            recorder = new FileRecorder(AppContext.this.getFilesDir().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator = new KeyGenerator() {
            @Override
            public String gen(String key, File file) {
                return UUID.randomUUID().toString();
            }
        };

        //默认重试5次失败
        Configuration config = new Configuration.Builder()
                .connectTimeout(10)
                .responseTimeout(60)
                .recorder(recorder, keyGenerator)
                .zone(Zone.zone0)
                .build();


        uploadManager = new UploadManager(config);

    }


    public static void initImageLoader(Context context) {

        DisplayImageOptions displayOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                // 对于同一张图片的不同尺寸的图片之前存的会被remove
                .denyCacheImageMultipleSizesInMemory()
                        //文件生成规则把
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                        // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                        //.writeDebugLogs() // Remove for release app
                .defaultDisplayImageOptions(displayOptions).build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);

    }

    private void initLogin() {
        User user = getLoginUser();
       /* if (null != user && user.getId() > 0) {
            login = true;
            loginUid = user.getId();
        } else {
            this.cleanLoginInfo();
        }*/

        if (null != user && user.getToken() != null && !user.getToken().equals("")) {
            login = true;

            ApiHttpClient.getHttpClient().addHeader("X-AUTH-TOKEN", user.getToken());
            loginUid = user.getId();
        } else {
            this.cleanLoginInfo();
        }
    }

    /**
     * 获得当前app运行的AppContext
     *
     * @return
     */
    public static AppContext getInstance() {
        return instance;
    }

    public static UploadManager getUploadManager() {
        return uploadManager;
    }

    public boolean containsProperty(String key) {
        Properties props = getProperties();
        return props.containsKey(key);
    }

    public void setProperties(Properties ps) {
        AppConfig.getAppConfig(this).set(ps);
    }

    public Properties getProperties() {
        return AppConfig.getAppConfig(this).get();
    }

    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    /**
     * 获取cookie时传AppConfig.CONF_COOKIE
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        String res = AppConfig.getAppConfig(this).get(key);
        return res;
    }

    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }

    /**
     * 获取App唯一标识
     *
     * @return
     */
    public String getAppId() {
        String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
        if (StringUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
        }
        return uniqueID;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }


    public void updateToken(String token) {

        setProperty("user.token", token);
    }


    /**
     * 保存登录信息
     *
     * @param user 用户信息
     */
    @SuppressWarnings("serial")
    public void saveUserInfo(final User user) {
        this.loginUid = user.getId();
        this.login = true;
        setProperties(new Properties() {
            {

                setProperty("user.token", user.getToken());
                setProperty("user.secret", user.getSecret() + "");
                // ApiHttpClient.getHttpClient().setBasicAuth(getProperty("user.token"), null);

                ApiHttpClient.getHttpClient().addHeader("X-AUTH-TOKEN", user.getToken());

                setProperty("user.uid", String.valueOf(user.getId()));
                setProperty("user.certification", String.valueOf(user.getCertification()));
                if (user.getNickName() != null)
                    setProperty("user.name", user.getNickName());

                if (user.getCertificationImageUrl() != null)
                    setProperty("user.certificationImageUrl", user.getCertificationImageUrl());

                if (user.getHeadImageUrl() != null)
                    setProperty("user.headImageUrl", user.getHeadImageUrl());

                // setProperty("user.face", user.getFace_photo());// 用户头像-文件名
                //setProperty("user.phone", user.getPhone());
                //  setProperty("user.apart_name", user.getApart_name());
//                setProperty("user.pwd",
//                        CyptoUtils.encode("oschinaApp", user.getPwd()));
//                setProperty("user.location", user.getLocation());
//                setProperty("user.followers",
//                        String.valueOf(user.getFollowers()));
//                setProperty("user.fans", String.valueOf(user.getFans()));
//                setProperty("user.score", String.valueOf(user.getScore()));
//                setProperty("user.favoritecount",
//                        String.valueOf(user.getFavoritecount()));
//                setProperty("user.gender", String.valueOf(user.getGender()));
//                setProperty("user.isRememberMe",
//                        String.valueOf(user.isRememberMe()));// 是否记住我的信息
            }
        });
    }

    /**
     * 更新用户信息
     *
     * @param user
     */
    @SuppressWarnings("serial")
    public void updateUserInfo(final User user) {
        setProperties(new Properties() {
            {
                setProperty("user.secret", user.getSecret() + "");
                // ApiHttpClient.getHttpClient().setBasicAuth(getProperty("user.token"), null);

                setProperty("user.uid", String.valueOf(user.getId()));
                setProperty("user.certification", String.valueOf(user.getCertification()));

                if (user.getCertificationImageUrl() != null)
                    setProperty("user.certificationImageUrl", user.getCertificationImageUrl());

                if (user.getNickName() != null)
                    setProperty("user.name", user.getNickName());

                System.out.println("user.secret" + getProperty("user.secret"));

                System.out.println("user.certification" + getProperty("user.certification"));

                System.out.println("user.certificationImageUrl" + getProperty("user.certificationImageUrl"));


            }
        });
    }

    /**
     * 获得登录用户的信息
     *
     * @return
     */
    public User getLoginUser() {
        User user = new User();
        user.setToken(getProperty("user.token"));
        user.setId(StringUtils.toInt(getProperty("user.uid"), 0));
        user.setNickName(getProperty("user.name"));
        if (getProperty("user.secret") != null) {
            user.setSecret(Integer.parseInt(getProperty("user.secret")));
        }

        if (getProperty("user.certification") != null) {


            user.setCertification(Integer.parseInt(getProperty("user.certification")));

        }

        if (getProperty("user.certificationImageUrl") != null) {
            user.setCertificationImageUrl(getProperty("user.certificationImageUrl"));
        }

        if (getProperty("user.headImageUrl") != null) {
            user.setHeadImageUrl(getProperty("user.headImageUrl"));
        }

        //user.setFace_photo(getProperty("user.face"));
        //user.setPhone(getProperty("user.phone"));
        // user.setApart_name(getProperty("user.apart_name"));

//        user.setFollowers(StringUtils.toInt(getProperty("user.followers"), 0));
//        user.setFans(StringUtils.toInt(getProperty("user.fans"), 0));
//        user.setScore(StringUtils.toInt(getProperty("user.score"), 0));
//        user.setFavoritecount(StringUtils.toInt(
//                getProperty("user.favoritecount"), 0));
//        user.setRememberMe(StringUtils.toBool(getProperty("user.isRememberMe")));
//        user.setGender(getProperty("user.gender"));
        return user;
    }

    /**
     * 清除登录信息
     */
    public void cleanLoginInfo() {
        this.loginUid = 0;
        this.login = false;

        removeProperty("user.token", "user.id", "user.name", "user.face", "user.apart_name");

       /* removeProperty("user.uid", "user.name", "user.face", "user.location",
                "user.followers", "user.fans", "user.score",
                "user.isRememberMe", "user.gender", "user.favoritecount");*/
    }

    public int getLoginUid() {
        return loginUid;
    }

    public String getClientId() {
        return clientId;
    }

    public boolean isLogin() {
        return login;
    }

    /**
     * 用户注销
     */
    public void Logout() {
        cleanLoginInfo();
        ApiHttpClient.cleanCookie();
        this.cleanCookie();
        this.login = false;
        this.loginUid = 0;

    }

    /**
     * 清除保存的缓存
     */
    public void cleanCookie() {
        removeProperty(AppConfig.CONF_COOKIE);
    }

    /**
     * 清除app缓存
     */
    public void clearAppCache() {
        DataCleanManager.cleanDatabases(this);
        // 清除数据缓存
        DataCleanManager.cleanInternalCache(this);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            DataCleanManager.cleanCustomCache(MethodsCompat
                    .getExternalCacheDir(this));
        }
        // 清除编辑器保存的临时内容
        Properties props = getProperties();
        for (Object key : props.keySet()) {
            String _key = key.toString();
            if (_key.startsWith("temp"))
                removeProperty(_key);
        }
        //new KJBitmap().cleanCache();

        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();

    }

    public static void setLoadImage(boolean flag) {
        set(KEY_LOAD_IMAGE, flag);
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    public static String getTweetDraft() {
        return getPreferences().getString(
                KEY_TWEET_DRAFT + getInstance().getLoginUid(), "");
    }

    public static void setTweetDraft(String draft) {
        set(KEY_TWEET_DRAFT + getInstance().getLoginUid(), draft);
    }

    public static String getNoteDraft() {
        return getPreferences().getString(
                AppConfig.KEY_NOTE_DRAFT + getInstance().getLoginUid(), "");
    }

    public static void setNoteDraft(String draft) {
        set(AppConfig.KEY_NOTE_DRAFT + getInstance().getLoginUid(), draft);
    }

    public static boolean isFristStart() {
        return getPreferences().getBoolean(KEY_FRITST_START, true);
    }

    public static void setFristStart(boolean frist) {
        set(KEY_FRITST_START, frist);
    }

    //夜间模式
    public static boolean getNightModeSwitch() {
        return getPreferences().getBoolean(KEY_NIGHT_MODE_SWITCH, false);
    }

    // 设置夜间模式
    public static void setNightModeSwitch(boolean on) {
        set(KEY_NIGHT_MODE_SWITCH, on);
    }

    public boolean isRefreshFriendCircle() {
        return refreshFriendCircle;
    }

    public void setRefreshFriendCircle(boolean refreshFriendCircle) {
        this.refreshFriendCircle = refreshFriendCircle;
    }
}
