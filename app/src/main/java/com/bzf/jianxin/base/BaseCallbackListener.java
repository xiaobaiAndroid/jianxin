package com.bzf.jianxin.base;

/**
 * com.bzf.jianxin.base
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface BaseCallbackListener<T> {
    void success(T t);
    void fail(Throwable e);
}
