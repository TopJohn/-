package com.faceswiping.app.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.faceswiping.app.AppContext;
import com.faceswiping.app.R;
import com.faceswiping.app.adapter.MarkedFriendAdapter;
import com.faceswiping.app.api.remote.FaceSwipingApi;
import com.faceswiping.app.base.BaseActivity;
import com.faceswiping.app.bean.MarkedItem;
import com.faceswiping.app.bean.Result;
import com.faceswiping.app.util.TDevice;
import com.faceswiping.app.widget.EmptyLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.apache.http.Header;

import java.util.ArrayList;

import butterknife.InjectView;

public class MarkNewFriendsActivity extends BaseActivity {

    @InjectView(R.id.mark_new_friends_image)
    ImageView friendsImageView;

    @InjectView(R.id.mark_new_friends_listView)
    ListView friendsListView;

    @InjectView(R.id.error_layout)
    EmptyLayout emptyLayout;

    private String key;

    private String url;

    private ActionBar actionBar;

    private MarkedFriendAdapter markedFriendAdapter;

    private ArrayList<MarkedItem> mDatas;

    public static DisplayImageOptions optionsImage = new DisplayImageOptions
            .Builder()
            .cacheInMemory(true)
            .cacheOnDisk(false)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .build();

    private AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            emptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);

            try {

                String response = new String(responseBody);

                System.out.println(response);

                Result<ArrayList<MarkedItem>> result = new Gson().fromJson(response, new TypeToken<Result<ArrayList<MarkedItem>>>() {
                }.getType());

                if (result.getErrorcode() == 0) {

                    mDatas = result.getData();

                    markedFriendAdapter = new MarkedFriendAdapter();
                    markedFriendAdapter.setData(mDatas.get(0).getData());
                    friendsListView.setAdapter(markedFriendAdapter);

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
        textView.setText("标记好友");

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mark_new_friends;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    public void initView() {

        url = getIntent().getStringExtra("url");
        key = getIntent().getStringExtra("key");
        ImageLoader.getInstance().displayImage(url, friendsImageView, optionsImage);

        emptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
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
            FaceSwipingApi.getMarkedPeopleList(key, handler);

        } else {

            AppContext.showToastShort(R.string.tip_no_internet);

        }
    }


    @Override
    public void onClick(View v) {

    }

    public String getKey() {
        return key;
    }

    public String getUrl() {
        return url;
    }
}
