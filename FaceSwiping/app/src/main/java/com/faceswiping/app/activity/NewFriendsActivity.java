package com.faceswiping.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.faceswiping.app.R;
import com.faceswiping.app.adapter.NewFriendAdapter;
import com.faceswiping.app.base.BaseActivity;
import com.faceswiping.app.bean.NewFriendBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class NewFriendsActivity extends BaseActivity {

    @InjectView(R.id.new_friends_listView)
    ListView friendsListView;

    private NewFriendAdapter adapter;

    private ArrayList<NewFriendBean> mDatas;

    private String json = "[{\n" +
            "  \"name\": \"新的好友\",\n" +
            "  \"headImageUrl\": \"http://7xp4qa.com1.z0.glb.clouddn.com/1.png\",\n" +
            "  \"content\": \"TianTian通过“刷脸加好友”加你为好友\",\n" +
            "  \"fromResource\": \"来源:刷脸加好友\"\n" +
            "  \"isAddedFriend\": \"0\"\n" +
            "  \"groupImageUrl\": \"http://7xp4qa.com1.z0.glb.clouddn.com/0.png\"\n" +
            "}, {\n" +
            "  \"name\": \"RedLight\",\n" +
            "  \"headImageUrl\": \"http://7xp4qa.com1.z0.glb.clouddn.com/2.png\",\n" +
            "  \"content\": \"我想要你和你一起去！\",\n" +
            "  \"fromResource\": \"来源:刷脸加好友\"\n" +
            "  \"isAddedFriend\": \"1\"\n" +
            "  \"groupImageUrl\": \"http://7xp4qa.com1.z0.glb.clouddn.com/0.png\"\n" +
            "},{\n" +
            "  \"name\": \"Yanxin\",\n" +
            "  \"headImageUrl\": \"http://7xp4qa.com1.z0.glb.clouddn.com/6.png\",\n" +
            "  \"content\": \"我想要和你一起去上海参加比赛！\",\n" +
            "  \"fromResource\": \"来源:刷脸加好友\"\n" +
            "  \"isAddedFriend\": \"0\"\n" +
            "  \"groupImageUrl\": \"http://7xp4qa.com1.z0.glb.clouddn.com/0.png\"\n" +
            "}]\n";

    private ActionBar actionBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.actionbar_custom_view);
        View view = mActionBar.getCustomView();
        TextView textView = (TextView) view.findViewById(R.id.actionbar_title);
        textView.setText("新的朋友");

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_friends;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        adapter = new NewFriendAdapter();

        mDatas = new Gson().fromJson(json, new TypeToken<List<NewFriendBean>>() {
        }.getType());

        adapter.setData(mDatas);
        friendsListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

        }
    }
}
