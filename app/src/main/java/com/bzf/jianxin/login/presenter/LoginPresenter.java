package com.bzf.jianxin.login.presenter;

import com.bzf.jianxin.login.view.LoginView;

/**
 * com.bzf.jianxin.login.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface LoginPresenter{

    /**
     * 登录
     * @param userName
     * @param psw
     */
    void login(LoginView view,String userName, String psw);
}
