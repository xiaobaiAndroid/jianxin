package com.bzf.jianxin;

import android.app.Application;

import com.bzf.jianxin.bean.Users;
import com.bzf.jianxin.commonutils.AppTool;
import com.bzf.jianxin.commonutils.BmobTool;
import com.bzf.jianxin.commonutils.HuanXinTool;
import com.bzf.jianxin.commonutils.LogTool;
import com.bzf.jianxin.login.module.UserModelImpl;

/**
 * com.bzf.jianxin
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class MyApplication extends Application {

    private static MyApplication mApplication;

    /**
     * 当前的登录用户
     */
    public  Users mCurrentUser;

    /**
     * 新消息是否显示通知栏
     */
    public  boolean mShowNewMessage;

    /**
     * 当前聊天的用户,正在与谁聊天
     */
    public String mCurrentChatUserName;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        if(AppTool.applicationIsOnlyInit(this)){
            LogTool.i(MyApplication.class.getName(),"onCreate---");
            HuanXinTool.init(this);
            BmobTool.init(this);
            mShowNewMessage = true;
            mCurrentChatUserName = "";
        }
        initCurrentUser();
    }


    public static MyApplication getIns() {
        return mApplication;
    }

    public Users getmCurrentUser() {
        return mCurrentUser;
    }

    public void initCurrentUser() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mCurrentUser = new UserModelImpl().getCurrentUser();
            }
        });
    }
}
