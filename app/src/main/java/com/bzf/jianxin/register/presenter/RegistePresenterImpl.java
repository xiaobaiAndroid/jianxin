package com.bzf.jianxin.register.presenter;

import android.os.Handler;

import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.login.module.UserModel;
import com.bzf.jianxin.login.module.UserModelImpl;
import com.bzf.jianxin.register.module.RegisterListener;
import com.bzf.jianxin.register.view.RegisterView;

/**
 * com.bzf.jianxin.register.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class RegistePresenterImpl implements RegisterPresenter{
    private RegisterView mView;
    private Handler mHandler;
    private UserModel mUserModel;

    public RegistePresenterImpl(RegisterView view){
        mView = view;
        mHandler = new Handler();
        mUserModel = new UserModelImpl();
    }


    @Override
    public void register(String userName, String psw) {
        mUserModel.register(userName,psw,new RegisterListener(){

            @Override
            public void success(final User user) {
                if(mView!=null){
                    mView.closeDialog();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.success(user);
                        }
                    });
                }
            }

            @Override
            public void fail(final Throwable e) {
                if(mView!=null){
                    mView.closeDialog();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.fail("注册失败："+e.getMessage());
                        }
                    });
                }
            }
        });
    }

    @Override
    public void detach() {
        mView = null;
    }
}
