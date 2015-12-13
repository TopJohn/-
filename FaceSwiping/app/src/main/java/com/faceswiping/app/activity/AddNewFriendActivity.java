package com.faceswiping.app.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faceswiping.app.AppContext;
import com.faceswiping.app.R;
import com.faceswiping.app.base.BaseActivity;
import com.faceswiping.app.util.UIHelper;

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

    private ActionBar actionBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.actionbar_custom_view);
        View view = mActionBar.getCustomView();
        TextView textView = (TextView) view.findViewById(R.id.actionbar_title);
        textView.setText("添加好友");

        return true;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    public void initView() {

        addFriendLayout.setOnClickListener(this);

        if (AppContext.getInstance().getLoginUser().getSecret() == 0) {
            addFriendState.setVisibility(View.VISIBLE);
        } else {
            addFriendState.setVisibility(View.GONE);
        }


    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.add_friend_faceAddFriendLayout:

                if (AppContext.getInstance().getLoginUser().getSecret() == 0) {
                    UIHelper.showFaceIdentificationActivity(this);
                } else {


                }

                break;
            default:

                break;

        }
    }
}
