package com.bzf.jianxin.base;

/**
 * com.bzf.jianxin.base
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface BasePresenter {

    /**
     * 在Activity的destory()中调用此方法，分离Activity的引用，防止内存泄漏
     */
    public void detach();
}
