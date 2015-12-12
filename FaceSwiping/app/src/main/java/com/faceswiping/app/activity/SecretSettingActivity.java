package com.faceswiping.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faceswiping.app.R;
import com.faceswiping.app.base.BaseActivity;

import butterknife.InjectView;

public class SecretSettingActivity extends BaseActivity {

    @InjectView(R.id.secret_setting_openState)
    TextView openStateTextView;

    @InjectView(R.id.secret_setting_openLayout)
    LinearLayout openOrCloseLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_secret_setting;
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
            case R.id.secret_setting_openLayout:

                break;
        }
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.secret_setting_actionBar_title;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }
}
