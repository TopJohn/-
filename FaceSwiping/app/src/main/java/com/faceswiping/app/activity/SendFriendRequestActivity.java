package com.faceswiping.app.activity;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.faceswiping.app.R;
import com.faceswiping.app.base.BaseActivity;

import butterknife.InjectView;

public class SendFriendRequestActivity extends BaseActivity {

    @InjectView(R.id.send_request_requestInfo)
    EditText inputInformation;

    @InjectView(R.id.send_request_friendImage)
    ImageView friendImage;


    private ActionBar actionBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.actionbar_custom_view);
        View view = mActionBar.getCustomView();
        TextView textView = (TextView) view.findViewById(R.id.actionbar_title);
        textView.setText("好友验证");

        return true;
    }

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
