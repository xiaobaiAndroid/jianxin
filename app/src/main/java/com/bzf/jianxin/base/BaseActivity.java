package com.bzf.jianxin.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.bzf.jianxin.MyApplication;
import com.bzf.jianxin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * com.bzf.jianxin.base
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity{

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createContentView(savedInstanceState);
        showNewMessageToNotification();
        ButterKnife.bind(this);
        init();
    }

    protected void initToolbar(String title){
        mToolbar.setTitle(title);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {//监听ActionBar的返回键
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 有消息显示到通知栏
     */
    protected void showNewMessageToNotification() {
        MyApplication.getIns().mShowNewMessage = true;
    }

    protected abstract void init();

    /**
     * 设置Activity布局
     * @param savedInstanceState
     */
    protected abstract void createContentView(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        if(mPresenter!=null){
            mPresenter.detach();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return true;
    }

}
