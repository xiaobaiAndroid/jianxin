package com.bzf.jianxin.register.view;

/**
 * 上传头像的view
 * com.bzf.jianxin.register.view
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface UploadAvatarView {

    void uploadAvatarSuccess();
    void uploadAvatartFail(String msg);

    void showUpdateAvatarDialog();
    void closeUpdateAvatarDialog();
}
