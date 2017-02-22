package com.bzf.jianxin;

import android.app.Activity;
import android.app.Application;

import com.bzf.jianxin.bean.Users;
import com.bzf.jianxin.commonutils.AppTool;
import com.bzf.jianxin.commonutils.BmobTool;
import com.bzf.jianxin.commonutils.HuanXinTool;
import com.bzf.jianxin.commonutils.LogTool;
import com.bzf.jianxin.login.module.UserModelImpl;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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

    private List<WeakReference<Activity>> activitys;



    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        if(AppTool.applicationIsOnlyInit(this)){
            LogTool.i(MyApplication.class.getName(),"onCreate---");
            HuanXinTool.init(this);
            BmobTool.init(this);
            //初始化Fresco
            Fresco.initialize(this);
            mShowNewMessage = true;
            mCurrentChatUserName = "";
            activitys = new ArrayList<>();
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
                mCurrentUser = new UserModelImpl().getCurrentLoginUser();
            }
        });
    }

    public void addActivity(Activity activity){
        activitys.add(new WeakReference<Activity>(activity));
    }

    public void exitApp(){
        for(WeakReference<Activity> wr : activitys){
            if(wr!=null){
                Activity activity = wr.get();
                if(activity!=null || !activity.isFinishing()){
                    activity.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
