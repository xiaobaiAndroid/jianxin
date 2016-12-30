package com.bzf.jianxin.chat.presenter;

import com.bzf.jianxin.chat.view.NewMsgView;
import com.bzf.jianxin.chat.view.SendMsgView;

/**
 * com.bzf.jianxin.chat.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface ChatPresenter{

    /**
     * 发送文本消息
     * @param view
     * @param content 消息文字内容
     * @param contactUsername 对方用户
     */
    void sendTextMessage(SendMsgView view,String content, String contactUsername);

    void readMessage();


    /**
     * 读取本地历史消息
     */
    void readHistoryMessage(String chatUserName,String startMsgId,int pagesize);

    /**
     * 获取新消息
     * @param view
     * @param contactUsername
     * @param msgId 新消息Id
     */
    void getNewMsg(NewMsgView view, String contactUsername, String msgId);
}
