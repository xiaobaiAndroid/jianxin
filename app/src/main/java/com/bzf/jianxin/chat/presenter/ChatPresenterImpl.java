package com.bzf.jianxin.chat.presenter;

import com.bzf.jianxin.base.BaseCallbackListener;
import com.bzf.jianxin.base.BasePresenter;
import com.bzf.jianxin.chat.model.MessageModel;
import com.bzf.jianxin.chat.model.MessageModelImpl;
import com.bzf.jianxin.chat.model.SendMessageListener;
import com.bzf.jianxin.chat.view.NewMsgView;
import com.bzf.jianxin.chat.view.SendMsgView;
import com.bzf.jianxin.chat.widget.ChatItemListViewBean;

/**
 * com.bzf.jianxin.chat.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class ChatPresenterImpl extends BasePresenter implements ChatPresenter{

    private MessageModel mMessageModel;

    public ChatPresenterImpl(){
        super();
        mMessageModel = new MessageModelImpl();
    }

    @Override
    public void sendTextMessage(final SendMsgView view, String content, String contactUsername) {
        view.showSendMsgDialog();

        mMessageModel.sendTextMessage(content, contactUsername, new SendMessageListener() {
            @Override
            public void onProgress(int progress, String status) {
                view.showSendMsgProgress(progress);
            }

            @Override
            public void success(final String s) {
                if(mHandler!=null){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.dimissSendMsgDialog();
                            view.sendSuccess();
                        }
                    });
                }
            }

            @Override
            public void fail(final Throwable e) {
                if(mHandler!=null){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.dimissSendMsgDialog();
                            view.sendFail(e.getMessage());
                        }
                    });
                }
            }
        });
    }

    @Override
    public void readMessage() {

    }

    @Override
    public void readHistoryMessage(String chatUserName,String startMsgId,int pagesize) {
        mMessageModel.readHistoryMsg(chatUserName, startMsgId, pagesize);
    }

    @Override
    public void getNewMsg(final NewMsgView view, String contactUsername, String msgId) {
        mMessageModel.getMsgById(contactUsername,msgId, new BaseCallbackListener<ChatItemListViewBean>() {
            @Override
            public void success(final ChatItemListViewBean chatItemListViewBean) {
                if(mHandler!=null){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.getNewMsgSuccess(chatItemListViewBean);
                        }
                    });
                }
            }

            @Override
            public void fail(final Throwable e) {
                if(mHandler!=null){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.getNewMsgFail(e.getMessage());
                        }
                    });
                }
            }
        });
    }


}
