package com.bzf.jianxin.register.module;

import com.bzf.jianxin.bean.User;

/**
 * com.bzf.jianxin.register.module
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface RegisterListener {
    void success(User user);
    void fail(Throwable e);
}
