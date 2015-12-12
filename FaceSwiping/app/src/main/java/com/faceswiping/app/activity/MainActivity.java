package com.faceswiping.app.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.faceswiping.app.AppConfig;
import com.faceswiping.app.AppContext;
import com.faceswiping.app.AppManager;
import com.faceswiping.app.R;
import com.faceswiping.app.cache.DataCleanManager;
import com.faceswiping.app.interf.BaseViewInterface;
import com.faceswiping.app.interf.OnTabReselectListener;
import com.faceswiping.app.util.DoubleClickExitHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

@SuppressLint("InflateParams")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends ActionBarActivity implements
        OnTabChangeListener, BaseViewInterface, View.OnClickListener, OnTouchListener {

    private DoubleClickExitHelper mDoubleClickExit;

    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    // private NavigationDrawerFragment mNavigationDrawerFragment;

    @InjectView(android.R.id.tabhost)
    public FragmentTabHost mTabHost;


    /**
     * Used to store the last screen title. For use in
     * {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private ActionBar mActionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置白天还是夜间模式
        if (AppContext.getNightModeSwitch()) {
            setTheme(R.style.AppBaseTheme_Night);
        } else {
            setTheme(R.style.AppBaseTheme_Light);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();
        AppManager.getAppManager().addActivity(this);

        handleIntent(getIntent());

    }


    //处理其他地方穿过来的意图Intent
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //需要覆盖原来的ntent
        setIntent(intent);
        handleIntent(intent);
    }

    /**
     * 处理传进来的intent
     *
     * @param intent
     * @return void
     * @author 火蚁 2015-1-28 下午3:48:44
     */
    private void handleIntent(Intent intent) {
        if (intent == null)
            return;

        String action = intent.getAction();
        if (action != null && action.equals(Intent.ACTION_VIEW)) {
            //UIHelper.showUrlRedirect(this, intent.getDataString());
        } else if (intent.getBooleanExtra("NOTICE", false)) {
            //从通知栏点击穿过来的Intent

            notifitcationBarClick(intent);
        }
    }

    /**
     * 从通知栏点击的时候相应
     *
     * @param fromWhich
     */
    private void notifitcationBarClick(Intent fromWhich) {
        if (fromWhich != null) {
            boolean fromNoticeBar = fromWhich.getBooleanExtra("NOTICE", false);
            if (fromNoticeBar) {
                //UIHelper.showNotificationListActivity(this);
            }
        }
    }

    @Override
    public void initView() {
        //双击返回键退出类
        mDoubleClickExit = new DoubleClickExitHelper(this);
//        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
//        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
//                (DrawerLayout) findViewById(R.id.drawer_layout));

        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        if (Build.VERSION.SDK_INT > 10) {
            //LinearLayout的方法，设置子控件之间的分割线 0表示没有分割线
            mTabHost.getTabWidget().setShowDividers(0);
        }
        //初始化Tab栏
        initTabs();

        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(this);


        //第一次启动则打开导航栏,清除文件及缓存，设置firstStart false
        if (AppContext.isFristStart()) {
            DataCleanManager.cleanInternalCache(AppContext.getInstance());
            AppContext.setFristStart(false);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  NoticeUtils.unbindFromService(this);
        //unregisterReceiver(mReceiver);
//        mReceiver = null;
        //   NoticeUtils.tryToShutDown(this);
    }

    @Override
    public void initData() {

    }

    private void initTabs() {
        MainTab[] tabs = MainTab.values();
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];
            TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));
            View indicator = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            Drawable drawable = this.getResources().getDrawable(
                    mainTab.getResIcon());
            title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null,
                    null);

            title.setText(getString(mainTab.getResName()));
            tab.setIndicator(indicator);

            mTabHost.addTab(tab, mainTab.getClz(), null);


            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }
    }


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        //标准模式不现实tab
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setTitle(mTitle);
        //设置home键的图标
        // actionBar.setDisplayHomeAsUpEnabled(true);
        //icon是不可点击的
        //actionBar.setIcon(R.drawable.actionbar_search_icon);

//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.actionbar_search_icon);

        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.actionbar_custom_view);
        View view = mActionBar.getCustomView();
        TextView textView = (TextView) view.findViewById(R.id.actionbar_title);
        textView.setText(R.string.fangwuzhangdan);

    }

    //菜单栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main_activity_menu, menu);
//        if (!mNavigationDrawerFragment.isDrawerOpen()) {
        restoreActionBar();
//            return true;
//        }
        return super.onCreateOptionsMenu(menu);
    }

    //菜单栏响应
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case android.R.id.home:

                //UIHelper.showSearchHousedetailActivity(this);

                //UIHelper.showSearchHouseActivity(this);


            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //tab的点击响应
    @Override
    public void onTabChanged(String tabId) {
        final int size = mTabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);
            } else {
                v.setSelected(false);
            }
        }
        //重绘菜单栏
        supportInvalidateOptionsMenu();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            default:
                break;
        }
    }



    //当重新点击导当前tab的时候刷新当前fragment并消费掉点击事件
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouchEvent(event);
        boolean consumed = false;
        // use getTabHost().getCurrentTabView to decide if the current tab is
        // touched again
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && v.equals(mTabHost.getCurrentTabView())) {
            // use getTabHost().getCurrentView() to get a handle to the view
            // which is displayed in the tab - and to get this views context
            Fragment currentFragment = getCurrentFragment();

            if (currentFragment != null
                    && currentFragment instanceof OnTabReselectListener) {
                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;

                listener.onTabReselect();
                consumed = true;
            }
        }
        return consumed;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());
    }

    /**
     * 监听返回--是否退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 是否退出应用
            if (AppContext.get(AppConfig.KEY_DOUBLE_CLICK_EXIT, true)) {
                return mDoubleClickExit.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        // 当 API Level > 11 调用这个方法可能导致奔溃（android.os.Build.VERSION.SDK_INT > 11）
    }


}
