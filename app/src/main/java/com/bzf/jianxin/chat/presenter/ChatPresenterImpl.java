package com.bzf.jianxin.chat.presenter;

import com.bzf.jianxin.base.BasePresenter;
import com.bzf.jianxin.chat.model.MessageModelImpl;
import com.bzf.jianxin.chat.model.SendMessageListener;
import com.bzf.jianxin.chat.view.ChatView;
import com.bzf.jianxin.chat.widget.ChatItemListViewBean;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

/**
 * com.bzf.jianxin.chat.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class ChatPresenterImpl extends BasePresenter<ChatView, MessageModelImpl> implements ChatPresenter {

    public ChatPresenterImpl(ChatView view) {
        super(view, new MessageModelImpl());
    }

    @Override
    public void sendTextMessage(String content, String contactUsername) {
        view.showSendMsgDialog();

        mModel.sendTextMessage(content, contactUsername, new SendMessageListener() {
            @Override
            public void onProgress(int progress, String status) {
                view.showSendMsgProgress(progress);
            }

            @Override
            public void success(final String s) {
                asyncRequest(s,new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        view.dimissSendMsgDialog();
                        view.sendSuccess();
                    }
                });
            }

            @Override
            public void fail(final Throwable e) {
                asyncRequest(e.getMessage(),new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        view.dimissSendMsgDialog();
                        view.sendFail(s);
                    }
                });
            }
        });
    }

    @Override
    public void readMessage() {

    }

    @Override
    public void readHistoryMessage(String chatUserName, String startMsgId, int pagesize) {
        mModel.readHistoryMsg(chatUserName, startMsgId, pagesize);
    }

    @Override
    public void getNewMsg(final String contactUsername, final String msgId) {
        asyncRequest(new ObservableOnSubscribe<ChatItemListViewBean>() {
            @Override
            public void subscribe(ObservableEmitter<ChatItemListViewBean> observable) throws Exception {
                ChatItemListViewBean bean = mModel.getMsgById(contactUsername, msgId);
                observable.onNext(bean);
            }
        }, new DisposableObserver<ChatItemListViewBean>() {

            @Override
            public void onNext(ChatItemListViewBean bean) {
                view.getNewMsgSuccess(bean);
            }

            @Override
            public void onError(Throwable e) {
                view.getNewMsgFail(e.getMessage());
            }

            @Override
            public void onComplete() {

            }

        });
    }


}
