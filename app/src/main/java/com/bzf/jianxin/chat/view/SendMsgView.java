package com.bzf.jianxin.chat.view;

/**
 * 发送消息的view
 * com.bzf.jianxin.chat.view
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface SendMsgView {

    void sendSuccess();
    void sendFail(String msg);
    void showSendMsgDialog();
    void dimissSendMsgDialog();
    void showSendMsgProgress(int progress);
}
