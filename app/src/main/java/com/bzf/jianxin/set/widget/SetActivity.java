package com.bzf.jianxin.set.widget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bzf.jianxin.R;
import com.bzf.jianxin.base.BaseActivity;
import com.bzf.jianxin.commonutils.ToastTool;
import com.bzf.jianxin.login.widget.LoginActivity;
import com.bzf.jianxin.main.widget.MainActivity;
import com.bzf.jianxin.set.presenter.SetPresenterImpl;
import com.bzf.jianxin.set.view.ExitLoginView;

import butterknife.BindView;

public class SetActivity extends BaseActivity<SetPresenterImpl> implements ExitLoginView{


    @BindView(R.id.mTv_eixt)
    TextView mTvEixt;

    @Override
    protected void init() {
        mPresenter = new SetPresenterImpl(this);
        initToolbar("设置");
        initListener();
    }

    private void initListener() {
        mTvEixt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.exitLogin();
            }
        });
    }

    @Override
    protected void createContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_set);
    }

    @Override
    public void exitLoginSuccess() {
        startOtherActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void exitLoginFail(String msg) {
        ToastTool.shotMessage(msg);
    }

    @Override
    public void onBackPressed() {
        startOtherActivity(new Intent(this, MainActivity.class));
    }
}
