package com.bzf.jianxin.main.view;

import com.bzf.jianxin.bean.Conversation;

import java.util.List;

/**
 * 更新会话列表的View
 * com.bzf.jianxin.main.view
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface UpdateConversationListView {

    void updateConversationListSucess(List<Conversation> list);
    void updateConversationListFail(String msg);
}
