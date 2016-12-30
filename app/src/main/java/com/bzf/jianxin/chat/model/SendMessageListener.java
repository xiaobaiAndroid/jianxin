package com.bzf.jianxin.chat.model;

import com.bzf.jianxin.base.BaseCallbackListener;

/**
 * com.bzf.jianxin.chat.model
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface SendMessageListener extends BaseCallbackListener<String>{

    /**
     * @param progress 进度信息
     * @param status 包含文件描述的进度信息
     */
    void onProgress(int progress, String status);
}
