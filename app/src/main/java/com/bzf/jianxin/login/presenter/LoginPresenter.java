package com.bzf.jianxin.login.presenter;

import com.bzf.jianxin.base.BasePresenter;

/**
 * com.bzf.jianxin.login.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface LoginPresenter extends BasePresenter{

    /**
     * 登录
     * @param userName
     * @param psw
     */
    void login(String userName,String psw);
}
