package com.bzf.jianxin.main.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.bzf.jianxin.R;
import com.bzf.jianxin.base.BaseFragment;
import com.bzf.jianxin.bean.Conversation;
import com.bzf.jianxin.commonutils.LogTool;
import com.bzf.jianxin.commonutils.ToastTool;
import com.bzf.jianxin.main.presenter.ConversationListPresenterImpl;
import com.bzf.jianxin.main.view.ConversationListView;
import com.bzf.jianxin.service.MessageDisposeManger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 消息列表页
 * com.bzf.jianxin.main.widget
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class ConversationListFragment extends BaseFragment<ConversationListPresenterImpl> implements ConversationListView {

    @BindView(R.id.mRv_Conversation)
    RecyclerView mRvConversation;

    private ConversationAdapter mAdapter;
    private List<Conversation> mList;
    private LocalBroadcastManager mLocalBroadcastManager;

    @Override
    protected View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_messagelist, null);
    }

    @Override
    protected void init() {
        mPresenter = new ConversationListPresenterImpl(this);
        mList = new ArrayList<>();
        mAdapter = new ConversationAdapter(mList);
        initListener();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvConversation.setLayoutManager(linearLayoutManager);
        mRvConversation.setAdapter(mAdapter);
        mPresenter.getConversationList();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver,new IntentFilter(MessageDisposeManger.ACTION_UPDATE_CONVERSATION));
    }

    @Override
    public void onDestroyView() {
        mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
        super.onDestroyView();

    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new ContactsAdapter.OnItemClickListener() {
            @Override
            public void OnItemListener(View view, int position) {
                Conversation conversation = mList.get(position);
                if(conversation!=null){
                    if(conversation.getMessageType()==Conversation.MessageTypeEnum.SIMPLE_CHAT.ordinal()){
                        //单聊
                    }else{
                        //群聊
                    }
                }
            }
        });
    }

    @Override
    public void conversationListcSuccess(List<Conversation> list) {
        if(list==null || list.size()<=0){
            return ;
        }
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void conversationListFail(String msg) {
        ToastTool.shotMessage(msg);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                    //有新消息，更新会话列表。
                mPresenter.getUpdateConversationList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void updateConversationListSucess(List<Conversation> list) {
        LogTool.i(ConversationListFragment.class.getName(),"updateConversationListSucess--list="+list.toArray());
        mList.clear();
        mList.addAll(list);
        if(mList.size()==list.size()){
            for(int position = 0; position<mList.size();position++){
                Conversation conversation = mList.get(position);
                if(conversation.getIsNewMsg()==Conversation.IsNewMsgEnum.HAS_NEWMSG.ordinal()){
                    LogTool.i(ConversationListFragment.class.getName(),"notifyItemChanged----");
                    mAdapter.notifyItemChanged(position);
                    mPresenter.setIsRead(conversation);
                }
            }
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateConversationListFail(String msg) {
        ToastTool.shotMessage(msg);
    }
}
