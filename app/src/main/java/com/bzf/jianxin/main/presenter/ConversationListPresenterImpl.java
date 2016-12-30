package com.bzf.jianxin.main.presenter;

import com.bzf.jianxin.base.BaseCallbackListener;
import com.bzf.jianxin.base.BasePresenter;
import com.bzf.jianxin.bean.Conversation;
import com.bzf.jianxin.main.model.ConversationModel;
import com.bzf.jianxin.main.model.ConversationModelImpl;
import com.bzf.jianxin.main.view.ConversationListView;
import com.bzf.jianxin.main.view.UpdateConversationListView;

import java.util.List;

/**
 * com.bzf.jianxin.main.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class ConversationListPresenterImpl extends BasePresenter implements ConversationListPresenter {

    private ConversationModel mConversationModel;

    public ConversationListPresenterImpl() {
        super();
        mConversationModel = new ConversationModelImpl();
    }

    @Override
    public void getConversationList(final ConversationListView view) {
        mConversationModel.getConversationList(new BaseCallbackListener<List<Conversation>>() {
            @Override
            public void success(final List<Conversation> conversations) {
                if(mHandler!=null){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.conversationListcSuccess(conversations);
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
                            view.conversationListFail(e.getMessage());
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getUpdateConversationList(final UpdateConversationListView view) {
        mConversationModel.getUpdateConversationList( new BaseCallbackListener<List<Conversation>>() {
            @Override
            public void success(final List<Conversation> conversations) {
                if(mHandler!=null){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.updateConversationListSucess(conversations);
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
                            view.updateConversationListFail(e.getMessage());
                        }
                    });
                }
            }
        });
    }

    @Override
    public void setIsRead(Conversation conversation) {
        mConversationModel.setIsRead(conversation);
    }


}
