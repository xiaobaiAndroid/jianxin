package com.bzf.jianxin.chat.view;

import com.bzf.jianxin.chat.widget.ChatItemListViewBean;

/**
 * com.bzf.jianxin.chat.view
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface NewMsgView {

    void getNewMsgSuccess(ChatItemListViewBean bean);
    void getNewMsgFail(String msg);
}
