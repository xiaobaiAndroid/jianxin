package com.bzf.jianxin.main.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bzf.jianxin.R;
import com.bzf.jianxin.bean.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.indexablerv.IndexableAdapter;

/**
 * com.bzf.jianxin.main.widget
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class ContactsAdapter extends IndexableAdapter<Contact> {


    private  LayoutInflater mLayoutInflater;

    public ContactsAdapter(Context context){
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.item_sliderbar_title, parent, false);
        return new TitleViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.item_contact_list, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        TitleViewHolder viewHolder = (TitleViewHolder) holder;
        viewHolder.tvTitle.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, Contact contact) {
        ContactViewHolder contactViewHolder = (ContactViewHolder) holder;
        contactViewHolder.tvName.setText(contact.getContactUsername());
        contactViewHolder.ivAvatar.setImageResource(R.drawable.default_useravatar);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.header)
        TextView header;
        @BindView(R.id.vLine)
        View vLine;
        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;

        public ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
