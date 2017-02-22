package com.bzf.jianxin.main.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bzf.jianxin.R;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * 通讯录头部
 * com.bzf.jianxin.main.widget
 * Author: baizhengfu
 * Email：709889312@qq.com
 */

public class ContactListHeadView implements RecyclerArrayAdapter.ItemView {

    @Override
    public View onCreateView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_list_footer,null);
    }

    @Override
    public void onBindView(View headerView) {

    }
}
