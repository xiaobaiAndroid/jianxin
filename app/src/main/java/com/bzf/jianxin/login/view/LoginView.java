package com.bzf.jianxin.login.view;

import com.bzf.jianxin.bean.User;

/**
 * com.bzf.jianxin.login.view
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface LoginView {

    void showDialog();
    void closeDialog();
    void success(User user);
    void fail(String message);
}
