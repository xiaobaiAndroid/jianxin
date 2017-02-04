package com.bzf.jianxin.login.module;

import com.bzf.jianxin.bean.User;

/**
 * com.bzf.jianxin.login.module
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface LoginListener {

    void success(User user);
    void fail(Throwable e);
}
