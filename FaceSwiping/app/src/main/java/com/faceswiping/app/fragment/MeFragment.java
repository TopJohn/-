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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faceswiping.app.AppContext;
import com.faceswiping.app.R;
import com.faceswiping.app.activity.MainActivity;
import com.faceswiping.app.api.remote.FaceSwipingApi;
import com.faceswiping.app.base.BaseFragment;
import com.faceswiping.app.bean.Result;
import com.faceswiping.app.bean.User;
import com.faceswiping.app.util.TDevice;
import com.faceswiping.app.util.UIHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {


    @InjectView(R.id.fragment_me_userImage)
    ImageView userImage;

    @InjectView(R.id.fragment_me_userName)
    TextView userName;

    @InjectView(R.id.fragment_me_userDescribe)
    TextView userDescribe;

    @InjectView(R.id.fragment_me_myInfo)
    LinearLayout myInformation;

    @InjectView(R.id.fragment_me_myPhoto)
    LinearLayout myPhoto;

    @InjectView(R.id.fragment_me_myFile)
    LinearLayout myFile;

    @InjectView(R.id.fragment_me_mySetting)
    LinearLayout mySetting;

    @InjectView(R.id.fragment_me_mySecret)
    LinearLayout mySecret;

    private MainActivity mainActivity;

    private ActionBar actionBar;

    private User user;

    private AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            hideWaitDialog();

            try {

                String response = new String(responseBody);

                Result<User> result = new Gson().fromJson(response, new TypeToken<User>() {
                }.getType());

                if (result.getErrorcode() == 0) {

                    user = result.getData();
                    fillUI();


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

            AppContext.showToastShort("获取用户信息失败～！");

        }
    };

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
        actionBar.hide();
        actionBar.setShowHideAnimationEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeAsUpIndicator(null);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_custom_view);
        View view = actionBar.getCustomView();
        TextView textView = (TextView) view.findViewById(R.id.actionbar_title);
        textView.setText("");

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.inject(this, view);
        initView(view);
        initData();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void initView(View view) {

        mySecret.setOnClickListener(this);

    }

    @Override
    public void initData() {
        //sendRequestData();



    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {

            case R.id.fragment_me_mySecret:

                UIHelper.showSecretSettingActivity(mainActivity);

                break;

            default:

                break;

        }


    }

    private void sendRequestData() {


        if (TDevice.hasInternet()) {

            FaceSwipingApi.getFriends(handler);

        } else {

            AppContext.showToastShort(R.string.tip_no_internet);

        }


    }

    private void fillUI() {

        ImageLoader.getInstance().displayImage(user.getHeadImageUrl(), userImage, ChatFragment.optionsImage);
        userName.setText(user.getNickName());
        userDescribe.setText(user.getIntroduction());


    }

}
