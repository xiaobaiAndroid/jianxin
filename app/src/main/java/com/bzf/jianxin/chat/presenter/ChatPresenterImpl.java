package com.bzf.jianxin.chat.presenter;

import com.bzf.jianxin.base.BaseCallbackListener;
import com.bzf.jianxin.base.BasePresenter;
import com.bzf.jianxin.chat.model.MessageModelImpl;
import com.bzf.jianxin.chat.model.SendMessageListener;
import com.bzf.jianxin.chat.view.ChatView;
import com.bzf.jianxin.chat.widget.ChatItemListViewBean;

/**
 * com.bzf.jianxin.chat.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class ChatPresenterImpl extends BasePresenter<ChatView,MessageModelImpl> implements ChatPresenter{

    public ChatPresenterImpl(ChatView view){
        super(view,new MessageModelImpl());
    }

    @Override
    public void sendTextMessage( String content, String contactUsername) {
        view.showSendMsgDialog();

        mModel.sendTextMessage(content, contactUsername, new SendMessageListener() {
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
        mModel.readHistoryMsg(chatUserName, startMsgId, pagesize);
    }

    @Override
    public void getNewMsg(String contactUsername, String msgId) {
        mModel.getMsgById(contactUsername,msgId, new BaseCallbackListener<ChatItemListViewBean>() {
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
