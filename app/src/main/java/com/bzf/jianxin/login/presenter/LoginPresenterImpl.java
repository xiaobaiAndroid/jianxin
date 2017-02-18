package com.bzf.jianxin.login.presenter;

import com.bzf.jianxin.base.BaseCallbackListener;
import com.bzf.jianxin.base.BasePresenter;
import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.login.module.UserModelImpl;
import com.bzf.jianxin.login.view.LoginView;

/**
 * com.bzf.jianxin.login.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class LoginPresenterImpl extends BasePresenter<LoginView,UserModelImpl> implements LoginPresenter{


    public LoginPresenterImpl(LoginView view) {
        super(view,new UserModelImpl());
    }

    @Override
    public void login(String userName, String psw) {

        view.showLoginDialog();
        mModel.login(userName, psw, new BaseCallbackListener<User>() {
            @Override
            public void success(final User user) {
                if(mHandler!=null){

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.closeLoginDialog();
                            view.loginSucess(user);
                        }
                    });
                }
            }

            @Override
            public void fail(final Throwable e) {
                if(mHandler!=null){

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.closeLoginDialog();
                            view.loginFail("登录失败："+e.getMessage());
                        }
                    });
                }
            }
        });
    }

}
