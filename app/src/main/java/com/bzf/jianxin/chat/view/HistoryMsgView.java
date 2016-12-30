package com.bzf.jianxin.chat.view;

import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * 聊天记录
 * com.bzf.jianxin.chat.view
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface HistoryMsgView {

    void showHistoryMsg(List<EMMessage> msgList);
    void failLoadHistoryMsg(String msg);
    void showHistoryMsgDialog();
    void dismissHistoryMsgDialog();
}
