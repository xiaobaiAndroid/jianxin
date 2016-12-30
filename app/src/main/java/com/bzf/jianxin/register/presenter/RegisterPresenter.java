package com.bzf.jianxin.register.presenter;

import android.graphics.Bitmap;

import com.bzf.jianxin.register.view.RegisterView;
import com.bzf.jianxin.register.view.UploadAvatarView;

import java.io.File;

/**
 * com.bzf.jianxin.register.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface RegisterPresenter{

    void register(RegisterView view, String username, String psw, String nickName);

    /**
     * 保存头像
     * @param photo
     * @param quality
     * @param file
     */
    void saveAvatar(Bitmap photo, int quality, File file);

    /**
     * 上传头像，然后更新用户表中的头像地址
     * @param view
     * @param file
     * @param username
     */
    void uploadAvatar(UploadAvatarView view, File file, String username);
}
