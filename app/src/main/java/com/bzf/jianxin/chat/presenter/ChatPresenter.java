package com.bzf.jianxin.chat.presenter;

/**
 * com.bzf.jianxin.chat.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface ChatPresenter{

    /**
     * 发送文本消息
     * @param content 消息文字内容
     * @param contactUsername 对方用户
     */
    void sendTextMessage(String content, String contactUsername);

    void readMessage();


    /**
     * 读取本地历史消息
     */
    void readHistoryMessage(String chatUserName,String startMsgId,int pagesize);

    /**
     * 获取新消息
     * @param contactUsername
     * @param msgId 新消息Id
     */
    void getNewMsg(String contactUsername, String msgId);
}
