package com.bzf.jianxin.login.presenter;

import android.os.Handler;

import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.login.module.UserModel;
import com.bzf.jianxin.login.module.UserModelImpl;
import com.bzf.jianxin.login.view.LoginView;

/**
 * com.bzf.jianxin.login.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class LoginPresenterImpl implements LoginPresenter{

    private LoginView mView;
    private Handler mHandler = new Handler();
    private UserModel mUserModel;

    public LoginPresenterImpl(LoginView view) {
        mView = view;
        mUserModel = new UserModelImpl();
    }

    @Override
    public void login(String userName, String psw) {
        mView.success(new User());
//        mView.showDialog();
//        mUserModel.login(userName, psw, new LoginListener() {
//            @Override
//            public void success(final User user) {
//                if(mView!=null){
//                    mView.closeDialog();
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mView.success(user);
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void fail(final Throwable e) {
//                if(mView!=null){
//                    mView.closeDialog();
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mView.fail("登录失败："+e.getMessage());
//                        }
//                    });
//                }
//            }
//        });
    }


    @Override
    public void detach() {
        mView = null;
    }
}
