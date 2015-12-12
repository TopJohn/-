package com.faceswiping.app.activity;


import com.faceswiping.app.R;
import com.faceswiping.app.fragment.ChatFragment;
import com.faceswiping.app.fragment.FriendFragment;
import com.faceswiping.app.fragment.MeFragment;

public enum MainTab {

    NEWS(0, R.string.main_tab_name_news, R.drawable.tab_icon_new,
            ChatFragment.class),

    TWEET(1, R.string.main_tab_name_tweet, R.drawable.tab_icon_tweet,
            FriendFragment.class),


    ME(4, R.string.main_tab_name_my, R.drawable.tab_icon_me,
            MeFragment.class);

    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
