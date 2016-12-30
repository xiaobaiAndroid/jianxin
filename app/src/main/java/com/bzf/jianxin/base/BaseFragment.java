package com.bzf.jianxin.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bzf.jianxin.MyApplication;

import butterknife.ButterKnife;

/**
 * com.bzf.jianxin.base
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    protected T mPresenter;
    private View mLayoutView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = createView(inflater);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 有消息显示到通知栏
     */
    protected void showNewMessageToNotification() {
        MyApplication.getIns().mShowNewMessage = true;
    }

    protected abstract View createView(LayoutInflater inflater);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLayoutView = getView();
        showNewMessageToNotification();
        init();
    }

    protected abstract void init();

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detach();
        }
        super.onDestroyView();
    }
}
