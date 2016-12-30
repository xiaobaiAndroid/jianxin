package com.bzf.jianxin.base;

import android.os.Handler;

/**
 * com.bzf.jianxin.base
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class BasePresenter {
    protected Handler mHandler;

    public BasePresenter(){
        mHandler = new Handler();
    }

    /**
     * 在Activity的destory()中调用此方法，分离Activity的引用，防止内存泄漏
     */
    public void detach(){
        mHandler = null;
    };

}
