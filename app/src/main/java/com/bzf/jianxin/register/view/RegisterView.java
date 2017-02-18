package com.bzf.jianxin.register.view;

import com.bzf.jianxin.base.BaseView;
import com.bzf.jianxin.bean.User;

/**
 * 注册的view
 * com.bzf.jianxin.register.view
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface RegisterView extends BaseView{

   void showRegisterDialog();
    void closeRegisterDialog();
    void registerSuccess(User user);
    void registerFail(String msg);

    /**
     * 上传头像成功的回调接口
     */
    void uploadAvatarSuccess();

    /**
     * 上传头像失败的回调接口
     * @param msg
     */
    void uploadAvatartFail(String msg);

    void closeUpdateAvatarDialog();
}
