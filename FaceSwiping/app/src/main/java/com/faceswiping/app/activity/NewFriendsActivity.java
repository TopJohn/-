package com.faceswiping.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.faceswiping.app.R;
import com.faceswiping.app.base.BaseActivity;
import com.faceswiping.app.R;

import butterknife.InjectView;

public class NewFriendsActivity extends BaseActivity {

    @InjectView(R.id.new_friends_listView)
    ListView friendsListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_friends;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.new_friends_actionBar_title;
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
