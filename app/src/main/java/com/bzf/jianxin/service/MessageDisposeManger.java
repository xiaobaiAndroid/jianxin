package com.bzf.jianxin.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;

import com.bzf.jianxin.MyApplication;
import com.bzf.jianxin.R;
import com.bzf.jianxin.bean.Conversation;
import com.bzf.jianxin.commonutils.DateTool;
import com.bzf.jianxin.commonutils.HuanXinTool;
import com.bzf.jianxin.commonutils.LogTool;
import com.bzf.jianxin.login.module.UserModelImpl;
import com.bzf.jianxin.main.model.ConversationDao;
import com.bzf.jianxin.main.widget.MainActivity;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 处理消息的Manager
 * com.bzf.jianxin.service
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class MessageDisposeManger {
    private static final String TAG = MessageDisposeManger.class.getName();

    /**
     * 广播: 有新消息
     */
    public static final String ACTION_MESSAGE = "com.bzf.jianxin.message_broadcast";

    /**
     * 广播事件：更新会话列表
     */
    public static final String ACTION_UPDATE_CONVERSATION = "com.bzf.jianxin.update.conversation_broadcast";

    private static MessageDisposeManger instance;

    private MessageDisposeManger() {
    }

    public static MessageDisposeManger getInstance() {
        if (instance == null) {
            synchronized (MessageDisposeManger.class) {
                if (instance == null) {
                    instance = new MessageDisposeManger();
                }
            }
        }
        return instance;
    }

    /**
     * 处理消息
     * 1、把消息转化成Conversation并保存到数据库
     * 2、通知会话列表更新会话列表
     * 3、分类处理消息，发通知栏或者更新ChatActivity页面
     */
    public synchronized void disposeMsg(Context context, List<EMMessage> list) {
        saveData(list);
        HuanXinTool.importMsg(list);
        //对消息进行筛选
        for (EMMessage eMMessage : list) {
            if(eMMessage==null){
                return;
            }
            LogTool.i(TAG, "eMMessage.getUserName()=" + eMMessage.getUserName() + ",mCurrentChatUserName=" + MyApplication.getIns().mCurrentChatUserName);
            if (eMMessage.getUserName().equals(MyApplication.getIns().mCurrentChatUserName)) {
                Intent intent = new Intent(ACTION_MESSAGE);
                Bundle bundle = new Bundle();
                bundle.putString("msgId",eMMessage.getMsgId());
                intent.putExtras(bundle);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            } else {
                //更新消息列表,或通知栏
                showNewMessageNotify(context,eMMessage);
            }
        }

        updateConversationList(context);
    }

    /**
     * 更新会话列表
     *
     * @param context
     */
    private void updateConversationList(Context context) {
        Intent intent = new Intent(ACTION_UPDATE_CONVERSATION);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /**
     * 保存新消息到数据库
     * @param list
     */
    private void saveData(List<EMMessage> list) {
        try {
            ConversationDao dao = new ConversationDao(new UserModelImpl().getCurrentLoginUser().getUsername());
            List<Conversation> conversations = conversionEMMessageToConversation(list);
            LogTool.i(TAG, " List<Conversation> =" + conversations.toString());
            dao.beginTrasaction();
            dao.insertList(conversations);
            dao.endTrasaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 实体转化
     *
     * @param list
     * @return
     */
    private List<Conversation> conversionEMMessageToConversation(List<EMMessage> list) {
        List<Conversation> conversations = new ArrayList<>();
        for (EMMessage eMMessage : list) {
            if (eMMessage == null) {
                continue;
            }
            try {
                String contactUserName = eMMessage.getUserName();
                Conversation conversation = new Conversation();
                conversation.setNewMessageCount(HuanXinTool.getUnreadMsgCount(contactUserName));
                conversation.setAvatarUrl("");
                if (eMMessage.getType().ordinal() == EMMessage.Type.TXT.ordinal()) {//文本消息
                    EMTextMessageBody body = (EMTextMessageBody) eMMessage.getBody();
                    conversation.setBestNewMessage(body.getMessage());
                }
                conversation.setContactNickName("张三");
                conversation.setIsNewMsg(Conversation.IsNewMsgEnum.HAS_NEWMSG.ordinal());
                conversation.setTime(DateTool.fromateDate(new Date(eMMessage.getMsgTime()), DateTool.PATTERN_4));
                conversation.setContactUsername(contactUserName);
                conversations.add(conversation);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return conversations;
    }

    private static void showNewMessageNotify(Context context,EMMessage eMMessage) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.icon);
        builder.setContentTitle(eMMessage.getUserName());
        EMMessageBody messageBody = eMMessage.getBody();
        if(messageBody instanceof EMTextMessageBody){
            EMTextMessageBody body = (EMTextMessageBody) messageBody;
            builder.setContentText(body.getMessage());
        }
        builder.setWhen(System.currentTimeMillis());
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        builder.setContentIntent(pendingIntent);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(3, builder.build());
    }
}
