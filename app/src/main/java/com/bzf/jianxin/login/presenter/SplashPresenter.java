package com.bzf.jianxin.login.presenter;

import com.bzf.jianxin.bean.Users;
import com.bzf.jianxin.login.view.LoginView;

/**
 * com.bzf.jianxin.login.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface SplashPresenter{

    /**
     * 获取当前用户
     * @return
     */
    Users getCurrentUser();

    /**
     * 自动登录
     * @param currentUser
     */
    void auoLogin(LoginView view,Users currentUser);
}
