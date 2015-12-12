package com.faceswiping.app.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faceswiping.app.R;
import com.faceswiping.app.base.BaseActivity;

import butterknife.InjectView;

public class FaceIdentificationActivity extends BaseActivity {

    @InjectView(R.id.face_identification_userImage)
    ImageView identificationUserImage;

    @InjectView(R.id.face_identification_openLayout)
    LinearLayout identificationOpenButton;

    @InjectView(R.id.face_identification_IdentificationLayout)
    LinearLayout identificationButton;

    @InjectView(R.id.face_identification_IdentificationState)
    TextView identificationState;

    private ActionBar actionBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.actionbar_custom_view);
        View view = mActionBar.getCustomView();
        TextView textView = (TextView) view.findViewById(R.id.actionbar_title);
        textView.setText("刷脸加好友");

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_face_identification;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }


    @Override
    public void initView() {
        actionBar = getSupportActionBar();

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.face_identification_openLayout:

                break;

            case R.id.face_identification_IdentificationLayout:

                break;
        }
    }
}
