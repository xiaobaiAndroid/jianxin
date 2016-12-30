package com.bzf.jianxin.register.view;

import com.bzf.jianxin.bean.User;

/**
 * 注册的view
 * com.bzf.jianxin.register.view
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface RegisterView {

   void showRegisterDialog();
    void closeRegisterDialog();
    void registerSuccess(User user);
    void registerFail(String msg);
}
