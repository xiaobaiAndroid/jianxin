package com.bzf.jianxin.register.view;

import com.bzf.jianxin.bean.User;

/**
 * com.bzf.jianxin.register.view
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface RegisterView {

    void showDialog();
    void closeDialog();
    void success(User user);
    void fail(String e);
}
