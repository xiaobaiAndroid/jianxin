package com.bzf.jianxin.main.view;

import com.bzf.jianxin.bean.Conversation;

import java.util.List;

/**
 * 会话列表View
 * com.bzf.jianxin.main.view
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface ConversationListView {

    void conversationListcSuccess(List<Conversation> list);
    void conversationListFail(String msg);
}
