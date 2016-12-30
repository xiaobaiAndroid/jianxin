package com.bzf.jianxin.addcontact.presenter;

import com.bzf.jianxin.addcontact.view.QueryContactView;

/**
 * com.bzf.jianxin.addcontact.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface AddContactPresenter {

    /**
     * 根据用户名查询用户
     * @param view
     * @param username
     */
    void queryUser(QueryContactView view,String username);

    /**
     * 请求添加好友
     * @param username
     * @param reason
     */
    void addUser(String username,String reason);
}
