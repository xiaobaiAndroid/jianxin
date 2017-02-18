package com.bzf.jianxin.login.presenter;

import com.bzf.jianxin.bean.Users;

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
    void auoLogin(Users currentUser);
}
