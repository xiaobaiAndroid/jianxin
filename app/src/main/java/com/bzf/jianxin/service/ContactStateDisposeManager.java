package com.bzf.jianxin.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import com.bzf.jianxin.R;
import com.bzf.jianxin.commonutils.HuanXinTool;
import com.hyphenate.exceptions.HyphenateException;

/**
 * 好友状态处理的Manger
 * com.bzf.jianxin.service
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class ContactStateDisposeManager {
    private static final String TAG = ContactStateDisposeManager.class.getName();

    private static ContactStateDisposeManager instance;

    private static Context mContext;

    private ContactStateDisposeManager(){}

    public static ContactStateDisposeManager getInstance(Context context){
        if(instance==null){
            instance = new ContactStateDisposeManager();
            mContext = context;
        }
        return instance;
    }

    /**
     * 处理添加了的联系人
     * @param username
     */
    public void disposeAddContact(String username) {

    }

    /**
     * 处理 删除了联系人
     * @param username
     */
    public void disposeDeleteContact(String username) {
    }

    /**
     * 处理收到好友邀请时
     * @param username 发起加为好友用户的名称
     * @param reason 对方发起好友邀请时发出的文字性描述
     */
    public void disposeContactInvited(String username, String reason) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setContentTitle("好友请求");
        builder.setContentText(username+"请求添加你为好友,理由:"+reason);
        builder.setSmallIcon(R.mipmap.icon);
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        Notification notification = builder.build();
        NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(2,notification);
        try {
            HuanXinTool.acceptInvitation(username);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理 好友请求被同意
     * @param username
     */
    public void disposeAcceptedInvited(String username) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setSmallIcon(R.mipmap.icon);
        builder.setContentTitle("好友请求反馈");
        builder.setContentText(username+"同意了你的好友请求");
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        builder.setVisibility(Notification.VISIBILITY_PUBLIC);
        Notification notification = builder.build();
        NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(3,notification);
    }


    /**
     * 处理 好友请求被拒绝
     * @param username
     */
    public void disposeDeclinedInvited(String username) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setSmallIcon(R.mipmap.icon);
        builder.setContentTitle("好友请求反馈");
        builder.setContentText(username+"拒绝了你的好友请求");
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        //在任何情况下都会显示
        builder.setVisibility(Notification.VISIBILITY_PUBLIC);
        Notification notification = builder.build();
        NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(3,notification);
    }
}
