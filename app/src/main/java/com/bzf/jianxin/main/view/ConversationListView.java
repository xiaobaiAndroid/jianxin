package com.bzf.jianxin.main.view;

import com.bzf.jianxin.base.BaseView;
import com.bzf.jianxin.bean.Conversation;

import java.util.List;

/**
 * 会话列表View
 * com.bzf.jianxin.main.view
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface ConversationListView extends BaseView{

    void conversationListcSuccess(List<Conversation> list);
    void conversationListFail(String msg);

    /**
     * 更新会话列表的回调
     * @param list
     */
    void updateConversationListSucess(List<Conversation> list);
    void updateConversationListFail(String msg);
}
