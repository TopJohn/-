package com.faceswiping.app.api.remote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.inyouzi.app.AppContext;
import com.inyouzi.app.AppException;
import com.inyouzi.app.api.ApiHttpClient;
import com.inyouzi.app.bean.Community;
import com.inyouzi.app.bean.Contract;
import com.inyouzi.app.bean.Discount;
import com.inyouzi.app.bean.EventApplyData;
import com.inyouzi.app.bean.Expend;
import com.inyouzi.app.bean.Guest;
import com.inyouzi.app.bean.House;
import com.inyouzi.app.bean.Report;
import com.inyouzi.app.bean.Room;
import com.inyouzi.app.bean.Tweet;
import com.inyouzi.app.util.StringUtils;
import com.inyouzi.app.util.TLog;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import org.kymjs.kjframe.utils.KJLoger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.util.Date;


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
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("pwd", password);
        params.put("keep_login", 1);
        String loginurl = "action/api/login_validate";
        ApiHttpClient.post(loginurl, params, handler);
    }


    public static void getBlogList(String type, int pageIndex,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("type", type);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/blog_list", params, handler);
    }

    public static void getPostList(int catalog, int page,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/post_list", params, handler);
    }

    public static void getPostListByTag(String tag, int page,
                                        AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("tag", tag);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/post_list", params, handler);
    }


    public static void getTweetTopicList(int page, String topic,
                                         AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("pageIndex", page);
        params.put("title", topic);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/tweet_topic_list", params, handler);
    }

    public static void getTweetLikeList(AsyncHttpResponseHandler handler) {
        ApiHttpClient.get("action/api/my_tweet_like_list", handler);
    }


    public static void getTweetLikeList(int tweetId, int page,
                                        AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("tweetid", tweetId);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/tweet_like_list", params, handler);

    }

    public static void getActiveList(int uid, int catalog, int page,
                                     AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("catalog", catalog);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/active_list", params, handler);
    }

    public static void getFriendList(int uid, int relation, int page,
                                     AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("relation", relation);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/friends_list", params, handler);
    }

    /**
     * 获取用户收藏
     *
     * @param uid     指定用户UID
     * @param type    收藏类型: 0:全部收藏　1:软件　2:话题　3:博客　4:新闻　5:代码
     * @param page
     * @param handler
     */
    public static void getFavoriteList(int uid, int type, int page,
                                       AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("type", type);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/favorite_list", params, handler);
    }

    /**
     * 分类列表
     *
     * @param tag     第一级:0
     * @param handler
     */
    public static void getSoftwareCatalogList(int tag,
                                              AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("tag", tag);
        ApiHttpClient.get("action/api/softwarecatalog_list", params, handler);
    }

    public static void getSoftwareTagList(int searchTag, int page,
                                          AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("searchTag", searchTag);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/softwaretag_list", params, handler);
    }

    /**
     * @param searchTag 　　软件分类　　推荐:recommend 最新:time 热门:view 国产:list_cn
     * @param page
     * @param handler
     */
    public static void getSoftwareList(String searchTag, int page,
                                       AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("searchTag", searchTag);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/software_list", params, handler);
    }


    public static void getBlogCommentList(int id, int page,
                                          AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/blogcomment_list", params, handler);
    }

    public static void getUserInformation(int uid, int hisuid, String hisname,
                                          int pageIndex, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("hisuid", hisuid);
        params.put("hisname", hisname);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/user_information", params, handler);
    }

    @SuppressWarnings("deprecation")
    public static void getUserBlogList(int authoruid, final String authorname,
                                       final int uid, final int pageIndex, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("authoruid", authoruid);
        params.put("authorname", URLEncoder.encode(authorname));
        params.put("uid", uid);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/userblog_list", params, handler);
    }

    public static void updateRelation(int uid, int hisuid, int newrelation,
                                      AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("hisuid", hisuid);
        params.put("newrelation", newrelation);
        ApiHttpClient.post("action/api/user_updaterelation", params, handler);
    }


    /**
     * 获取新闻明细
     *
     * @param id      新闻的id
     * @param handler
     */
    public static void getNewsDetail(int id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id", id);
        ApiHttpClient.get("action/api/news_detail", params, handler);
    }

    public static void getBlogDetail(int id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id", id);
        ApiHttpClient.get("action/api/blog_detail", params, handler);
    }

    /**
     * 获取软件详情
     *
     * @param ident
     * @param handler
     */
    public static void getSoftwareDetail(String ident,
                                         AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("ident",
                ident);
        ApiHttpClient.get("action/api/software_detail", params, handler);
    }

    /**
     * 通过id获取软件详情
     *
     * @param id
     * @param handler
     */
    public static void getSoftwareDetail(int id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id",
                id);
        ApiHttpClient.get("action/api/software_detail", params, handler);
    }

    public static void getPostDetail(int id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id", id);
        ApiHttpClient.get("action/api/post_detail", params, handler);
    }


    public static void publicBlogComment(int blog, int uid, String content,
                                         AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("blog", blog);
        params.put("uid", uid);
        params.put("content", content);
        ApiHttpClient.post("action/api/blogcomment_pub", params, handler);
    }

    public static void replyBlogComment(int blog, int uid, String content,
                                        int reply_id, int objuid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("blog", blog);
        params.put("uid", uid);
        params.put("content", content);
        params.put("reply_id", reply_id);
        params.put("objuid", objuid);
        ApiHttpClient.post("action/api/blogcomment_pub", params, handler);
    }


    public static void deleteBlog(int uid, int authoruid, int id,
                                  AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("authoruid", authoruid);
        params.put("id", id);
        ApiHttpClient.post("action/api/userblog_delete", params, handler);
    }

    public static void deleteBlogComment(int uid, int blogid, int replyid,
                                         int authorid, int owneruid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("blogid", blogid);
        params.put("replyid", replyid);
        params.put("authorid", authorid);
        params.put("owneruid", owneruid);
        ApiHttpClient.post("action/api/blogcomment_delete", params, handler);
    }

    /**
     * 用户添加收藏
     *
     * @param uid   用户UID
     * @param objid 比如是新闻ID 或者问答ID 或者动弹ID
     * @param type  1:软件 2:话题 3:博客 4:新闻 5:代码
     */
    public static void addFavorite(int uid, int objid, int type,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("objid", objid);
        params.put("type", type);
        ApiHttpClient.post("action/api/favorite_add", params, handler);
    }

    public static void delFavorite(int uid, int objid, int type,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("objid", objid);
        params.put("type", type);
        ApiHttpClient.post("action/api/favorite_delete", params, handler);
    }

    public static void getSearchList(String catalog, String content,
                                     int pageIndex, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("content", content);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/search_list", params, handler);
    }

    public static void publicMessage(int uid, int receiver, String content,
                                     AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("receiver", receiver);
        params.put("content", content);
        ApiHttpClient.post("action/api/message_pub", params, handler);
    }

    public static void deleteMessage(int uid, int friendid,
                                     AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("friendid", friendid);
        ApiHttpClient.post("action/api/message_delete", params, handler);
    }

    public static void forwardMessage(int uid, String receiverName,
                                      String content, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("receiverName", receiverName);
        params.put("content", content);
        ApiHttpClient.post("action/api/message_pub", params, handler);
    }

    public static void getMessageList(int uid, int pageIndex,
                                      AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/message_list", params, handler);
    }

    public static void updatePortrait(int uid, File portrait,
                                      AsyncHttpResponseHandler handler) throws FileNotFoundException {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("portrait", portrait);
        ApiHttpClient.post("action/api/portrait_update", params, handler);
    }

    public static void getNotices(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getLoginUid());
        ApiHttpClient.get("action/api/user_notice", params, handler);
    }

    /**
     * 清空通知消息
     *
     * @param uid
     * @param type 1:@我的信息 2:未读消息 3:评论个数 4:新粉丝个数
     * @return
     * @throws AppException
     */
    public static void clearNotice(int uid, int type,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("type", type);
        ApiHttpClient.post("action/api/notice_clear", params, handler);
    }

    public static void singnIn(String url, AsyncHttpResponseHandler handler) {
        ApiHttpClient.getDirect(url, handler);
    }

    /**
     * 获取软件的动态列表
     *
     * @param softid
     * @param handler
     */
    public static void getSoftTweetList(int softid, int page,
                                        AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("project", softid);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/software_tweet_list", params, handler);
    }

    public static void checkUpdate(AsyncHttpResponseHandler handler) {

        String url = "appfile/latest/android";

        ApiHttpClient.get(url, handler);
    }

    /**
     * 查找用户
     *
     * @param username
     * @param handler
     */
    public static void findUser(String username,
                                AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("name", username);
        ApiHttpClient.get("action/api/find_user", params, handler);
    }

    /**
     * 获取活动列表
     *
     * @param pageIndex
     * @param uid       <= 0 近期活动 实际的用户ID 则获取用户参与的活动列表，需要已登陆的用户
     * @param handler
     */
    public static void getEventList(int pageIndex, int uid,
                                    AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("pageIndex", pageIndex);
        params.put("uid", uid);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/event_list", params, handler);
    }

    /**
     * 获取某活动已出席的人员列表
     *
     * @param eventId
     * @param pageIndex
     * @param handler
     */
    public static void getEventApplies(int eventId, int pageIndex,
                                       AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("pageIndex", pageIndex);
        params.put("event_id", eventId);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/event_attend_user", params, handler);
    }

    /**
     * 举报
     *
     * @param report
     * @param handler
     */
    public static void report(Report report, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("obj_id", report.getReportId());
        params.put("url", report.getLinkAddress());
        params.put("obj_type", report.getReason());
        if (report.getOtherReason() != null
                && !StringUtils.isEmpty(report.getOtherReason())) {
            params.put("memo", report.getOtherReason());
        } else {
            params.put("memo", "其他原因");
        }
        TLog.log("Test", report.getReportId() + "" + report.getLinkAddress()
                + report.getReason() + report.getOtherReason());
        ApiHttpClient.post("action/communityManage/report", params, handler);
    }

    /**
     * 摇一摇，随机数据
     *
     * @param handler
     */
    public static void shake(AsyncHttpResponseHandler handler) {
        shake(-1, handler);
    }

    /**
     * 摇一摇指定请求类型
     */
    public static void shake(int type, AsyncHttpResponseHandler handler) {
        String inter = "action/api/rock_rock";
        if (type > 0) {
            inter = (inter + "/?type=" + type);
        }
        ApiHttpClient.get(inter, handler);
    }

    /**
     * 活动报名
     *
     * @param data
     * @param handler
     */
    public static void eventApply(EventApplyData data,
                                  AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("event", data.getEvent());
        params.put("user", data.getUser());
        params.put("name", data.getName());
        params.put("gender", data.getGender());
        params.put("mobile", data.getPhone());
        params.put("company", data.getCompany());
        params.put("job", data.getJob());
        ApiHttpClient.post("action/api/event_apply", params, handler);
    }

    private static void uploadLog(String data, String report,
                                  AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("app", "1");
        params.put("report", report);
        params.put("msg", data);
        ApiHttpClient.post("action/api/user_report_to_admin", params, handler);
    }

    /**
     * BUG上报
     *
     * @param data
     * @param handler
     */
    public static void uploadLog(String data, AsyncHttpResponseHandler handler) {
        uploadLog(data, "1", handler);
    }

    /**
     * 反馈意见
     *
     * @param data
     * @param handler
     */
    public static void feedback(String data, AsyncHttpResponseHandler handler) {
        uploadLog(data, "2", handler);
    }


    /**
     * 获取team列表
     *
     * @param handler
     */
    public static void teamList(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getLoginUid());
        ApiHttpClient.get("action/api/team_list", params, handler);
    }

    /**
     * 获取team成员列表
     *
     * @param handler
     */
    public static void getTeamMemberList(int teamid,
                                         AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        ApiHttpClient.get("action/api/team_member_list", params, handler);
    }

    /**
     * 获取team成员个人信息
     *
     * @param handler
     */
    public static void getTeamUserInfo(String teamid, String uid,
                                       int pageIndex, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("uid", uid);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", 20);
        ApiHttpClient.get("action/api/team_user_information", params, handler);
    }

    /**
     * 获取我的任务中进行中、未完成、已完成等状态的数量
     */
    public static void getMyIssueState(String teamid, String uid,
                                       AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("uid", uid);
        ApiHttpClient.get("action/api/team_user_issue_information", params,
                handler);
    }

    /**
     * 获取指定用户的动态
     */
    public static void getUserDynamic(int teamid, String uid, int pageIndex,
                                      AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", 20);
        params.put("type", "git");
        params.put("uid", uid);
        ApiHttpClient.get("action/api/team_active_list", params, handler);
    }

    /**
     * 动态详情
     *
     * @param activeid
     * @param teamid
     * @param uid
     * @param handler
     */
    public static void getDynamicDetail(int activeid, int teamid, int uid,
                                        AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("uid", uid);
        params.put("activeid", activeid);
        ApiHttpClient.get("action/api/team_active_detail", params, handler);
    }

    /**
     * 获取指定用户的任务
     */
    public static void getMyIssue(String teamid, String uid, int pageIndex,
                                  String type, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("uid", uid);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", 20);
        params.put("state", type);
        params.put("projectid", "-1");
        ApiHttpClient.get("action/api/team_issue_list", params, handler);
    }

    /**
     * 获取指定周周报
     *
     * @param teamid
     * @param year
     * @param week
     * @param handler
     */
    public static void getDiaryFromWhichWeek(int teamid, int year, int week,
                                             AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("year", year);
        params.put("week", week);
        ApiHttpClient.get("action/api/team_diary_list", params, handler);
    }

    /**
     * 删除一个便签
     *
     * @param id  便签id
     * @param uid 用户id
     */
    public static void deleteNoteBook(int id, int uid,
                                      AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("id", id); // 便签id
        ApiHttpClient
                .get("action/api/team_stickynote_recycle", params, handler);
    }

    public static void getNoteBook(int uid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        ApiHttpClient.get("action/api/team_sticky_list", params, handler);
    }

    /**
     * 获取指定周报的详细信息
     *
     * @param teamid
     * @param diaryid
     * @param handler
     */
    public static void getDiaryDetail(int teamid, int diaryid,
                                      AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("diaryid", diaryid);
        ApiHttpClient.get("action/api/team_diary_detail", params, handler);
    }

    /**
     * diary评论列表
     *
     * @param teamid
     * @param diaryid
     * @param handler
     */
    public static void getDiaryComment(int teamid, int diaryid,
                                       AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("id", diaryid);
        params.put("type", "diary");
        params.put("pageIndex", 0);
        params.put("pageSize", "20");
        KJLoger.debug(teamid + "==getDiaryComment接口=" + diaryid);
        ApiHttpClient
                .get("action/api/team_reply_list_by_type", params, handler);
    }

    /**
     * 周报评论（以后可改为全局评论）
     *
     * @param uid
     * @param teamid
     * @param diaryId
     * @param content
     * @param handler
     */
    public static void sendComment(int uid, int teamid, int diaryId,
                                   String content, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("teamid", teamid);
        params.put("type", "118");
        params.put("tweetid", diaryId);
        params.put("content", content);
        ApiHttpClient.post("action/api/team_tweet_reply", params, handler);
    }

    /**
     * 客户端扫描二维码登陆
     *
     * @param url
     * @param handler
     * @return void
     * @author 火蚁 2015-3-13 上午11:45:47
     */
    public static void scanQrCodeLogin(String url,
                                       AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        String uuid = url.substring(url.lastIndexOf("=") + 1);
        params.put("uuid", uuid);
        ApiHttpClient.getDirect(url, handler);
    }


    //请求发送短信
    public static void getVertifyCode(String phoneNumber, AsyncHttpResponseHandler handler) {
        String url = "sms";
        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {
            jsonObject.put("phone", phoneNumber);
            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.post(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 手机登陆
     *
     * @param phoneNumber
     * @param vertifyCode
     * @param handler
     */
    public static void phoneLogin(String phoneNumber, String vertifyCode,
                                  AsyncHttpResponseHandler handler) {

        RequestParams params = new RequestParams();
        params.put("phone", phoneNumber);
        params.put("sms_code", vertifyCode);
        String url = "token";

        ApiHttpClient.get(url, params, handler);
    }

    public static void getGuestList(int page, String keyWords,
                                    AsyncHttpResponseHandler handler) {

        String url = "guests";
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        params.put("keywords", keyWords);
        ApiHttpClient.get(url, params, handler);


    }


//    public static void getHouseList(int pageIndex, String community, String status, AsyncHttpResponseHandler handler) {
//
//        String url = "houses";
//        RequestParams params = new RequestParams();
//        params.put("pageIndex", pageIndex);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        params.put("community", community);
//        //出租状态
//        params.put("status", status);
//        ApiHttpClient.get(url, params, handler);
//    }


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

    //获取七牛上传证书
    public static void getQiniuToken(AsyncHttpResponseHandler handler) {
        String url = "upload_token";
        RequestParams params = new RequestParams();

        ApiHttpClient.get(url, handler);

    }

    //获取七牛上传证书
    public static void getQiniuToken(String key, AsyncHttpResponseHandler handler) {
        String url = "upload_token";
        RequestParams params = new RequestParams();

        params.put("key", key);

        ApiHttpClient.get(url, params, handler);

    }

    public static void getdownloadUrl(String key, AsyncHttpResponseHandler handler) {

        String url = "download_url/" + key;

        ApiHttpClient.get(url, handler);

    }


    public static void getMyInformation(int uid, AsyncHttpResponseHandler handler) {

        String url = "host/" + uid;

        ApiHttpClient.get(url, handler);
    }


    public static void updateToken(AsyncHttpResponseHandler handler) {

        String url = "upgrade_token";

        ApiHttpClient.get(url, handler);

    }

    public static void getHouseManageData(AsyncHttpResponseHandler handler) {

        String url = "get_host_manager";

        ApiHttpClient.get(url, handler);

    }


    public static void getHouseList(int community_id, String houseStatus, int page, String keyWords, AsyncHttpResponseHandler handler) {

        String url = "search_room";

        RequestParams params = new RequestParams();

        if (community_id != 0)
            params.put("community_id", community_id);

        if (houseStatus.equals("已出租"))
            params.put("status", "0");
        else if (houseStatus.equals("未出租"))
            params.put("status", "1");
        else if (houseStatus.equals("游离租客"))
            params.put("status", "3");

        params.put("page", page + 1);

        params.put("pageSize", AppContext.PAGE_SIZE);

        params.put("keyWords", keyWords);

        ApiHttpClient.get(url, params, handler);

    }


    public static void getHouseInform(int house_id, AsyncHttpResponseHandler handler) {

        String url = "house/" + house_id;

        ApiHttpClient.get(url, handler);

    }

    public static void getRoomList(int house_id, AsyncHttpResponseHandler handler) {

        String url = "rooms";

        RequestParams params = new RequestParams();

        params.put("house_id", house_id);

        ApiHttpClient.get(url, params, handler);


    }

    public static void getRoomInform(int room_id, AsyncHttpResponseHandler handler) {

        String url = "room/" + room_id;

        ApiHttpClient.get(url, handler);

    }

    /**
     * 获取自己拥有房源的小区列表
     */
    public static void getCommunities(AsyncHttpResponseHandler handler) {

        String url = "communities";

        ApiHttpClient.get(url, handler);


    }

    public static void deleteCommunity(int community_id, AsyncHttpResponseHandler handler) {

        String url = "community/" + community_id;

        ApiHttpClient.delete(url, handler);

    }

    public static void addCommunity(String communityName, String province, String city, String district, AsyncHttpResponseHandler handler) {

        String url = "communities";
        RequestParams params = new RequestParams();

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {
            jsonObject.put("name", communityName);
            jsonObject.put("community_province", province);
            jsonObject.put("community_city", city);
            jsonObject.put("community_district", district);
            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
            ApiHttpClient.post(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void deletePicture(String key, AsyncHttpResponseHandler handler) {

        String url = "picture/" + key;
        ApiHttpClient.delete(url, handler);

    }

    public static void addHouse(House house, AsyncHttpResponseHandler handler) {

        String url = "houses";

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {
            jsonObject.put("address_province", house.getAddress_province());
            jsonObject.put("address_city", house.getAddress_city());
            jsonObject.put("address_district", house.getAddress_district());
            jsonObject.put("address_community_id", house.getAddress_community_id());
            jsonObject.put("address_building", house.getAddress_building());
            jsonObject.put("address_unit", house.getAddress_unit());
            jsonObject.put("address_room", house.getAddress_room());
            jsonObject.put("layout_bedroom", house.getLayout_bedroom());
            jsonObject.put("layout_livingroom", house.getLayout_livingroom());
            jsonObject.put("layout_bathroom", house.getLayout_bathroom());
            jsonObject.put("area", house.getArea());

            if (house.getThings_json() != null) {
                jsonObject.put("things_json", JSON.toJSONString(house.getThings_json()));
            } else {
                jsonObject.put("things_json", "{\"air_condition\": 0,\"bed\": 0,\"chair\": 0,\"chest\": 0,\"fire\": 0,\"ice_box\": 0,\"internet\": 0,\"table\": 0,\"television\": 0,\"washing_machine\": 0,\"water_heater\": 0}");
            }

            if (house.getPhoto() != null) {
                jsonObject.put("photo", JSON.toJSONString(house.getPhoto()));
            } else {
                jsonObject.put("photo", "[]");
            }
            jsonObject.put("room_num", house.getRoom_num());
            jsonObject.put("remark", house.getRemark());
            jsonObject.put("decoration_status", house.getDecoration_status());

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.post(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void addHouseContract(Contract contract, AsyncHttpResponseHandler handler) {

        String url = "house/contracts";

        StringEntity stringEntity;
        try {

            stringEntity = new StringEntity(JSON.toJSONStringWithDateFormat(contract, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat), "utf-8");

            ApiHttpClient.post(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void vertifyGuestUser(String phoneNumber, AsyncHttpResponseHandler handler) {

        String url = "guest";
        RequestParams params = new RequestParams();

        params.put("phone", phoneNumber);

        ApiHttpClient.get(url, params, handler);

    }


    public static void getGuestUser(int guest_id, AsyncHttpResponseHandler handler) {

        String url = "guest/" + guest_id;
        ApiHttpClient.get(url, handler);

    }

    public static void addGuestUser(Guest guest, AsyncHttpResponseHandler handler) {

        String url = "guests";

        StringEntity stringEntity;
        try {

            stringEntity = new StringEntity(JSON.toJSONString(guest), "utf-8");

            ApiHttpClient.post(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void updateGuestUser(Guest guest, AsyncHttpResponseHandler handler) {

        String url = "guest/" + guest.getId();

        StringEntity stringEntity;
        try {

            stringEntity = new StringEntity(JSON.toJSONString(guest), "utf-8");

            ApiHttpClient.put(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void updateOwnerInform(int house_id, String ownerName, String ownerPhone, AsyncHttpResponseHandler handler) {

        String url = "house/" + house_id;

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {

            jsonObject.put("owner_name", ownerName);
            jsonObject.put("owner_phone", ownerPhone);

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.put(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void updateHouseName(int house_id, Community community, String address_building, String address_unit, String address_room, AsyncHttpResponseHandler handler) {

        String url = "house/" + house_id;

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {

            jsonObject.put("address_community_id", community.getId());
            jsonObject.put("address_building", address_building);
            jsonObject.put("address_unit", address_unit);
            jsonObject.put("address_room", address_room);

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.put(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void updateHouseContractInform(House house, AsyncHttpResponseHandler handler) {

        String url = "house/contract/" + house.getContract().getId();

        // JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {

            stringEntity = new StringEntity(JSON.toJSONStringWithDateFormat(house.getContract(), "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat), "utf-8");

//            jsonObject.put("house_id", house.getId());
//            jsonObject.put("address_province", house.getAddress_province());
//            jsonObject.put("address_city", house.getAddress_city());
//            jsonObject.put("address_district", house.getAddress_district());
//            jsonObject.put("address_community_id", house.getAddress_community_id());
//            jsonObject.put("owner_name", house.getContract().getOwner_name());
//            jsonObject.put("owner_phone", house.getContract().getOwner_phone());
            //jsonObject.put("contract", JSON.toJSONStringWithDateFormat(house.getContract(), "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat));

            //stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.put(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void deleteHouse(int house_id, AsyncHttpResponseHandler handler) {

        String url = "house/" + house_id;

        ApiHttpClient.delete(url, handler);

    }

    public static void updateHouseInform(House house, AsyncHttpResponseHandler handler) {

        String url = "house/" + house.getId();

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {
            jsonObject.put("address_province", house.getAddress_province());
            jsonObject.put("address_city", house.getAddress_city());
            jsonObject.put("address_district", house.getAddress_district());
            jsonObject.put("address_community_id", house.getAddress_community_id());
            jsonObject.put("address_building", house.getAddress_building());
            jsonObject.put("address_unit", house.getAddress_unit());
            jsonObject.put("address_room", house.getAddress_room());
            jsonObject.put("layout_bedroom", house.getLayout_bedroom());
            jsonObject.put("layout_livingroom", house.getLayout_livingroom());
            jsonObject.put("layout_bathroom", house.getLayout_bathroom());
            jsonObject.put("area", house.getArea());

            if (house.getThings_json() != null) {
                jsonObject.put("things_json", JSON.toJSONString(house.getThings_json()));
            } else {
                //这步不可能执行的现在
                jsonObject.put("things_json", "{}");
            }

            if (house.getPhoto() != null) {
                jsonObject.put("photo", JSON.toJSONString(house.getPhoto()));
            } else {
                jsonObject.put("photo", "[]");
            }
            jsonObject.put("room_num", house.getRoom_num());
            jsonObject.put("remark", house.getRemark());
            jsonObject.put("decoration_status", house.getDecoration_status());
            jsonObject.put("rooms", house.getRooms());

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.put(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void addNewRoom(Room room, AsyncHttpResponseHandler handler) {

        String url = "rooms";

        // JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {

//            jsonObject.put("house_id", room.getHouse_id());
//            jsonObject.put("room_type", room.getRoom_type());
//            //jsonObject.put("area", room.getArea());
//            jsonObject.put("status", room.getStatus());
//            jsonObject.put("expect_rent_in_price", room.getExpect_rent_in_price());
//            jsonObject.put("expect_rent_in_deposit", room.getExpect_rent_in_deposit());
//            jsonObject.put("expect_rent_in_payment", room.getExpect_rent_in_payment());
//            jsonObject.put("contract", JSON.toJSON(room.getContract()));

            PropertyFilter filter = new PropertyFilter() {
                public boolean apply(Object source, String name, Object value) {
                    if ("things_json".equals(name) || "photo".equals(name)) {
                        return false;
                    }

                    return true;
                }
            };


            stringEntity = new StringEntity(JSON.toJSONString(room, filter), "utf-8");

            ApiHttpClient.post(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void updateNewRoom(Room room, AsyncHttpResponseHandler handler) {

        String url = "room/" + room.getId();

        StringEntity stringEntity;
        try {

            PropertyFilter filter = new PropertyFilter() {
                public boolean apply(Object source, String name, Object value) {
                    if ("things_json".equals(name) || "photo".equals(name)) {
                        return false;
                    }

                    return true;
                }
            };

            stringEntity = new StringEntity(JSON.toJSONString(room, filter), "utf-8");

            ApiHttpClient.put(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void addRoomContract(Contract contract, AsyncHttpResponseHandler handler) {

        String url = "room/contracts";

        StringEntity stringEntity;
        try {

            stringEntity = new StringEntity(JSON.toJSONStringWithDateFormat(contract, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat), "utf-8");

            ApiHttpClient.post(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void releaseRoom(int room_id, AsyncHttpResponseHandler handler) {

        String url = "room/release/" + room_id;

        ApiHttpClient.post(url, handler);

    }


    public static void updateRoomInform(Room room, AsyncHttpResponseHandler handler) {

        String url = "room/" + room.getId();

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {
            jsonObject.put("expect_rent_in_price", room.getExpect_rent_in_price());
            jsonObject.put("expect_rent_in_deposit", room.getExpect_rent_in_deposit());
            jsonObject.put("expect_rent_in_payment", room.getExpect_rent_in_payment());
            jsonObject.put("status", room.getStatus());

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.put(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void updateRoomContractInform(Room room, AsyncHttpResponseHandler handler) {

        String url = "room/contract/" + room.getContract().getId();

        StringEntity stringEntity;
        try {

            stringEntity = new StringEntity(JSON.toJSONStringWithDateFormat(room.getContract(), "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat), "utf-8");

            // System.out.println(JSON.toJSONStringWithDateFormat(room.getContract(), "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat));


            ApiHttpClient.put(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void deleteRoomContract(int contract_id, AsyncHttpResponseHandler handler) {

        String url = "room/contract/" + contract_id;

        ApiHttpClient.delete(url, handler);

    }

    public static void updateRoomInform_1(Room room, AsyncHttpResponseHandler handler) {
        String url = "room/" + room.getId();

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {
            jsonObject.put("room_type", room.getRoom_type());
            jsonObject.put("area", room.getArea());

            if (room.getThings_json() != null) {
                jsonObject.put("things_json", JSON.toJSONString(room.getThings_json()));
            } else {
                jsonObject.put("things_json", "{}");
            }

            if (room.getPhoto() != null) {
                jsonObject.put("photo", JSON.toJSONString(room.getPhoto()));
            } else {
                jsonObject.put("photo", "[]");
            }
            jsonObject.put("remark", room.getRemark());

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.put(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void deleteRoom(int room_id, AsyncHttpResponseHandler handler) {

        String url = "room/" + room_id;

        ApiHttpClient.delete(url, handler);

    }

    public static void getGuestTaxesRemindList(AsyncHttpResponseHandler handler) {

        String url = "room/remind_pay";

        ApiHttpClient.get(url, handler);

    }


    public static void getHostTaxesRemindList(AsyncHttpResponseHandler handler) {

        String url = "house/remind_pay";

        ApiHttpClient.get(url, handler);

    }

    public static void getGuestTaxesDueDate(AsyncHttpResponseHandler handler) {

        String url = "room/due_date";

        ApiHttpClient.get(url, handler);

    }

    public static void getHostTaxesDueDate(AsyncHttpResponseHandler handler) {

        String url = "house/due_date";

        ApiHttpClient.get(url, handler);

    }

    public static void confirmGuestTaxesPaid(int room_id, AsyncHttpResponseHandler handler) {

        String url = "room/paid/contract/" + room_id;

        ApiHttpClient.post(url, handler);

    }


    public static void confirmHostTaxesPaid(int house_id, AsyncHttpResponseHandler handler) {

        String url = "house/paid/contract/" + house_id;

        ApiHttpClient.post(url, handler);

    }

    public static void getIncreasedHouseList(int page, AsyncHttpResponseHandler handler) {

        String url = "house/new_house_list";

        RequestParams params = new RequestParams();

        params.put("page", page + 1);

        params.put("pageSize", AppContext.PAGE_SIZE);

        ApiHttpClient.get(url, handler);

    }

    public static void getTaxesPaidList(int page, AsyncHttpResponseHandler handler) {

        String url = "room/taxes_paid_list";

        RequestParams params = new RequestParams();

        params.put("page", page + 1);

        params.put("pageSize", AppContext.PAGE_SIZE);

        ApiHttpClient.get(url, handler);

    }

    public static void updateUserProtrait(int host_id, String key, AsyncHttpResponseHandler handler) {

        String url = "host/" + host_id;

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {

            jsonObject.put("face_photo", key);

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.put(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void updateHostName(int host_id, String name, AsyncHttpResponseHandler handler) {

        String url = "host/" + host_id;

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {

            jsonObject.put("name", name);

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.put(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void updateApartName(int host_id, String apartName, AsyncHttpResponseHandler handler) {

        String url = "host/" + host_id;

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {

            jsonObject.put("apart_name", apartName);

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.put(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void updatePhone(int host_id, String phone, AsyncHttpResponseHandler handler) {

        String url = "host/" + host_id;

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {

            jsonObject.put("phone", phone);

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.put(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void getTaxesPaidTodayList(int page, AsyncHttpResponseHandler handler) {

        String url = "taxes/taxes_paid_day_list";

        RequestParams params = new RequestParams();

        params.put("page", page + 1);

        params.put("pageSize", AppContext.PAGE_SIZE);

        ApiHttpClient.get(url, params, handler);

    }


    public static void getHouseTaxesDetail(int year, int month, int house_id, int page, AsyncHttpResponseHandler handler) {

        String url = "tax/year/" + year + "/month/" + month + "/house/" + house_id;

        RequestParams params = new RequestParams();

        params.put("page", page + 1);

        params.put("pageSize", AppContext.PAGE_SIZE);

        ApiHttpClient.get(url, handler);

    }

    public static void getTaxesPaidMonthList(int year, int month, int page, AsyncHttpResponseHandler handler) {

        String url = "tax/year/" + year + "/month/" + month;

        RequestParams params = new RequestParams();

        params.put("page", page + 1);

        params.put("pageSize", AppContext.PAGE_SIZE);

        ApiHttpClient.get(url, handler);

    }

    public static void getTaxesBillById(int payment_id, AsyncHttpResponseHandler handler) {

        String url = "tax/" + payment_id;

        ApiHttpClient.get(url, handler);

    }

    public static void getMoneyManage(AsyncHttpResponseHandler handler) {

        String url = "get_money_manager";

        ApiHttpClient.get(url, handler);

    }


    public static void getMonthExpend(int year, int month, AsyncHttpResponseHandler handler) {

        String url = "expend/year/" + year + "/month/" + month;

        ApiHttpClient.get(url, handler);

    }

    public static void getExpendByType(int house_id, int year, int month, int expend_type, int page, AsyncHttpResponseHandler handler) {


        String url = "expend/year/" + year + "/month/" + month + "/expend_type/" + expend_type;

        RequestParams params = new RequestParams();

        if (house_id != 0)
            url += "/house/" + house_id;

        params.put("page", page + 1);
        params.put("pageSize", AppContext.PAGE_SIZE);

        ApiHttpClient.get(url, params, handler);

    }


    public static void getExpendDetail(int expend_id, AsyncHttpResponseHandler handler) {

        String url = "expend/" + expend_id;

        ApiHttpClient.get(url, handler);
    }

    public static void getCurrentMonthBill(int year, int month, int page, AsyncHttpResponseHandler handler) {

        String url = "bill/year/" + year + "/month/" + month;

        RequestParams params = new RequestParams();
        params.put("page", page + 1);
        params.put("pageSize", AppContext.PAGE_SIZE);

        ApiHttpClient.get(url, params, handler);
    }

    public static void addExpend(Expend expend, AsyncHttpResponseHandler handler) {

        String url = "expends";

        StringEntity stringEntity;
        try {


            stringEntity = new StringEntity(JSON.toJSONStringWithDateFormat(expend, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat), "utf-8");


            ApiHttpClient.post(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void getEmptyHouseList(int page, AsyncHttpResponseHandler handler) {

        String url = "rooms/empty";

        RequestParams params = new RequestParams();

        params.put("page", page + 1);
        params.put("pageSize", AppContext.PAGE_SIZE);

        ApiHttpClient.get(url, params, handler);
    }

    public static void getFreeGuestInform(int guest_id, AsyncHttpResponseHandler handler) {

        String url = "guest/free/" + guest_id;

        ApiHttpClient.get(url, handler);
    }


    public static void getHouseBill(int house_id, int year, int month, AsyncHttpResponseHandler handler) {

        String url = "bill/year/" + year + "/month/" + month + "/house/" + house_id;

        ApiHttpClient.get(url, handler);

    }

    public static void addDiscount(Discount discount, AsyncHttpResponseHandler handler) {

        String url = "discounts";

        StringEntity stringEntity;
        try {

            stringEntity = new StringEntity(JSON.toJSONStringWithDateFormat(discount, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat), "utf-8");

            ApiHttpClient.post(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void getDiscountList(int payment_id, int page, AsyncHttpResponseHandler handler) {

        String url = "discounts";

        RequestParams params = new RequestParams();

        if (payment_id != 0)
            params.put("payment_id", payment_id);

        params.put("page", page + 1);
        params.put("pageSize", AppContext.PAGE_SIZE);

        ApiHttpClient.get(url, params, handler);

    }

    public static void getDiscountDetail(int discount_id, AsyncHttpResponseHandler handler) {

        String url = "discount/" + discount_id;

        ApiHttpClient.get(url, handler);

    }

    public static void addBankCard(String bankCard, String name, AsyncHttpResponseHandler handler) {

        String url = "bankCards";

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {

            jsonObject.put("card_number", bankCard);
            jsonObject.put("host", name);

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.post(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void getMyWalletInform(AsyncHttpResponseHandler handler) {

        String url = "wallet";

        ApiHttpClient.get(url, handler);

    }

    public static void hasBankCard(AsyncHttpResponseHandler handler) {

        String url = "hasBankCard";

        ApiHttpClient.get(url, handler);

    }

    public static void getBalance(AsyncHttpResponseHandler handler, int id, float amount) {

        String url = "withdraws";


        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {

            jsonObject.put("bank_card_id", id);
            jsonObject.put("amount", amount);
            jsonObject.put("time", StringUtils.getDateString(new Date()));

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.post(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void deleteBankCard(AsyncHttpResponseHandler handler, int bankCardId) {

        String url = "bankCard/" + bankCardId;

        ApiHttpClient.delete(url, handler);

    }

    public static void getBankCardList(AsyncHttpResponseHandler handler) {

        String url = "bankCards";

        ApiHttpClient.get(url, handler);


    }

    public static void getDealList(AsyncHttpResponseHandler handler, int page) {

        String url = "deals";

        RequestParams params = new RequestParams();

        params.put("page", page + 1);

        params.put("pageSize", AppContext.PAGE_SIZE);

        ApiHttpClient.get(url, params, handler);


    }

    public static void getDealDetail(AsyncHttpResponseHandler handler, int id, int type) {

        String url = "";
        //提现
        if (type == 1) {
            url = "deal_detail/" + id;
            //收租
        } else if (type == 0) {
            url = "payment/" + id;
        }
        ApiHttpClient.get(url, handler);


    }

    public static void getNotificationList(int page, AsyncHttpResponseHandler handler) {

        String url = "notifications";

        RequestParams params = new RequestParams();

        params.put("page", page + 1);

        params.put("pageSize", AppContext.PAGE_SIZE);

        ApiHttpClient.get(url, handler);


    }


    public static void geNotificationDetail(AsyncHttpResponseHandler handler, int id) {

        String url = "notification/" + id;

        ApiHttpClient.get(url, handler);


    }

    public static void confirmTaxesPaid(AsyncHttpResponseHandler handler, int id) {

        String url = "payment/confirm/" + id;

        ApiHttpClient.post(url, handler);

    }


    public static void confirmTaxesPaid(AsyncHttpResponseHandler handler, int id, int contract_id) {

        String url = "payment/confirm/" + id;

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {

            jsonObject.put("contract_id", contract_id);
            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
            ApiHttpClient.post(url, stringEntity, "application/json", handler);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void getTweetList(int page,
                                    AsyncHttpResponseHandler handler) {
        String url = "tweets";

        RequestParams params = new RequestParams();
        params.put("page", page + 1);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get(url, params, handler);
    }

    public static void pubLikeTweet(int tweetId, int authorId,
                                    AsyncHttpResponseHandler handler) {

        String url = "tweet_like/" + tweetId;

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {

            jsonObject.put("tweetid", tweetId);
            jsonObject.put("like_user_id", AppContext.getInstance().getLoginUid());
            jsonObject.put("tweet_author_id", authorId);

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.post(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pubUnLikeTweet(int tweetId, int authorId,
                                      AsyncHttpResponseHandler handler) {

        String url = "tweet_unlike/" + tweetId;

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {

            jsonObject.put("tweetid", tweetId);
            jsonObject.put("like_user_id", AppContext.getInstance().getLoginUid());
            jsonObject.put("tweet_author_id", authorId);

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.post(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pubTweet(Tweet tweet, AsyncHttpResponseHandler handler) {

        String url = "tweets";

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {
//            stringEntity = new StringEntity(JSON.toJSONStringWithDateFormat(tweet, "yyyy-MM-dd'T'HH:mm:ssZ", SerializerFeature.WriteDateUseDateFormat), "utf-8");

            jsonObject.put("author", tweet.getAuthor());
            jsonObject.put("author_id", tweet.getAuthor_id());
            jsonObject.put("body", tweet.getBody());
            String date = JSON.toJSONStringWithDateFormat(tweet.getPublish_date(), "yyyy-MM-dd'T'HH:mm:ssZ", SerializerFeature.WriteDateUseDateFormat);
            jsonObject.put("publish_date", date.substring(1, date.length() - 1));
            jsonObject.put("urls", JSON.toJSON(tweet.getUrls()));

            //System.out.println(jsonObject.toString());

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.post(url, stringEntity, "application/json", handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getCommentList(int id, int page, AsyncHttpResponseHandler handler) {

        String url = "comments";

        RequestParams params = new RequestParams();
        params.put("tweet_id", id);
        params.put("page", page + 1);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get(url, params, handler);
    }

    public static void getTweetDetail(int id, AsyncHttpResponseHandler handler) {

        String url = "tweet/" + id;

        ApiHttpClient.get(url, handler);
    }

    public static void deleteComment(int id, int replyid, int authorid, AsyncHttpResponseHandler handler) {

        String url = "comment/" + replyid;

        JSONObject jsonObject = new JSONObject();
//        StringEntity stringEntity;
//        try {
//
//            jsonObject.put("id", id);
//            jsonObject.put("replyid", replyid);
//            jsonObject.put("authorid", authorid);
//
//            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
//
//            ApiHttpClient.post(url, stringEntity, "application/json", handler);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        ApiHttpClient.delete(url, handler);

    }

    public static void replyComment(int id, int replyid, int authorid, int uid, String content, AsyncHttpResponseHandler handler) {
        String url = "comments";

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {

            jsonObject.put("tweet_id", id);
            jsonObject.put("author_id", uid);
            jsonObject.put("content", content);
            jsonObject.put("comment_id", replyid);
            jsonObject.put("comment_author_id", authorid);

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.post(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发表评论
     *
     * @param id      动态id
     * @param uid     当天登陆用户的UID
     * @param content 发表的评论内容
     * @param handler
     */
    public static void publicComment(int id, int uid,
                                     String content, AsyncHttpResponseHandler handler) {

        String url = "comments";

        JSONObject jsonObject = new JSONObject();
        StringEntity stringEntity;
        try {

            jsonObject.put("tweet_id", id);
            jsonObject.put("author_id", uid);
            jsonObject.put("content", content);

            stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            ApiHttpClient.post(url, stringEntity, "application/json", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void deleteDiscount(int discount_id, AsyncHttpResponseHandler handler) {

        String url = "discount/" + discount_id;

        ApiHttpClient.delete(url, handler);

    }

    public static void deleteTweet(int uid, int tweetid,
                                   AsyncHttpResponseHandler handler) {
        String url = "tweet/" + tweetid;

        ApiHttpClient.delete(url, handler);
    }


}
