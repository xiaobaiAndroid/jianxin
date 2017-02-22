package com.bzf.jianxin.main.model;

import com.bzf.jianxin.bean.Conversation;

import java.util.List;

/**
 * 会话model
 * com.bzf.jianxin.main.model
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface ConversationModel {

    /**
     * 获取所有会话列表
     */
    List<Conversation> getConversationList()throws Exception;

    /**
     * 更新会话列表
     */
    List<Conversation> getUpdateConversationList()throws Exception;

    /**
     * 把指定会话设置成已读
     * @param conversation
     */
    void setIsRead(Conversation conversation);
}
