package com.faceswiping.app.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.faceswiping.app.AppContext;
import com.faceswiping.app.R;
import com.faceswiping.app.adapter.NewFriendAdapter;
import com.faceswiping.app.api.remote.FaceSwipingApi;
import com.faceswiping.app.base.BaseActivity;
import com.faceswiping.app.bean.NewFriendBean;
import com.faceswiping.app.bean.Result;
import com.faceswiping.app.util.TDevice;
import com.faceswiping.app.widget.EmptyLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;

import butterknife.InjectView;

public class NewFriendsActivity extends BaseActivity {



    @InjectView(R.id.new_friends_listView)
    ListView friendsListView;

    @InjectView(R.id.error_layout)
    EmptyLayout emptyLayout;

    private NewFriendAdapter adapter;

    private ArrayList<NewFriendBean> mDatas;


    private ActionBar actionBar;


    private AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            emptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);

            try {

                String response = new String(responseBody);

                Result<ArrayList<NewFriendBean>> result = new Gson().fromJson(response, new TypeToken<ArrayList<NewFriendBean>>() {
                }.getType());

                if (result.getErrorcode() == 0) {

                    mDatas = result.getData();

                    adapter = new NewFriendAdapter();
                    adapter.setData(mDatas);
                    friendsListView.setAdapter(adapter);

                } else {

                    emptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);

                }


            } catch (Exception e) {
                e.printStackTrace();
                onFailure(statusCode, headers, responseBody, e);
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            emptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);

        }
    };


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

        emptyLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestData();
            }
        });

    }

    @Override
    public void initData() {

        sendRequestData();

    }



    private void sendRequestData() {

        if (TDevice.hasInternet()) {
            emptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
            FaceSwipingApi.getNewFriendList(handler);

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
