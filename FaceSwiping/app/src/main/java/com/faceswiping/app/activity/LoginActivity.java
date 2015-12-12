package com.faceswiping.app.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.faceswiping.app.AppContext;
import com.faceswiping.app.R;
import com.faceswiping.app.api.remote.FaceSwipingApi;
import com.faceswiping.app.base.BaseActivity;
import com.faceswiping.app.bean.Result;
import com.faceswiping.app.bean.User;
import com.faceswiping.app.util.SimpleTextWatcher;
import com.faceswiping.app.util.StringUtils;
import com.faceswiping.app.util.TDevice;
import com.faceswiping.app.util.UIHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.igexin.sdk.PushManager;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import butterknife.InjectView;

public class LoginActivity extends BaseActivity {

    @InjectView(R.id.editName)
    EditText editName;

    @InjectView(R.id.editPassword)
    EditText editPassword;

    @InjectView(R.id.loginButton)
    Button loginButton;


    TextWatcher textWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            upateLoginButton();
        }
    };


    private AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {

                String response = new String(responseBody);

                System.out.println(response);


//                Result<User> result = JSON.parseObject(response, new TypeReference<Result<User>>() {
//                });

                Result<User> result = new Gson().fromJson(response, new TypeToken<Result<User>>() {
                }.getType());


                if (result.getErrorcode() == 0) {
                    // 保存登录信息
                    User user = result.getData();

                    for (Header header : headers) {
                        if (header.getName().equals("X-AUTH-TOKEN")) {
                            System.out.println(header.getValue());
                            user.setToken(header.getValue());
                        }
                    }

                    AppContext.getInstance().saveUserInfo(user);

                    handleLoginSuccess();

                } else {

                    AppContext.getInstance().cleanLoginInfo();
                    hideWaitDialog();
                    AppContext.showToast(result.getErrormsg());
                }


            } catch (Exception e) {
                e.printStackTrace();
                onFailure(statusCode, headers, responseBody, e);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] arg1, byte[] arg2,
                              Throwable arg3) {

            System.out.println(statusCode);
            hideWaitDialog();
            AppContext.showToast(R.string.tip_login_error_for_network);
        }
    };


    private void handleLoginSuccess() {

        FaceSwipingApi.sendClientId(PushManager.getInstance().getClientid(LoginActivity.this), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                hideWaitDialog();
                UIHelper.showMainActivity(LoginActivity.this);
                finish();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                hideWaitDialog();
//                System.out.println("clientId绑定失败");
//                AppContext.showToast(R.string.tip_login_error_for_network);

                //clientId可能一开始会获取不到，也不影响
                hideWaitDialog();
                UIHelper.showMainActivity(LoginActivity.this);
                finish();

            }
        });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        //不需要actionBar这个页面
        mActionBar.hide();

        editName.setOnClickListener(this);
        editPassword.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        editName.addTextChangedListener(textWatcher);
        editPassword.addTextChangedListener(textWatcher);
        upateLoginButton();

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {

            case R.id.loginButton:
                handleLogin();

                break;
            default:
                break;
        }

    }


    private void handleLogin() {

        if (!prepareForLogin()) {
            return;
        }

        // if the data has ready
        String userName = editName.getText().toString();
        String passWord = editPassword.getText().toString();

        showWaitDialog(R.string.progress_login);
        FaceSwipingApi.login(userName, passWord, mHandler);
    }


    private boolean prepareForLogin() {
        if (!TDevice.hasInternet()) {
            AppContext.showToastShort(R.string.tip_no_internet);
            return false;
        }
        String userName = editName.getText().toString();
        if (StringUtils.isEmpty(userName)) {
            AppContext.showToastShort(R.string.tip_please_input_phonenumber);
            editName.requestFocus();
            return false;
        }

        String passWord = editPassword.getText().toString();

        if (StringUtils.isEmpty(passWord)) {
            AppContext.showToastShort(R.string.tip_please_input_vertifycode);
            editPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void upateLoginButton() {
        if (editName.getText().length() == 0) {
            loginButton.setEnabled(false);
            return;
        }

        if (editPassword.getText().length() == 0) {
            loginButton.setEnabled(false);
            return;
        }


        loginButton.setEnabled(true);
    }


}
