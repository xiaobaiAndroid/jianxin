package com.bzf.jianxin.login.module;

import com.bzf.jianxin.base.BaseCallbackListener;
import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.bean.Users;

import java.io.File;
import java.util.List;

/**
 * com.bzf.jianxin.login.module
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface UserModel {

    void login(String userName,String psw,BaseCallbackListener listener);

    void register(String username, String psw, String nickName, BaseCallbackListener listener);

    /**
     * 获取联系人列表
     */
    void getContactList(BaseCallbackListener listener);

    /**
     * 获取当前登录用户
     * @return
     */
    Users getCurrentUser();

    /**
     * 退出登录
     */
    void exitLogin(BaseCallbackListener<String> listener);

    /**
     * 查找用户
     * @param username
     */
    void queryUser(String username,BaseCallbackListener<List<User>> listener);

    /**
     * 请求添加用户为好友
     * @param username
     * @param reson  添加的理由
     */
    void addUser(String username,String reson);

    /**
     * 更新用户的头像地址
     * @param imageFile    图片文件
     * @param username  用户名
     * @param listener
     */
    void updateUserAvatar(File imageFile, String username, BaseCallbackListener<String> listener);
}
