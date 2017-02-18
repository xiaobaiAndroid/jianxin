package com.bzf.jianxin.chat.view;

import com.bzf.jianxin.base.BaseView;
import com.bzf.jianxin.chat.widget.ChatItemListViewBean;

/**
 * com.bzf.jianxin.chat.view
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface ChatView extends BaseView{

    void sendSuccess();
    void sendFail(String msg);
    void showSendMsgDialog();
    void dimissSendMsgDialog();
    void showSendMsgProgress(int progress);

    /**
     * 获取新消息成功的回调
     * @param bean
     */
    void getNewMsgSuccess(ChatItemListViewBean bean);

    /**
     * 获取新消息失败的回调
     * @param msg
     */
    void getNewMsgFail(String msg);
}
