package com.faceswiping.app.activity;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.faceswiping.app.R;
import com.faceswiping.app.base.BaseActivity;

import butterknife.InjectView;

public class SendFriendRequestActivity extends BaseActivity {

    @InjectView(R.id.send_request_requestInfo)
    EditText inputInformation;

    @InjectView(R.id.send_request_friendImage)
    ImageView friendImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_friend_request);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_friend_request;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.friend_request_actionBar_title;
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
        switch (id){

        }
    }
}
