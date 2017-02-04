package com.bzf.jianxin.base;

/**
 * com.bzf.jianxin.base
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface BaseView<T> {

    void showDialog();
    void closeDialog();
    void success(T t);
    void fail(String message);
}
