package com.bzf.jianxin.login.module;

import com.bzf.jianxin.register.module.RegisterListener;

/**
 * com.bzf.jianxin.login.module
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface UserModel {

    void login(String userName,String psw,LoginListener listener);

    void register(String userName, String psw, RegisterListener listener);
}
