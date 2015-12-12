package com.faceswiping.app.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.faceswiping.app.AppContext;
import com.faceswiping.app.R;
import com.faceswiping.app.activity.MainActivity;
import com.faceswiping.app.adapter.FriendAdapter;
import com.faceswiping.app.api.remote.FaceSwipingApi;
import com.faceswiping.app.base.BaseFragment;
import com.faceswiping.app.bean.FriendBean;
import com.faceswiping.app.bean.Result;
import com.faceswiping.app.util.TDevice;
import com.faceswiping.app.widget.EmptyLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends BaseFragment {


    @InjectView(R.id.fragment_gridView)
    GridView gridView;


    private FriendAdapter friendAdapter;

    private ArrayList<FriendBean> mDatas;

    private MainActivity mainActivity;

    private ActionBar actionBar;

    @InjectView(R.id.error_layout)
    EmptyLayout emptyLayout;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
        actionBar = mainActivity.getSupportActionBar();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeAsUpIndicator(null);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_custom_view);
        View view = actionBar.getCustomView();
        TextView textView = (TextView) view.findViewById(R.id.actionbar_title);
        textView.setText("联系人");

        super.onCreateOptionsMenu(menu, inflater);
    }


    private AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            hideWaitDialog();

            try {

                String response = new String(responseBody);

                Result<ArrayList<FriendBean>> result = new Gson().fromJson(response, new TypeToken<ArrayList<FriendBean>>() {
                }.getType());

                if (result.getErrorcode() == 0) {

                    mDatas = result.getData();

                    friendAdapter = new FriendAdapter();
                    friendAdapter.setData(mDatas);

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        ButterKnife.inject(this, view);
        initView(view);
        initData();
        return view;
    }


    @Override
    public void initView(View view) {

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

    @Override
    public void onClick(View v) {


    }

    private void sendRequestData() {

        if (TDevice.hasInternet()) {
            emptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
            FaceSwipingApi.getFriends(handler);

        } else {

            AppContext.showToastShort(R.string.tip_no_internet);

        }

    }

}
