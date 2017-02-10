package com.bzf.jianxin.main.widget;

import android.view.LayoutInflater;
import android.view.View;

import com.bzf.jianxin.R;
import com.bzf.jianxin.base.BaseFragment;

/**
 * 消息列表页
 * com.bzf.jianxin.main.widget
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class MessageListFragment extends BaseFragment{

    @Override
    protected View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_messagelist,null);
    }

    @Override
    protected void init() {

    }
}
