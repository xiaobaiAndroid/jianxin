package com.bzf.jianxin.chat.model;

import com.bzf.jianxin.chat.widget.ChatItemListViewBean;

/**
 * com.bzf.jianxin.chat.model
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface MessageModel {

    /**
     * 发送文本消息
     * @param content 消息文字内容
     * @param contactUsername 对方用户
     * @param lisenter  发送消息状态啊监听器
     */
    void sendTextMessage(String content,String contactUsername,SendMessageListener lisenter);

    void readMessage();

    /**
     * 获取历史聊天记录
     * @param chatUserName
     * @param startMsgId
     * @param pagesize
     */
    void readHistoryMsg(String chatUserName, String startMsgId, int pagesize);

    /**
     * 根据Id获取消息
     * @param contactUsername
     * @param msgId
     * @return
     */
    ChatItemListViewBean getMsgById(String contactUsername, String msgId)throws Exception;
}
