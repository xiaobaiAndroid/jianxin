package com.bzf.jianxin.login.view;

import com.bzf.jianxin.base.BaseView;
import com.bzf.jianxin.bean.User;

/**
 * 登录的view
 * com.bzf.jianxin.login.view
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface LoginView extends BaseView{
    void showLoginDialog();
    void closeLoginDialog();
    void loginSucess(User user);
    void loginFail(String message);
}
