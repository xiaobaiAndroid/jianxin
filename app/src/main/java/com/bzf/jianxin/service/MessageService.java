package com.bzf.jianxin.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import com.bzf.jianxin.R;
import com.bzf.jianxin.commonutils.HuanXinTool;
import com.bzf.jianxin.commonutils.LogTool;
import com.bzf.jianxin.main.widget.MainActivity;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMMessage;

import java.util.List;

public class MessageService extends Service {

    private static final String TAG = MessageService.class.getName();


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createForegroundService();
    }

    /**
     * 创建前台服务
     */
    private void createForegroundService() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.icon);
        builder.setContentTitle("简信");
        builder.setContentText("正在后台运行");
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(false);
        builder.setWhen(System.currentTimeMillis());
        Notification notification = builder.build();
        startForeground(1,notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            LogTool.i(TAG, "onStartCommand-----mMessageListener==null: " + (mMessageListener == null));
            HuanXinTool.readMessageListener(mMessageListener);
            HuanXinTool.setContactListener(mEMContactListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        HuanXinTool.detachMessageListener(mMessageListener);
        super.onDestroy();
    }


    /**
     * 显示新消息到通知栏
     *
     * @param list
     */
    private void showNewMessageNotify(List<EMMessage> list) {
        //如果有多条消息，直接跳到消息列表

    }

    /**
     * 消息监听器
     */
    private EMMessageListener mMessageListener = new EMMessageListener() {
        /**
         * 在接受到文本消息，图片，视频，语音，地理位置，文件这些消息体的时候，会通过此接口通知用户。
         *
         * @param list
         */
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            //收到消息
            LogTool.i(TAG, "收到消息:" + list.toString());
            //发送广播，通知有新消息
            MessageDisposeManger.getInstance().disposeMsg(getApplicationContext(),list);

        }

        /**
         * 这个接口只包含命令的消息体，包含命令的消息体通常不对用户展示。
         *
         * @param list
         */
        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {
            //收到透传消息
            LogTool.i(TAG, "收到透传消息" + list.toString());
        }

        /**
         * 接受到消息体的已读回执, 消息的接收方已经阅读此消息。
         *
         * @param list
         */
        @Override
        public void onMessageRead(List<EMMessage> list) {
            //收到已读回执
            LogTool.i(TAG, "收到已读回执" + list.toString());
        }

        /**
         * 收到消息体的发送回执，消息体已经成功发送到对方设备。
         *
         * @param list
         */
        @Override
        public void onMessageDelivered(List<EMMessage> list) {
            LogTool.i(TAG, "收到消息体的发送回执" + list.toString());
        }

        /**
         * 接受消息发生改变的通知，包括消息ID的改变。消息是改变后的消息。
         * @param emMessage 发生改变的消息
         * @param change    包含改变的信息
         */
        @Override
        public void onMessageChanged(EMMessage emMessage, Object change) {
            LogTool.i(TAG, emMessage.toString() + change.toString());
        }
    };

    /**
     * 好友状态监听器
     */
    private EMContactListener mEMContactListener = new EMContactListener() {
        /**
         * 增加联系人时回调此方法
         * @param username 增加的联系人
         */
        @Override
        public void onContactAdded(String username) {
            ContactStateDisposeManager.getInstance(getApplicationContext()).disposeAddContact(username);
        }

        /**
         * 被删除时回调此方法
         * @param username
         */
        @Override
        public void onContactDeleted(String username) {
            ContactStateDisposeManager.getInstance(getApplicationContext()).disposeDeleteContact(username);
        }

        /**
         * 收到好友邀请
         * @param username 发起加为好友用户的名称
         * @param reason 对方发起好友邀请时发出的文字性描述
         */
        @Override
        public void onContactInvited(String username, String reason) {
            ContactStateDisposeManager.getInstance(getApplicationContext()).disposeContactInvited(username,reason);
        }

        /**
         * 好友请求被同意
         * @param username
         */
        @Override
        public void onFriendRequestAccepted(String username) {
            ContactStateDisposeManager.getInstance(getApplicationContext()).disposeAcceptedInvited(username);
        }

        /**
         * 好友请求被拒绝
         * @param username
         */
        @Override
        public void onFriendRequestDeclined(String username) {
            ContactStateDisposeManager.getInstance(getApplicationContext()).disposeDeclinedInvited(username);
        }
    };

}
