package com.faceswiping.app.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faceswiping.app.AppConfig;
import com.faceswiping.app.AppContext;
import com.faceswiping.app.R;
import com.faceswiping.app.base.BaseActivity;
import com.faceswiping.app.util.UIHelper;

import butterknife.InjectView;

public class SecretSettingActivity extends BaseActivity {

    @InjectView(R.id.secret_setting_openState)
    TextView openStateTextView;

    @InjectView(R.id.secret_setting_openLayout)
    LinearLayout openOrCloseLayout;

    private ActionBar actionBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.actionbar_custom_view);
        View view = mActionBar.getCustomView();
        TextView textView = (TextView) view.findViewById(R.id.actionbar_title);
        textView.setText("隐私");

        return true;
    }

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

        actionBar = getSupportActionBar();
        openOrCloseLayout.setOnClickListener(this);

//        if (AppContext.get(AppConfig.KEY_DOUBLE_CLICK_EXIT, false)) {
//            openStateTextView.setText("已开通");
//        } else {
//            openStateTextView.setText("未开通");
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppContext.get(AppConfig.KEY_DOUBLE_CLICK_EXIT, false)) {
            openStateTextView.setText("已开通");
        } else {
            openStateTextView.setText("未开通");
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.secret_setting_openLayout:

                UIHelper.showFaceIdentificationActivity(this);

                break;
        }
    }


    @Override
    protected boolean hasBackButton() {
        return true;
    }
}
