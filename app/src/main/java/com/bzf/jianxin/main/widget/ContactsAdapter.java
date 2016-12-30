package com.bzf.jianxin.main.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bzf.jianxin.R;
import com.bzf.jianxin.bean.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * com.bzf.jianxin.main.widget
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Contact> mContactList = new ArrayList<>();

    private OnItemClickListener mListener;

    public ContactsAdapter(List<Contact> list){
        mContactList = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mTv_username;

        public ContactViewHolder(View itemView) {
            super(itemView);
            mTv_username = (TextView) itemView.findViewById(R.id.tv_username);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.OnItemListener(v,getLayoutPosition());
        }
    };

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_contact, viewGroup, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ContactViewHolder holder = (ContactViewHolder) viewHolder;
        Contact contact = mContactList.get(position);
        if(contact!=null){
            holder.mTv_username.setText(contact.getContactUsername());
        }

    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    public interface OnItemClickListener{
           void OnItemListener(View view,int position);
    }

}
