package com.faceswiping.app.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.faceswiping.app.activity.LoginActivity;
import com.inyouzi.app.AppConfig;
import com.inyouzi.app.AppContext;
import com.inyouzi.app.base.BaseListFragment;
import com.inyouzi.app.bean.BankCard;
import com.inyouzi.app.bean.Comment;
import com.inyouzi.app.bean.Community;
import com.inyouzi.app.bean.Constants;
import com.inyouzi.app.bean.Contract;
import com.inyouzi.app.bean.House;
import com.inyouzi.app.bean.Notice;
import com.inyouzi.app.bean.Room;
import com.inyouzi.app.bean.SimpleBackPage;
import com.inyouzi.app.bean.Tweet;
import com.inyouzi.app.interf.ICallbackResult;
import com.inyouzi.app.interf.OnWebViewImageListener;
import com.inyouzi.app.service.DownloadService;
import com.inyouzi.app.service.DownloadService.DownloadBinder;
import com.inyouzi.app.ui.AboutActivity;
import com.inyouzi.app.ui.AddBankCardActivity;
import com.inyouzi.app.ui.AddCommunityActivity;
import com.inyouzi.app.ui.AddDiscountActivity;
import com.inyouzi.app.ui.AddExpendActivity;
import com.inyouzi.app.ui.AddExpendTypeActivity;
import com.inyouzi.app.ui.AddFreeGuestToRoomContractActivity;
import com.inyouzi.app.ui.AddGuestActivity;
import com.inyouzi.app.ui.AddGuestListActivity;
import com.inyouzi.app.ui.AddHostInfoActivity;
import com.inyouzi.app.ui.AddHouseActivity;
import com.inyouzi.app.ui.AddHouseConfigActivity;
import com.inyouzi.app.ui.AddHouseContractActivity;
import com.inyouzi.app.ui.AddHouseDetailActivity;
import com.inyouzi.app.ui.AddNewRoomActivity;
import com.inyouzi.app.ui.AddRoomActivity;
import com.inyouzi.app.ui.AddRoomConfigActivity;
import com.inyouzi.app.ui.AddRoomContractActivity;
import com.inyouzi.app.ui.AllHouseActivity;
import com.inyouzi.app.ui.ApartNameActivity;
import com.inyouzi.app.ui.BalanceDetailActivity;
import com.inyouzi.app.ui.BankCardDetailActivity;
import com.inyouzi.app.ui.BankCardListActivity;
import com.inyouzi.app.ui.ChooseHouseActivity;
import com.inyouzi.app.ui.ChooseRoomActivity;
import com.inyouzi.app.ui.CommunityActivity;
import com.inyouzi.app.ui.CurrentMonthBillActivity;
import com.inyouzi.app.ui.DealDetailActivity;
import com.inyouzi.app.ui.DealListActivity;
import com.inyouzi.app.ui.DetailActivity;
import com.inyouzi.app.ui.DiscountChooseRoomActivity;
import com.inyouzi.app.ui.DiscountDetailActivity;
import com.inyouzi.app.ui.DiscountListActivity;
import com.inyouzi.app.ui.EditGuestInformActivity;
import com.inyouzi.app.ui.EditHouseNameActivity;
import com.inyouzi.app.ui.EditRoomActivity;
import com.inyouzi.app.ui.EmptyHouseListActivity;
import com.inyouzi.app.ui.ExpendConfigActivity;
import com.inyouzi.app.ui.ExpendDetailActivity;
import com.inyouzi.app.ui.ExpendListActivity;
import com.inyouzi.app.ui.FangWuZhuangxiuActivity;
import com.inyouzi.app.ui.FreeGuestActivity;
import com.inyouzi.app.ui.GetBalanceActivity;
import com.inyouzi.app.ui.GuestInformActivity;
import com.inyouzi.app.ui.GuestInformEditActivity;
import com.inyouzi.app.ui.GuestListActivity;
import com.inyouzi.app.ui.HouseBillActivity;
import com.inyouzi.app.ui.HouseBillListActivity;
import com.inyouzi.app.ui.HouseContractInfoEditActivity;
import com.inyouzi.app.ui.HouseInfomEditActivity;
import com.inyouzi.app.ui.HouseInformActivity;
import com.inyouzi.app.ui.HouseTaxesDetailActivity;
import com.inyouzi.app.ui.ImagePreviewActivity;
import com.inyouzi.app.ui.IncreasedListActivity;
import com.inyouzi.app.ui.KeepOnHouseContractActivity;
import com.inyouzi.app.ui.KeepOnRoomContractActivity;
import com.inyouzi.app.ui.MainActivity;
import com.inyouzi.app.ui.MonthExpendActivity;
import com.inyouzi.app.ui.MyWalletActivity;
import com.inyouzi.app.ui.NotificationDetailActivity;
import com.inyouzi.app.ui.NotificationListActivity;
import com.inyouzi.app.ui.NotificationSettingActivity;
import com.inyouzi.app.ui.PhoneActivity;
import com.inyouzi.app.ui.RoomActivity;
import com.inyouzi.app.ui.RoomContractEditActivity;
import com.inyouzi.app.ui.RoomInformActivity;
import com.inyouzi.app.ui.RoomInformEditActivity;
import com.inyouzi.app.ui.RoomStatusActivity;
import com.inyouzi.app.ui.RoomTypeActivity;
import com.inyouzi.app.ui.SearchHouseActivity;
import com.inyouzi.app.ui.SettingActivity;
import com.inyouzi.app.ui.SexSelectActivity;
import com.inyouzi.app.ui.SimpleBackActivity;
import com.inyouzi.app.ui.SplashActivity;
import com.inyouzi.app.ui.TaxesBillActivity;
import com.inyouzi.app.ui.TaxesDueDateListActivity;
import com.inyouzi.app.ui.TaxesPaidListActivity;
import com.inyouzi.app.ui.TaxesPaidMonthActivity;
import com.inyouzi.app.ui.TaxesPaidTodayActivity;
import com.inyouzi.app.ui.TaxesRemindActivity;
import com.inyouzi.app.ui.TweetActivity;
import com.inyouzi.app.ui.UserInformActivity;
import com.inyouzi.app.ui.UserNameActivity;
import com.inyouzi.app.ui.YeZhuXinxiActivity;
import com.inyouzi.app.ui.YeZhuXinxiEditActivity;
import com.inyouzi.app.widget.AvatarView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 界面帮助类
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @version 创建时间：2014年10月10日 下午3:33:36
 */
public class UIHelper {

    /**
     * 全局web样式
     */
    // 链接样式文件，代码块高亮的处理
    public final static String linkCss = "<script type=\"text/javascript\" src=\"file:///android_asset/shCore.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/brush.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/client.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/detail_page.js\"></script>"
            + "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>"
            + "<script type=\"text/javascript\">function showImagePreview(var url){window.location.url= url;}</script>"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shThemeDefault.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shCore.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/common.css\">";
    public final static String WEB_STYLE = linkCss;

    public static final String WEB_LOAD_IMAGES = "<script type=\"text/javascript\"> var allImgUrls = getAllImgSrc(document.body.innerHTML);</script>";

    private static final String SHOWIMAGE = "ima-api:action=showImage&data=";


    /**
     * 添加网页的点击图片展示支持
     */
    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @JavascriptInterface
    public static void addWebImageShow(final Context cxt, WebView wv) {
        wv.getSettings().setJavaScriptEnabled(true);
        wv.addJavascriptInterface(new OnWebViewImageListener() {
            @Override
            @JavascriptInterface
            public void showImagePreview(String bigImageUrl) {
                if (bigImageUrl != null && !StringUtils.isEmpty(bigImageUrl)) {
                    UIHelper.showImagePreview(cxt, new String[]{bigImageUrl});
                }
            }
        }, "mWebViewImageListener");
    }


    public static String setHtmlCotentSupportImagePreview(String body) {
        // 读取用户设置：是否加载文章图片--默认有wifi下始终加载图片
        if (AppContext.get(AppConfig.KEY_LOAD_IMAGE, true)
                || TDevice.isWifiOpen()) {
            // 过滤掉 img标签的width,height属性
            body = body.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
            body = body.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");
            // 添加点击图片放大支持
            // 添加点击图片放大支持
            body = body.replaceAll("(<img[^>]+src=\")(\\S+)\"",
                    "$1$2\" onClick=\"showImagePreview('$2')\"");
        } else {
            // 过滤掉 img标签
            body = body.replaceAll("<\\s*img\\s+([^>]*)\\s*>", "");
        }
        return body;
    }


    /**
     * 打开系统中的浏览器
     *
     * @param context
     * @param url
     */
    public static void openSysBrowser(Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
            AppContext.showToastShort("无法浏览此网页");
        }
    }

    @JavascriptInterface
    public static void showImagePreview(Context context, String[] imageUrls) {
        ImagePreviewActivity.showImagePrivew(context, 0, imageUrls);
    }

    @JavascriptInterface
    public static void showImagePreview(Context context, int index,
                                        String[] imageUrls) {
        ImagePreviewActivity.showImagePrivew(context, index, imageUrls);
    }


    public static SpannableString parseActiveAction(int objecttype,
                                                    int objectcatalog, String objecttitle) {
        String title = "";
        int start = 0;
        int end = 0;
        if (objecttype == 32 && objectcatalog == 0) {
            title = "加入了开源中国";
        } else if (objecttype == 1 && objectcatalog == 0) {
            title = "添加了开源项目 " + objecttitle;
        } else if (objecttype == 2 && objectcatalog == 1) {
            title = "在讨论区提问：" + objecttitle;
        } else if (objecttype == 2 && objectcatalog == 2) {
            title = "发表了新话题：" + objecttitle;
        } else if (objecttype == 3 && objectcatalog == 0) {
            title = "发表了博客 " + objecttitle;
        } else if (objecttype == 4 && objectcatalog == 0) {
            title = "发表一篇新闻 " + objecttitle;
        } else if (objecttype == 5 && objectcatalog == 0) {
            title = "分享了一段代码 " + objecttitle;
        } else if (objecttype == 6 && objectcatalog == 0) {
            title = "发布了一个职位：" + objecttitle;
        } else if (objecttype == 16 && objectcatalog == 0) {
            title = "在新闻 " + objecttitle + " 发表评论";
        } else if (objecttype == 17 && objectcatalog == 1) {
            title = "回答了问题：" + objecttitle;
        } else if (objecttype == 17 && objectcatalog == 2) {
            title = "回复了话题：" + objecttitle;
        } else if (objecttype == 17 && objectcatalog == 3) {
            title = "在 " + objecttitle + " 对回帖发表评论";
        } else if (objecttype == 18 && objectcatalog == 0) {
            title = "在博客 " + objecttitle + " 发表评论";
        } else if (objecttype == 19 && objectcatalog == 0) {
            title = "在代码 " + objecttitle + " 发表评论";
        } else if (objecttype == 20 && objectcatalog == 0) {
            title = "在职位 " + objecttitle + " 发表评论";
        } else if (objecttype == 101 && objectcatalog == 0) {
            title = "回复了动态：" + objecttitle;
        } else if (objecttype == 100) {
            title = "更新了动态";
        }
        SpannableString sp = new SpannableString(title);
        // 设置标题字体大小、高亮
        if (!StringUtils.isEmpty(objecttitle)) {
            start = title.indexOf(objecttitle);
            if (objecttitle.length() > 0 && start > 0) {
                end = start + objecttitle.length();
                sp.setSpan(new AbsoluteSizeSpan(14, true), start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sp.setSpan(
                        new ForegroundColorSpan(Color.parseColor("#0e5986")),
                        start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return sp;
    }

    /**
     * 组合动态的回复文本
     *
     * @param name
     * @param body
     * @return
     */
    public static SpannableStringBuilder parseActiveReply(String name,
                                                          String body) {
        Spanned span = Html.fromHtml(body.trim());
        SpannableStringBuilder sp = new SpannableStringBuilder(name + "：");
        sp.append(span);
        // 设置用户名字体加粗、高亮
        // sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
        // name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#008000")), 0,
                name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sp;
    }

    /**
     * 发送App异常崩溃报告
     *
     * @param context
     * @param crashReport
     */
    public static void sendAppCrashReport(final Context context) {

        DialogHelp.getConfirmDialog(context, "程序发生异常", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 退出
                System.exit(-1);
            }
        }).show();
    }

    /**
     * 发送通知广播
     *
     * @param context
     * @param notice
     */
    public static void sendBroadCast(Context context, Notice notice) {
        if (!((AppContext) context.getApplicationContext()).isLogin()
                || notice == null)
            return;
        Intent intent = new Intent(Constants.INTENT_ACTION_NOTICE);
        Bundle bundle = new Bundle();
        bundle.putSerializable("notice_bean", notice);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }


    /**
     * 显示用户中心页面
     *
     * @param context
     * @param hisuid
     * @param hisuid
     * @param hisname
     */
    public static void showUserCenter(Context context, int hisuid,
                                      String hisname) {
        if (hisuid == 0 && hisname.equalsIgnoreCase("匿名")) {
            AppContext.showToast("提醒你，该用户为非会员");
            return;
        }
        Bundle args = new Bundle();
        args.putInt("his_id", hisuid);
        args.putString("his_name", hisname);
//        showSimpleBack(context, SimpleBackPage.USER_CENTER, args);
    }

    /**
     * 显示用户的博客列表
     *
     * @param context
     * @param uid
     */
    public static void showUserBlog(Context context, int uid) {
        Bundle args = new Bundle();
        args.putInt(BaseListFragment.BUNDLE_KEY_CATALOG, uid);
//        showSimpleBack(context, SimpleBackPage.USER_BLOG, args);
    }

    /**
     * 显示用户头像大图
     *
     * @param context
     * @param avatarUrl
     */
    public static void showUserAvatar(Context context, String avatarUrl) {
        if (StringUtils.isEmpty(avatarUrl)) {
            return;
        }
        String url = AvatarView.getLargeAvatar(avatarUrl);
        ImagePreviewActivity.showImagePrivew(context, 0, new String[]{url});
    }

    /**
     * 显示登陆用户的个人中心页面
     *
     * @param context
     */
    public static void showMyInformation(Context context) {
//        showSimpleBack(context, SimpleBackPage.MY_INFORMATION);
    }

    /**
     * 显示我的所有动态
     *
     * @param context
     */
    public static void showMyActive(Context context) {
//        showSimpleBack(context, SimpleBackPage.MY_ACTIVE);
    }

    /**
     * 显示扫一扫界面
     *
     * @param context
     *//*
    public static void showScanActivity(Context context) {
        Intent intent = new Intent(context, CaptureActivity.class);
        context.startActivity(intent);
    }*/

    /**
     * 显示用户的消息中心
     *
     * @param context
     */
    public static void showMyMes(Context context) {
//        showSimpleBack(context, SimpleBackPage.MY_MES);
    }

    /**
     * 显示用户收藏界面
     *
     * @param context
     */
    public static void showUserFavorite(Context context, int uid) {

        Bundle args = new Bundle();
        args.putInt(BaseListFragment.BUNDLE_KEY_CATALOG, uid);
//        showSimpleBack(context, SimpleBackPage.USER_FAVORITE);
    }


    /**
     * 显示设置界面
     *
     * @param context
     */
    public static void showSetting(Context context) {
//        showSimpleBack(context, SimpleBackPage.SETTING);
    }

    /**
     * 显示通知设置界面
     *
     * @param context
     */
    public static void showSettingNotification(Context context) {
//        showSimpleBack(context, SimpleBackPage.SETTING_NOTIFICATION);
    }

    /**
     * 显示关于界面
     *
     * @param context
     */
    public static void showAboutOSC(Context context) {
//        showSimpleBack(context, SimpleBackPage.ABOUT_OSC);
    }

    /**
     * 清除app缓存
     *
     * @param activity
     */
    public static void clearAppCache(Activity activity) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    AppContext.showToastShort("缓存清除成功");
                } else {
                    AppContext.showToastShort("缓存清除失败");
                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    AppContext.getInstance().clearAppCache();
                    msg.what = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = -1;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }

    public static void openDownLoadService(Context context, String downurl,
                                           String tilte) {
        final ICallbackResult callback = new ICallbackResult() {

            @Override
            public void OnBackResult(Object s) {
            }
        };
        ServiceConnection conn = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                DownloadBinder binder = (DownloadBinder) service;
                binder.addCallback(callback);
                binder.start();

            }
        };
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(DownloadService.BUNDLE_KEY_DOWNLOAD_URL, downurl);
        intent.putExtra(DownloadService.BUNDLE_KEY_TITLE, tilte);
        context.startService(intent);
        context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    /**
     * 发送广播告知评论发生变化
     *
     * @param context
     * @param isBlog
     * @param id
     * @param catalog
     * @param operation
     * @param replyComment
     */
    public static void sendBroadCastCommentChanged(Context context,
                                                   boolean isBlog, int id, int catalog, int operation,
                                                   Comment replyComment) {
        Intent intent = new Intent(Constants.INTENT_ACTION_COMMENT_CHANGED);
        Bundle args = new Bundle();
        args.putInt(Comment.BUNDLE_KEY_ID, id);
        args.putInt(Comment.BUNDLE_KEY_CATALOG, catalog);
        args.putBoolean(Comment.BUNDLE_KEY_BLOG, isBlog);
        args.putInt(Comment.BUNDLE_KEY_OPERATION, operation);
        args.putParcelable(Comment.BUNDLE_KEY_COMMENT, replyComment);
        intent.putExtras(args);
        context.sendBroadcast(intent);
    }


    /**
     * 显示搜索房屋界面
     *
     * @param context
     */
    public static void showSearchHouseActivity(Context context) {
        Intent intent = new Intent(context, SearchHouseActivity.class);
        context.startActivity(intent);
    }


    /**
     * 显示引导注册界面
     */
    public static void showSplashActivity(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        context.startActivity(intent);
    }


    /**
     * 显示注册界面
     */
    public static void showLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }


    /**
     * 显示房屋信息页面
     */
    public static void showHouseInformActivity(Context context, int house_id) {
        Intent intent = new Intent(context, HouseInformActivity.class);
        intent.putExtra("house_id", house_id);
        context.startActivity(intent);
    }


    /**
     * 显示房屋信息编辑页面
     */
    public static void showHouseInformEditActivity(Context context, House house, int requestCode) {
        Intent intent = new Intent(context, HouseInfomEditActivity.class);
        intent.putExtra("house", house);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    /**
     * 显示房屋信息编辑页面
     */
    public static void showMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    /**
     * 显示添加房源页面
     */
    public static void showAddHouseActivity(Context context) {
        Intent intent = new Intent(context, AddHouseActivity.class);
        context.startActivity(intent);
    }


    /**
     * 显示添加支出页面
     */
    public static void showAddExpendActivity(Context context) {
        Intent intent = new Intent(context, AddExpendActivity.class);
        context.startActivity(intent);
    }

    /**
     * 显示添加业主信息页面
     */
    public static void showAddHostInoActivity(Context context) {
        Intent intent = new Intent(context, AddHostInfoActivity.class);
        context.startActivity(intent);
    }


    /**
     * 显示添加租客信息页面
     */
    public static void showAddGuestActivity(Context context, int requestCode) {
        Intent intent = new Intent(context, AddGuestActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }


    /**
     * 显示租客列表信息页面
     */
    public static void showAddGuestListActivity(Context context) {
        Intent intent = new Intent(context, AddGuestListActivity.class);
        context.startActivity(intent);
    }


    /**
     * 显示个人详细信息页面
     */
    public static void showUserInformActivity(Context context) {
        Intent intent = new Intent(context, UserInformActivity.class);
        context.startActivity(intent);
    }

    /**
     * 显示个人详细信息页面
     */
    public static void showUserNameActivity(Context context) {
        Intent intent = new Intent(context, UserNameActivity.class);
        context.startActivity(intent);
    }

    /**
     * 显示所有房屋信息页面
     */
    public static void showAllHouseActivity(Context context) {
        Intent intent = new Intent(context, AllHouseActivity.class);
        context.startActivity(intent);
    }

    /**
     * 显示房屋装修页面
     */
    public static void showFangWuZhuangxiuActivity(Context context, int requestCode) {
        Intent intent = new Intent(context, FangWuZhuangxiuActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    /**
     * 显示房屋单间详情
     */
    public static void showRoomInformActivity(Context context, int room_id) {

        Intent intent = new Intent(context, RoomInformActivity.class);
        intent.putExtra("room_id", room_id);
        ((Activity) context).startActivity(intent);
    }


    public static void showRoomInformActivityForResult(Context context, int room_id, int requestCode) {

        Intent intent = new Intent(context, RoomInformActivity.class);
        intent.putExtra("room_id", room_id);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    public static void showCommunityActivity(Context context, int requestCode) {
        Intent intent = new Intent(context, CommunityActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }

    public static void showAddCommunityActivity(Context context, int requestCode) {
        Intent intent = new Intent(context, AddCommunityActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }

    public static void showAddHouseConfigActivity(Context context, int requestCode, Serializable serializable) {
        Intent intent = new Intent(context, AddHouseConfigActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("things_json", serializable);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }

    public static void showAddHouseDetailActivity(Context context, int requestCode, House house) {

        Intent intent = new Intent(context, AddHouseDetailActivity.class);
        intent.putExtra("photo", (ArrayList<String>) house.getPhoto());
        intent.putExtra("layout_bedroom", house.getLayout_bedroom());
        intent.putExtra("layout_livingroom", house.getLayout_livingroom());
        intent.putExtra("layout_bathroom", house.getLayout_bathroom());
        intent.putExtra("decoration_status", house.getDecoration_status());
        intent.putExtra("area", house.getArea());
        intent.putExtra("remark", house.getRemark());
        ((Activity) context).startActivityForResult(intent, requestCode);


    }

    public static void showAddHouseContractActivity(Context context, int house_id) {

        Intent intent = new Intent(context, AddHouseContractActivity.class);
        intent.putExtra("house_id", house_id);
        context.startActivity(intent);


    }

    //isNewHouse false 则从房屋详情的合同里跳转
    public static void showAddHouseContractActivityForResult(Context context, int house_id, boolean isNewHouse, int requestCode) {

        Intent intent = new Intent(context, AddHouseContractActivity.class);
        intent.putExtra("house_id", house_id);
        intent.putExtra("isNewHouse", isNewHouse);
        ((Activity) context).startActivityForResult(intent, requestCode);


    }

    public static void showAddRoomActivity(Context context, int house_id) {

        Intent intent = new Intent(context, AddRoomActivity.class);
        intent.putExtra("house_id", house_id);
        ((Activity) context).startActivity(intent);

    }

    public static void showEditRoomActivity(Context context, Room room, int requestCode) {

        Intent intent = new Intent(context, EditRoomActivity.class);
        intent.putExtra("room", room);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }

    public static void showRoomTypeActivity(Context context, int requestCode) {

        Intent intent = new Intent(context, RoomTypeActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }


    public static void showRoomStatusActivity(Context context, int requestCode) {

        Intent intent = new Intent(context, RoomStatusActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }


    public static void showSexSelectActivity(Context context, int requestCode) {

        Intent intent = new Intent(context, SexSelectActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }


    public static void showYeZhuXinxiActivity(Context context, int house_id, String owner_name, String owner_phone, int requestCode) {

        Intent intent = new Intent(context, YeZhuXinxiActivity.class);
        intent.putExtra("house_id", house_id);
        intent.putExtra("owner_name", owner_name);
        intent.putExtra("owner_phone", owner_phone);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }


    public static void showYeZhuXinxiEditActivity(Context context, int house_id, String owner_name, String owner_phone, int requestCode) {

        Intent intent = new Intent(context, YeZhuXinxiEditActivity.class);
        intent.putExtra("house_id", house_id);
        intent.putExtra("owner_name", owner_name);
        intent.putExtra("owner_phone", owner_phone);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }

    public static void showRoomActivity(Context context, int house_id, int requestCode) {

        Intent intent = new Intent(context, RoomActivity.class);
        intent.putExtra("house_id", house_id);
        ((Activity) context).startActivityForResult(intent, requestCode);


    }

    public static void showCallActivity(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        ((Activity) context).startActivity(intent);

    }

    public static void showMessageActivity(Context context, String phoneNumber) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        //intent.putExtra("sms_body", "短信内容");
        ((Activity) context).startActivity(intent);

    }

    public static void showHouseContractEditActivity(Context context, House house, int requestCode) {

        Intent intent = new Intent(context, HouseContractInfoEditActivity.class);

        intent.putExtra("house", house);

        ((Activity) context).startActivityForResult(intent, requestCode);


    }

    public static void showEditHouseNameActivity(Context context, House house, int requestCode) {

        Intent intent = new Intent(context, EditHouseNameActivity.class);
        intent.putExtra("house_id", house.getId());
        Community community = new Community();
        community.setName(house.getAddress_community());
        community.setCommunity_province(house.getAddress_province());
        community.setCommunity_city(house.getAddress_city());
        community.setCommunity_district(house.getAddress_district());
        intent.putExtra("community", community);
        intent.putExtra("address_building", house.getAddress_building());
        intent.putExtra("address_unit", house.getAddress_unit());
        intent.putExtra("address_room", house.getAddress_room());

        ((Activity) context).startActivityForResult(intent, requestCode);

    }

    public static void showKeepOnHouseContractActivity(Context context, Contract contract, int requestCode) {


        Intent intent = new Intent(context, KeepOnHouseContractActivity.class);
        intent.putExtra("contract", contract);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }


    public static void showAddNewRoomActivity(Context context, int house_id, int requestCode) {

        Intent intent = new Intent(context, AddNewRoomActivity.class);
        intent.putExtra("house_id", house_id);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }

    public static void showEditGuestInformActivity(Context context, int guest_id, int requestCode) {

        Intent intent = new Intent(context, EditGuestInformActivity.class);
        intent.putExtra("guest_id", guest_id);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }


    public static void showGuestInformActivity(Context context, int guest_id) {

        Intent intent = new Intent(context, GuestInformActivity.class);
        intent.putExtra("guest_id", guest_id);
        ((Activity) context).startActivity(intent);

    }


    public static void showAddRoomContractActivity(Context context, Room room, int requestCode) {

        Intent intent = new Intent(context, AddRoomContractActivity.class);
        intent.putExtra("room", room);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }

    public static void showRoomContractEditActivity(Context context, Room room, int requestCode) {

        Intent intent = new Intent(context, RoomContractEditActivity.class);

        intent.putExtra("room", room);

        ((Activity) context).startActivityForResult(intent, requestCode);


    }

    public static void showKeepOnRoomContractActivity(Context context, Room room, int requestCode) {


        Intent intent = new Intent(context, KeepOnRoomContractActivity.class);
        intent.putExtra("room", room);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }


    public static void showRoomInformEditActivity(Context context, Room room, int requestCode) {

        Intent intent = new Intent(context, RoomInformEditActivity.class);

        intent.putExtra("room", room);

        ((Activity) context).startActivityForResult(intent, requestCode);


    }

    public static void showAddRoomConfigActivity(Context context, int requestCode, Serializable serializable) {
        Intent intent = new Intent(context, AddRoomConfigActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("things_json", serializable);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }

    public static void showTaxesRemindActivity(Context context) {

        Intent intent = new Intent(context, TaxesRemindActivity.class);
        ((Activity) context).startActivity(intent);
    }

    public static void showEmptyHouseListActivity(Context context) {

        Intent intent = new Intent(context, EmptyHouseListActivity.class);
        ((Activity) context).startActivity(intent);
    }

    public static void showTaxesDueDateListActivity(Context context) {

        Intent intent = new Intent(context, TaxesDueDateListActivity.class);
        ((Activity) context).startActivity(intent);
    }

    public static void showGuestInformEditActivity(Context context, int guest_id, int requestCode) {

        Intent intent = new Intent(context, GuestInformEditActivity.class);
        intent.putExtra("guest_id", guest_id);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    public static void showIncreasedListActivity(Context context) {

        Intent intent = new Intent(context, IncreasedListActivity.class);
        ((Activity) context).startActivity(intent);
    }

    public static void showTaxesPaidListActivity(Context context) {

        Intent intent = new Intent(context, TaxesPaidListActivity.class);
        ((Activity) context).startActivity(intent);
    }

    public static void showApartNameActivity(Context context) {
        Intent intent = new Intent(context, ApartNameActivity.class);
        context.startActivity(intent);
    }


    public static void showPhoneActivity(Context context) {
        Intent intent = new Intent(context, PhoneActivity.class);
        context.startActivity(intent);
    }

    public static void showTaxesPaidTodayActivity(Context context) {

        Intent intent = new Intent(context, TaxesPaidTodayActivity.class);
        context.startActivity(intent);

    }


    public static void showTaxesPaidMonthActivity(Context context) {

        Intent intent = new Intent(context, TaxesPaidMonthActivity.class);
        context.startActivity(intent);

    }

    public static void showTaxesBillActivity(int payment_id, int room_id, Context context) {

        Intent intent = new Intent(context, TaxesBillActivity.class);
        intent.putExtra("payment_id", payment_id);
        intent.putExtra("room_id", room_id);

        context.startActivity(intent);

    }

    public static void showMonthExpendActivity(Context context) {

        Intent intent = new Intent(context, MonthExpendActivity.class);
        context.startActivity(intent);

    }

    public static void showExpendListActivity(Context context, int house_id, int year, int month, int expend_type) {

        Intent intent = new Intent(context, ExpendListActivity.class);

        intent.putExtra("house_id", house_id);
        intent.putExtra("expend_type", expend_type);
        intent.putExtra("year", year);
        intent.putExtra("month", month);

        context.startActivity(intent);

    }

    public static void showExpendDetailActivity(Context context, int expend_id) {

        Intent intent = new Intent(context, ExpendDetailActivity.class);

        intent.putExtra("expend_id", expend_id);

        context.startActivity(intent);

    }

    public static void showExpendConfigActivity(Context context, HashMap<String, Integer> things_json) {

        Intent intent = new Intent(context, ExpendConfigActivity.class);

        intent.putExtra("things_json", things_json);

        context.startActivity(intent);

    }

    public static void showCurrentMonthBillActivity(Context context) {

        Intent intent = new Intent(context, CurrentMonthBillActivity.class);

        context.startActivity(intent);

    }

    public static void showAddExpendTypeActivity(Context context, int requestCode) {

        Intent intent = new Intent(context, AddExpendTypeActivity.class);

        ((Activity) context).startActivityForResult(intent, requestCode);

    }


    public static void showChooseHouseActivity(Context context, int requestCode) {

        Intent intent = new Intent(context, ChooseHouseActivity.class);

        ((Activity) context).startActivityForResult(intent, requestCode);

    }

    public static void showGuestListActivity(Context context) {

        Intent intent = new Intent(context, GuestListActivity.class);

        context.startActivity(intent);

    }

    public static void showFreeGuestActivity(Context context, int guest_id) {

        Intent intent = new Intent(context, FreeGuestActivity.class);

        intent.putExtra("guest_id", guest_id);

        context.startActivity(intent);

    }

    public static void showChooseRoomActivity(Context context, int requestCode) {

        Intent intent = new Intent(context, ChooseRoomActivity.class);

        //intent.putExtra("guest_id", guest_id);

        ((Activity) context).startActivityForResult(intent, requestCode);

    }

    public static void showAddFreeGuestToRoomContractActivity(Context context, int guest_id, String guest_name, int room_id, String address) {

        Intent intent = new Intent(context, AddFreeGuestToRoomContractActivity.class);

        intent.putExtra("guest_id", guest_id);
        intent.putExtra("guest_name", guest_name);
        intent.putExtra("room_id", room_id);
        intent.putExtra("address", address);

        context.startActivity(intent);

    }

    public static void showAddFreeGuestToRoomContractActivity(Context context, int guest_id, String guest_name, int room_id, String address, int requestCode) {

        Intent intent = new Intent(context, AddFreeGuestToRoomContractActivity.class);

        intent.putExtra("guest_id", guest_id);
        intent.putExtra("guest_name", guest_name);
        intent.putExtra("room_id", room_id);
        intent.putExtra("address", address);

        ((Activity) context).startActivityForResult(intent, requestCode);

    }

    public static void showHouseBillListActivity(Context context) {

        Intent intent = new Intent(context, HouseBillListActivity.class);

        context.startActivity(intent);

    }

    public static void showHouseBillActivity(Context context, int house_id, String address) {

        Intent intent = new Intent(context, HouseBillActivity.class);
        intent.putExtra("house_id", house_id);
        intent.putExtra("address", address);
        context.startActivity(intent);

    }

    public static void showHouseTaxesDetailActivity(Context context, int house_id, int year, int month) {

        Intent intent = new Intent(context, HouseTaxesDetailActivity.class);
        intent.putExtra("house_id", house_id);
        intent.putExtra("year", year);
        intent.putExtra("month", month);
        context.startActivity(intent);

    }

    public static void showAddDiscountActivity(Context context) {

        Intent intent = new Intent(context, AddDiscountActivity.class);
        context.startActivity(intent);

    }


    public static void showDiscountChooseRoomActivity(Context context, int requestCode) {

        Intent intent = new Intent(context, DiscountChooseRoomActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }

    public static void showDiscountDetailActivity(Context context, int discount_id) {

        Intent intent = new Intent(context, DiscountDetailActivity.class);

        intent.putExtra("discount_id", discount_id);

        context.startActivity(intent);

    }

    public static void showDiscountListActivity(Context context, int payment_id) {

        Intent intent = new Intent(context, DiscountListActivity.class);

        intent.putExtra("payment_id", payment_id);

        context.startActivity(intent);

    }

    public static void showSettingActivity(Context context) {

        Intent intent = new Intent(context, SettingActivity.class);

        context.startActivity(intent);

    }

    public static void showAboutActivity(Context context) {

        Intent intent = new Intent(context, AboutActivity.class);

        context.startActivity(intent);

    }

    public static void showMyWalletActivity(Context context) {

        Intent intent = new Intent(context, MyWalletActivity.class);

        context.startActivity(intent);

    }

    public static void showBankCardDetailActivity(Context context, BankCard bankCard) {

        Intent intent = new Intent(context, BankCardDetailActivity.class);
        intent.putExtra("bankCard", bankCard);
        context.startActivity(intent);

    }

    public static void showGetBalanceActivity(Context context) {

        Intent intent = new Intent(context, GetBalanceActivity.class);
        context.startActivity(intent);

    }

    public static void showAddBankCardActivity(Context context) {

        Intent intent = new Intent(context, AddBankCardActivity.class);
        context.startActivity(intent);

    }

    public static void showAddBankCardActivity(Context context, int requestCode) {

        Intent intent = new Intent(context, AddBankCardActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }

    public static void showBalanceDetailActivity(Context context, BankCard bankCard, float amount) {

        Intent intent = new Intent(context, BalanceDetailActivity.class);

        intent.putExtra("bankCard", bankCard);
        intent.putExtra("amount", amount);

        context.startActivity(intent);

    }


    public static void showBankCardListActivity(Context context, int requestCode) {

        Intent intent = new Intent(context, BankCardListActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }

    public static void showDealDetailActivity(Context context, int id, int type) {

        Intent intent = new Intent(context, DealDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        context.startActivity(intent);

    }

    public static void showDealListActivity(Context context) {

        Intent intent = new Intent(context, DealListActivity.class);
        context.startActivity(intent);

    }

    public static void showNotificationListActivity(Context context) {

        Intent intent = new Intent(context, NotificationListActivity.class);
        context.startActivity(intent);

    }

    public static void showNotificationDetailActivity(Context context, int notification_id) {

        Intent intent = new Intent(context, NotificationDetailActivity.class);
        intent.putExtra("notification_id", notification_id);
        context.startActivity(intent);

    }

    public static void showNotificationSettingActivity(Context context) {

        Intent intent = new Intent(context, NotificationSettingActivity.class);
        context.startActivity(intent);

    }

    public static void showTweetActivity(Context context, SimpleBackPage page) {
        Intent intent = new Intent(context, TweetActivity.class);
        intent.putExtra(TweetActivity.FROM_KEY, 1);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }

    /**
     * 显示动弹详情
     *
     * @param context context
     * @param tweetid 动弹的id
     */
    public static void showTweetDetail(Context context, Tweet tweet, int tweetid) {
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("tweet_id", tweetid);
        bundle.putInt(DetailActivity.BUNDLE_KEY_DISPLAY_TYPE,
                DetailActivity.DISPLAY_TWEET);
        if (tweet != null) {
            bundle.putParcelable("tweet", tweet);
        }
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


}
