package com.faceswiping.app.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.faceswiping.app.AppContext;
import com.faceswiping.app.R;
import com.faceswiping.app.api.remote.FaceSwipingApi;
import com.faceswiping.app.base.BaseActivity;
import com.faceswiping.app.bean.Result;
import com.faceswiping.app.util.TDevice;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import butterknife.InjectView;

public class SendFriendRequestActivity extends BaseActivity {

    @InjectView(R.id.send_request_requestInfo)
    TextView inputInformation;

    @InjectView(R.id.send_request_friendImage)
    ImageView friendImage;

    private int id;

    private String key;

    private String url;

    private ActionBar actionBar;

    private AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            hideWaitDialog();

            try {

                String response = new String(responseBody);

                System.out.println(response);

                Result result = new Gson().fromJson(response, Result.class);

                if (result.getErrorcode() == 0) {

                    finish();

                } else {

                    AppContext.showToastShort(result.getErrormsg());

                }


            } catch (Exception e) {
                e.printStackTrace();
                onFailure(statusCode, headers, responseBody, e);
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            AppContext.showToastShort("发送失败～！");


        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.actionbar_custom_view);
        View view = mActionBar.getCustomView();
        TextView textView = (TextView) view.findViewById(R.id.actionbar_title);
        textView.setText("好友验证");

        getMenuInflater().inflate(R.menu.send_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.actionbar_finish) {

            sendRequestData();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        key = getIntent().getStringExtra("key");
        id = getIntent().getIntExtra("id", 0);
        url = getIntent().getStringExtra("url");

        System.out.println(url);

        ImageLoader.getInstance().displayImage(url, friendImage, MarkNewFriendsActivity.optionsImage);

    }

    @Override
    public void initData() {

    }

    private void sendRequestData() {

        if (TDevice.hasInternet()) {
            showWaitDialog("发送中....");
            FaceSwipingApi.pubFriendRequest(id, key, handler);

        } else {

            AppContext.showToastShort(R.string.tip_no_internet);

        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

        }
    }
}
