package com.faceswiping.app.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.faceswiping.app.AppContext;
import com.faceswiping.app.R;
import com.faceswiping.app.api.remote.FaceSwipingApi;
import com.faceswiping.app.base.BaseActivity;
import com.faceswiping.app.bean.User;
import com.faceswiping.app.util.SimpleTextWatcher;
import com.faceswiping.app.util.StringUtils;
import com.faceswiping.app.util.TDevice;
import com.faceswiping.app.util.UIHelper;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import butterknife.InjectView;

public class LoginActivity extends BaseActivity {

    @InjectView(R.id.edit_text)
    EditText phoneEditText;

    @InjectView(R.id.vertify_code)
    EditText vertifyCodeEditText;

    @InjectView(R.id.get_vertify_code)
    TextView getVertifyText;

    @InjectView(R.id.btn_login)
    Button loginButton;

    @InjectView(R.id.clear_1)
    ImageView clearImg1;

    @InjectView(R.id.clear_2)
    ImageView clearImg2;

    private String phoneNumber;
    private String vertifyCode;



    private final TextWatcher mPhoneWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            clearImg1
                    .setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE
                            : View.VISIBLE);
        }
    };
    private final TextWatcher mVertifyWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            clearImg2
                    .setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE
                            : View.VISIBLE);
        }
    };

    private AsyncHttpResponseHandler getVertifyCodeHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            String response = new String(responseBody);

            //Result result = JSON.parseObject(new String(responseBody), Result.class);

            Result result = new Gson().fromJson(response, Result.class);

            if (result.getStatus().equals("ok")) {

            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            AppContext.showToastShort("请求失败～！");

        }
    };


    private AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {

                String response = new String(responseBody);

                System.out.println(statusCode);

                System.out.println(response);


//                Result<User> result = JSON.parseObject(response, new TypeReference<Result<User>>() {
//                });

                Result<User> result = new Gson().fromJson(response, new TypeToken<Result<User>>() {
                }.getType());


                if (result.getStatus().equals("ok")) {
                    // 保存登录信息
                    User user = result.getData();


                    AppContext.getInstance().saveUserInfo(user);

                    handleLoginSuccess();

                } else {

                    AppContext.getInstance().cleanLoginInfo();
                    hideWaitDialog();
                    AppContext.showToast(result.getStatus());
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

        FaceSwipingApi.sendClientId(PushManager.getInstance().getClientid(PhoneLoginActivity.this), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                hideWaitDialog();
                UIHelper.showMainActivity(PhoneLoginActivity.this);
                countDownTimer.cancel();
                finish();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                hideWaitDialog();
//                System.out.println("clientId绑定失败");
//                AppContext.showToast(R.string.tip_login_error_for_network);

                //clientId可能一开始会获取不到，也不影响
                hideWaitDialog();
                UIHelper.showMainActivity(PhoneLoginActivity.this);
                countDownTimer.cancel();
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

        clearImg1.setOnClickListener(this);
        clearImg2.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        getVertifyText.setOnClickListener(this);


        phoneEditText.addTextChangedListener(mPhoneWatcher);
        vertifyCodeEditText.addTextChangedListener(mVertifyWatcher);


    }

    @Override
    public void initData() {

        //从文件中取出来的
        phoneEditText.setText(AppContext.getInstance()
                .getProperty("user.account"));

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.clear_1:
                phoneEditText.getText().clear();
                phoneEditText.requestFocus();
                break;
            case R.id.clear_2:
                vertifyCodeEditText.getText().clear();
                vertifyCodeEditText.requestFocus();
                break;
            case R.id.get_vertify_code:

                getVertifyText.setClickable(false);
                getVertifyCode();

                break;
            case R.id.btn_login:
                handleLogin();
                break;
            default:
                break;
        }

    }

    private boolean getVertifyCode() {
        if (!TDevice.hasInternet()) {
            AppContext.showToastShort(R.string.tip_no_internet);
            getVertifyText.setClickable(true);
            return false;
        }
        String phone = phoneEditText.getText().toString();
        if (StringUtils.isEmpty(phone)) {
            AppContext.showToastShort(R.string.tip_please_input_phonenumber);
            getVertifyText.setClickable(true);
            phoneEditText.requestFocus();
            return false;
        }
        //验证手机号
        if (!StringUtils.isPhone(phone)) {
            AppContext.showToastShort(R.string.tip_illegal_phonenumber);
            getVertifyText.setClickable(true);
            phoneEditText.requestFocus();
            return false;
        }
        if (TDevice.hasInternet()) {
            FaceSwipingApi.getVertifyCode(phone, getVertifyCodeHandler);
            countDownTimer.start();
        } else {
            AppContext.showToastShort(R.string.tip_no_internet);
            getVertifyText.setClickable(true);
        }
        return true;
    }


    private void handleLogin() {

        if (!prepareForLogin()) {
            return;
        }

        // if the data has ready
        phoneNumber = phoneEditText.getText().toString();
        vertifyCode = vertifyCodeEditText.getText().toString();

        showWaitDialog(R.string.progress_login);
        FaceSwipingApi.phoneLogin(phoneNumber, vertifyCode, mHandler);
    }


    private boolean prepareForLogin() {
        if (!TDevice.hasInternet()) {
            AppContext.showToastShort(R.string.tip_no_internet);
            return false;
        }
        String phone = phoneEditText.getText().toString();
        if (StringUtils.isEmpty(phone)) {
            AppContext.showToastShort(R.string.tip_please_input_phonenumber);
            phoneEditText.requestFocus();
            return false;
        }
        //验证手机号
        if (!StringUtils.isPhone(phone)) {
            AppContext.showToastShort(R.string.tip_illegal_phonenumber);
            phoneEditText.requestFocus();
            return false;
        }
        String vertifyCode = vertifyCodeEditText.getText().toString();

        if (StringUtils.isEmpty(vertifyCode)) {
            AppContext.showToastShort(R.string.tip_please_input_vertifycode);
            vertifyCodeEditText.requestFocus();
            return false;
        }

        if (!StringUtils.isVertifyCode(vertifyCode)) {
            AppContext.showToastShort(R.string.qingshuruzhengqueyanzhengma);
            vertifyCodeEditText.requestFocus();
            return false;
        }


        return true;
    }


    private CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {

        // 参数依次为总时长,和计时的时间间隔
        //long millisInFuture,long countDownInterval


        // 固定间隔被调用, millisUntilFinished倒计时剩余时间
        @Override
        public void onTick(long millisUntilFinished) {

            getVertifyText.setClickable(false);
            getVertifyText.setTextColor(getResources().getColor(R.color.gray));
            getVertifyText.setText(millisUntilFinished / 1000 + "秒");

        }

        // 倒计时完成时（值为0）被调用
        @Override
        public void onFinish() {
            getVertifyText.setClickable(true);
            getVertifyText.setTextColor(getResources().getColor(R.color.green_text_color));
            getVertifyText.setText("重新验证");
            getVertifyText.setClickable(true);
        }
    };

}
