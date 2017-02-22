package com.bzf.jianxin.main.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bzf.jianxin.R;
import com.bzf.jianxin.bean.Conversation;
import com.bzf.jianxin.main.customwidget.BadgeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * com.bzf.jianxin.main.widget
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {

    private List<Conversation> mList;
//    private ContactsAdapter.OnItemClickListener onItemClickListener;
    private Context mContext;

    public ConversationAdapter(List<Conversation> list) {
        this.mList = list;
    }


//    public void setOnItemClickListener(ContactsAdapter.OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }

    @Override
    public ConversationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_conversation, viewGroup, false);
        return new ConversationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConversationViewHolder viewHolder, int position) {
        //设置数据
        Conversation conversation = mList.get(position);
        if (conversation != null) {
            viewHolder.mTvNewMessage.setText(conversation.getBestNewMessage());
            viewHolder.mTvTime.setText(conversation.getTime());
            viewHolder.mTvUsername.setText(conversation.getContactNickName());
            int newMsgCount = conversation.getNewMessageCount();
            if (newMsgCount != 0) {
                viewHolder.mBadgeView.setText(String.valueOf(newMsgCount));
            } else {
                viewHolder.mBadgeView.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ConversationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.mRl_conversation_item)
        RelativeLayout mRlConversationItem;
        @BindView(R.id.mIv_avatar)
        ImageView mIvAvatar;
        @BindView(R.id.mTv_username)
        TextView mTvUsername;
        @BindView(R.id.mTv_newMessage)
        TextView mTvNewMessage;
        @BindView(R.id.mTv_time)
        TextView mTvTime;
        @BindView(R.id.mBadgeView)
        BadgeView mBadgeView;


        public ConversationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mRlConversationItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            onItemClickListener.OnItemListener(view, getLayoutPosition());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    ;
}
