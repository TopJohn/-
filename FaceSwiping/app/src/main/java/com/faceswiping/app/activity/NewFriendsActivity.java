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
import com.faceswiping.app.bean.FriendBean;
import com.faceswiping.app.bean.NewFriendBean;
import com.faceswiping.app.bean.Result;
import com.faceswiping.app.util.TDevice;
import com.faceswiping.app.widget.EmptyLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.faceswiping.app.interf.*;
import org.apache.http.Header;

import java.util.ArrayList;

import butterknife.InjectView;

public class NewFriendsActivity extends BaseActivity implements ListItemClickHelp{

    @InjectView(R.id.new_friends_listView)
    ListView friendsListView;

    @InjectView(R.id.error_layout)
    EmptyLayout emptyLayout;

    private NewFriendAdapter adapter;

    private NewFriendBean currentReceiveFriend;
    private ArrayList<NewFriendBean> mDatas = new ArrayList<NewFriendBean>();

    private ArrayList<NewFriendBean> mDatas1 = new ArrayList<NewFriendBean>();
    private ArrayList<NewFriendBean> mDatas2 = new ArrayList<NewFriendBean>();


    private ActionBar actionBar;


    private AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            emptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);

            try {

                String response = new String(responseBody);
                System.out.println(response);
                Result<ArrayList<NewFriendBean>> result = new Gson().fromJson(response, new TypeToken<
                       Result<ArrayList<NewFriendBean>>>() {
                }.getType());

                if (result.getErrorcode() == 0) {

                    mDatas1 = result.getUserAddFriendsRequests();
                    mDatas2 = result.getFriends();

                    if (mDatas1.size() > 0){
                        for (int i = 0; i < mDatas1.size(); i++) {
                            mDatas.add(mDatas1.get(i));
                        }
                    }

                    if (mDatas2.size() > 0){
                        for (int i = 0; i < mDatas2.size(); i++) {
                            mDatas.add(mDatas2.get(i));
                        }
                    }

                    adapter = new NewFriendAdapter();
                    adapter.setData(mDatas);
                    adapter.setCallBack(NewFriendsActivity.this);
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

    private AsyncHttpResponseHandler handler1 = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {

                String response = new String(responseBody);
                System.out.println(response);
                Result<ArrayList<FriendBean>> result = new Gson().fromJson(response, new TypeToken<
                        Result<ArrayList<FriendBean>>>() {
                }.getType());

                if (result.getErrorcode() == 0) {
                    hideWaitDialog();
                    //receive success
                    mDatas.remove(currentReceiveFriend);
                    NewFriendBean receivedFriend = new NewFriendBean();
                    receivedFriend.setName(currentReceiveFriend.getUserName());
                    receivedFriend.setHeaderImageUrl(currentReceiveFriend.getHeadImageUrl());
                    mDatas.add(0, receivedFriend);
                    adapter.notifyDataSetChanged();
                    AppContext.showToastShort(R.string.tip_receive_success);
                } else {
                    hideWaitDialog();
                    AppContext.showToastShort(R.string.tip_receive_fail);
                }


            } catch (Exception e) {
                e.printStackTrace();
                onFailure(statusCode, headers, responseBody, e);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    };

    //receive button click
    @Override
    public void onClick(View item, View widget, int position, int which) {
        showWaitDialog("接受中...");
        currentReceiveFriend = mDatas.get(position);
        System.out.println("position:" + position);
        FaceSwipingApi.getFriendsAccept(currentReceiveFriend.getUserId(),handler1);
    }
}
