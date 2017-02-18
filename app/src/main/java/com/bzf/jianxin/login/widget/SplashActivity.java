package com.bzf.jianxin.login.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bzf.jianxin.R;
import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.bean.Users;
import com.bzf.jianxin.commonutils.ToastTool;
import com.bzf.jianxin.login.presenter.SplashPresenter;
import com.bzf.jianxin.login.presenter.SplashPresenterImpl;
import com.bzf.jianxin.login.view.LoginView;
import com.bzf.jianxin.main.widget.MainActivity;

/**
 * com.bzf.jianxin.login.widget
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class SplashActivity extends AppCompatActivity implements LoginView{

    private SplashPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPresenter =  new SplashPresenterImpl(this);
        Users currentUser = mPresenter.getCurrentUser();
        if(currentUser==null){
            toLogin();
        }else{
            mPresenter.auoLogin(currentUser);
        }
    }

    private void toLogin() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    @Override
    public void showLoginDialog() {

    }

    @Override
    public void closeLoginDialog() {

    }

    @Override
    public void loginSucess(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFail(String message) {
        ToastTool.shotMessage("自动登录失败："+message);
        toLogin();
    }
}
