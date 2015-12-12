package com.faceswiping.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faceswiping.app.R;
import com.faceswiping.app.base.BaseActivity;

import butterknife.InjectView;

public class AddNewFriendActivity extends BaseActivity {

    @InjectView(R.id.add_friend_faceAddFriendLayout)
    LinearLayout addFriendLayout;

    @InjectView(R.id.add_friend_faceAddFriendState)
    TextView addFriendState;

    @InjectView(R.id.add_friend_faceAddContactFriendLayout)
    LinearLayout addContactFriend;

    @InjectView(R.id.add_friend_faceAddWeiXinFriendLayout)
    LinearLayout addWeiXinFriend;

    @InjectView(R.id.add_friend_faceAddQQFriendLayout)
    LinearLayout addQQFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_new_friend;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.add_newFriend_actionBar_title;
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

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

        }
    }
}
