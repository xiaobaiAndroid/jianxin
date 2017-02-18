package com.bzf.jianxin.set.view;

import com.bzf.jianxin.base.BaseView;

/**
 * 退出登录的view
 * com.bzf.jianxin.set.view
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface ExitLoginView extends BaseView{

    void exitLoginSuccess();
    void exitLoginFail(String msg);
}
