package com.bzf.jianxin.login.view;

import com.bzf.jianxin.bean.User;

/**
 * 登录的view
 * com.bzf.jianxin.login.view
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface LoginView {
    void showLoginDialog();
    void closeLoginDialog();
    void loginSucess(User user);
    void loginFail(String message);
}
