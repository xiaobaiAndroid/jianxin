package com.bzf.jianxin.login.presenter;

import com.bzf.jianxin.bean.Users;
import com.bzf.jianxin.login.view.LoginView;

/**
 * com.bzf.jianxin.login.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class SplashPresenterImpl extends LoginPresenterImpl implements SplashPresenter{


    public SplashPresenterImpl(LoginView view) {
        super(view);
    }

    @Override
    public Users getCurrentUser() {
        return mModel.getCurrentLoginUser();
    }

    @Override
    public void auoLogin(Users currentUser) {
        login(currentUser.getUsername(), currentUser.getPsw());
    }

}
