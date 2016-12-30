package com.bzf.jianxin.login.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bzf.jianxin.base.BaseActivity;

/**
 * com.bzf.jianxin.login.widget
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class SplashActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}
