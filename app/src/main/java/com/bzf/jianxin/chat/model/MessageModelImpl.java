package com.bzf.jianxin.chat.model;

import com.bzf.jianxin.base.BaseCallbackListener;
import com.bzf.jianxin.chat.widget.ChatItemListViewBean;
import com.bzf.jianxin.commonutils.HuanXinTool;
import com.bzf.jianxin.commonutils.LogTool;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;

/**
 * com.bzf.jianxin.chat.model
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class MessageModelImpl implements MessageModel{
    private static final String TAG = MessageModelImpl.class.getName();

    @Override
    public void sendTextMessage(String content, String contactUsername,final SendMessageListener lisenter) {
        LogTool.i(TAG,"content="+content+",contactUsername="+contactUsername);
        HuanXinTool.sendTextMessage(content, contactUsername, EMMessage.ChatType.Chat, new EMCallBack() {
            @Override
            public void onSuccess() {
                lisenter.success("");
                LogTool.i(TAG,"发送成功");
            }

            @Override
            public void onError(int code, String error) {
                lisenter.fail(new Throwable(code+error));
                LogTool.i(TAG,"发送失败：code="+code+",error="+error);
            }

            /**
             * @param progress 进度信息
             * @param status 包含文件描述的进度信息
             */
            @Override
            public void onProgress(int progress, String status) {
                lisenter.onProgress(progress,status);
            }
        });
    }

    @Override
    public void readMessage() {

    }

    @Override
    public void readHistoryMsg(String chatUserName, String startMsgId, int pagesize) {
        HuanXinTool.getChatRecord(chatUserName,startMsgId,pagesize);
    }

    @Override
    public void getMsgById(final String contactUsername, final String msgId, final BaseCallbackListener<ChatItemListViewBean> listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMMessage emMessage = HuanXinTool.getMessage(contactUsername, msgId, true);
                    ChatItemListViewBean bean = new ChatItemListViewBean();
                    EMMessageBody body = emMessage.getBody();
                    if(body instanceof EMTextMessageBody){
                        EMTextMessageBody textBody = (EMTextMessageBody) body;
                        bean.setText(textBody.getMessage());
                    }
                    bean.setType(0);
                    listener.success(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.fail(e);
                }
            }
        }).start();

    }

}
