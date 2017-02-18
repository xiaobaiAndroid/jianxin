package com.bzf.jianxin.base;

import android.os.Handler;

/**
 * Presenter基类
 * com.bzf.jianxin.base
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class BasePresenter<T extends BaseView,V extends BaseModel> {

    protected Handler mHandler;
    protected T view;
    protected V mModel;

    public BasePresenter(T view,V model){
        this.view = view;
        mModel = model;
        mHandler = new Handler();
    }

    /**
     * 在Activity的destory()中调用此方法，分离Activity的引用，防止内存泄漏
     */
    public void detach(){
        mHandler = null;
        view = null;
    };

}
