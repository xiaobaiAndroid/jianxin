package com.bzf.jianxin.register.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

/**
 * com.bzf.jianxin.register.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface RegisterPresenter{

    void register(String username, String psw, String nickName);

    /**
     * 保存头像
     * @param photo
     * @param quality
     * @param file
     */
    void saveAvatar(Bitmap photo, int quality, File file);

    /**
     * 上传头像，然后更新用户表中的头像地址
     * @param file
     * @param username
     */
    void uploadAvatar(File file, String username);

    /**
     * 穿件保存头像的File对象
     */
    void createSaveImageFile(Context context)throws Exception;

    /**
     * 设置裁剪图片Intent参数
     * @param uri
     * @throws Exception
     */
    Intent getPhotoZoomIntent(Uri uri)throws Exception;
}
