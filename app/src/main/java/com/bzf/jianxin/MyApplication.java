package com.bzf.jianxin;

import android.app.Application;

import com.bzf.jianxin.commonutils.HuanXinTool;

import cn.bmob.v3.Bmob;

/**
 * com.bzf.jianxin
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class MyApplication extends Application {

    private MyApplication mApplication;

    /**
     * Bmob SDK初始化必须用到此密钥
     */
    private static final String BMOBAPPLICATIONID = "a08de45f94bfcfb9d1697319657a9b69";


    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        HuanXinTool.init(this);
        initBmob();
    }

    /**
     * 初始化BmobSDK
     */
    private void initBmob() {
        Bmob.initialize(this,BMOBAPPLICATIONID);
    }


    public MyApplication getIns() {
        return mApplication;
    }
}
