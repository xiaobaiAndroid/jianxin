package com.bzf.jianxin.addcontact.view;

import com.bzf.jianxin.bean.User;

import java.util.List;

/**
 * 查找联系人的view
 * com.bzf.jianxin.addcontact.view
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface QueryContactView {

    void queryContactSuccess(List<User> list);
    void queryContactFail(String msg);
    void showQueryContactDialog();
    void closeQueryContactDialog();
}
