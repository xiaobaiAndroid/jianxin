package com.bzf.jianxin.main.presenter;

import com.bzf.jianxin.bean.Conversation;

/**
 * com.bzf.jianxin.main.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface ConversationListPresenter {

    /**
     *  获取所有会话列表
     */
    public void getConversationList();


    /**
     *  获取更新的会话列表
     */
    void getUpdateConversationList();

    /**
     * 设置指定会话已读，设置这个标记是为了会话列表的局部更新
     * @param conversation
     */
    void setIsRead(Conversation conversation);
}
