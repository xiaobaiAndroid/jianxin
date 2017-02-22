package com.bzf.jianxin.main.presenter;

import com.bzf.jianxin.base.BasePresenter;
import com.bzf.jianxin.bean.Conversation;
import com.bzf.jianxin.main.model.ConversationModelImpl;
import com.bzf.jianxin.main.view.ConversationListView;

import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableObserver;

/**
 * com.bzf.jianxin.main.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class ConversationListPresenterImpl extends BasePresenter<ConversationListView,ConversationModelImpl> implements ConversationListPresenter {


    public ConversationListPresenterImpl(ConversationListView view) {
        super(view,new ConversationModelImpl());
    }

    @Override
    public void getConversationList() {
        asyncRequest(new ObservableOnSubscribe<List<Conversation>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Conversation>> e) throws Exception {
                List<Conversation> conversations = mModel.getConversationList();
                if(conversations!=null && conversations.size()>0){
                    e.onNext(conversations);
                }else{
                    throw new NullPointerException("会话列表为空");
                }
            }
        }, new DisposableObserver<List<Conversation>>() {
            @Override
            public void onNext(List<Conversation> conversations) {
                view.conversationListcSuccess(conversations);
            }

            @Override
            public void onError(Throwable e) {
                view.conversationListFail(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void getUpdateConversationList() {
        asyncRequest(new ObservableOnSubscribe<List<Conversation>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Conversation>> e) throws Exception {
                List<Conversation> conversations =  mModel.getUpdateConversationList();
                if(conversations!=null && conversations.size()>0){
                    e.onNext(conversations);
                }else{
                    throw new NullPointerException("conversation list is null");
                }
            }
        }, new DisposableObserver<List<Conversation>>() {
            @Override
            public void onNext(List<Conversation> conversations) {
                view.updateConversationListSucess(conversations);
            }

            @Override
            public void onError(Throwable e) {
                view.updateConversationListFail(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void setIsRead(Conversation conversation) {
        mModel.setIsRead(conversation);
    }


}
