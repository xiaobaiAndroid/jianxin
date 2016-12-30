package com.bzf.jianxin.commonutils;

import android.content.Context;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;
import java.util.Map;

/**
 * 环信工具类
 * com.bzf.jianxin.commonutils
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class HuanXinTool {

    private static final String TAG = HuanXinTool.class.getName();

    /**
     * 初始化环信SDK
     *
     * @param context
     */
    public static void init(Context context) {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //关闭自动登录
        options.setAutoLogin(false);
        //设置好友请求是自动同意
        options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(context, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
        EMClient.getInstance().setDebugMode(false);

    }

    /**
     * 获取所有联系人列表
     *
     * @return
     * @throws HyphenateException
     */
    public static List<String> getAllContacts() throws HyphenateException {
        return EMClient.getInstance().contactManager().getAllContactsFromServer();
    }

    /**
     * 添加联系人
     *
     * @param username 添加的好友的username
     * @param reasion  添加理由
     * @throws HyphenateException
     */
    public static void addContact(String username, String reasion) throws HyphenateException {
        EMClient.getInstance().contactManager().addContact(username, reasion);
    }

    /**
     * 接收消息，只需要执行一次，并保证在整个app生命周期内有效
     *
     * @param listener
     * @throws NullPointerException
     */
    public static void readMessageListener(EMMessageListener listener) throws NullPointerException {
        if (listener == null) {
            throw new NullPointerException("消息监听器为null");
        } else {
            EMClient.getInstance().chatManager().addMessageListener(listener);
        }
    }

    /**
     * @param content        消息文字内容
     * @param toChatUsername 对方用户或者群聊的id
     * @param chatType       如果是群聊，设置chattype，默认是单聊
     * @param callBack       监听消息的发送及接收状态。
     */
    public static void sendTextMessage(String content, String toChatUsername, EMMessage.ChatType chatType, EMCallBack callBack) {
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        message.setChatType(chatType);
        message.setMessageStatusCallback(callBack);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 不需要的时候移除listener
     *
     * @param mMessageListener
     */
    public static void detachMessageListener(EMMessageListener mMessageListener) {
        if (mMessageListener != null) {
            EMClient.getInstance().chatManager().removeMessageListener(mMessageListener);
        }
    }

    /**
     * 获取聊天记录 20条
     *
     * @param chatUserName 聊天用户的用户名
     * @param startMsgId
     * @param pagesize
     * @return
     */
    public static List<EMMessage> getChatRecord(String chatUserName, String startMsgId, int pagesize) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(chatUserName);
        //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
        //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
        return conversation.loadMoreMsgFromDB(startMsgId, pagesize);
    }

    /**
     * 获取指定用户名的所有聊天记录
     *
     * @param chatUserName
     * @return
     */
    public static List<EMMessage> getAllChatRecord(String chatUserName) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(chatUserName);
        return conversation.getAllMessages();
    }

    /**
     * 获取未读消息数量
     *
     * @param chatUserName
     * @return
     */
    public static int getUnreadMsgCount(String chatUserName) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(chatUserName);
        return conversation.getUnreadMsgCount();
    }

    /**
     * 指定会话消息未读数清零
     *
     * @param chatUserName
     */
    public static void clearUnreadMsg(String chatUserName) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(chatUserName);
        conversation.markAllMessagesAsRead();
    }

    /**
     * 把一条消息置为已读
     *
     * @param chatUserName
     * @param messageId
     */
    public static void setMsgIsRead(String chatUserName, String messageId) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(chatUserName);
        conversation.markMessageAsRead(messageId);
    }

    /**
     * 所有未读消息数清零
     */
    public static void setAllConversationIsRead() {
        EMClient.getInstance().chatManager().markAllConversationsAsRead();
    }

    /**
     * 获取所有会话,如果出现偶尔返回的conversations的sizi为0，
     * 那很有可能是没有调用EMClient.getInstance().chatManager().loadAllConversations()具体用法请参考登录章节
     */
    public static Map<String, EMConversation> getAllConversations() {
        return EMClient.getInstance().chatManager().getAllConversations();
    }

    /**
     * 删除和某个user会话
     *
     * @param chatUserName
     */
    public static void deleteConversation(String chatUserName) {
        //如果需要保留聊天记录，传false
        EMClient.getInstance().chatManager().deleteConversation(chatUserName, true);
    }

    /**
     * 删除当前会话的某条聊天记录
     *
     * @param chatUserName
     * @param msgId
     */
    public static void removeMessage(String chatUserName, String msgId) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(chatUserName);
        conversation.removeMessage(msgId);
    }

    /**
     * 根据msgid获取消息
     * @param chatUserName
     * @param messageId 需要获取的消息id
     * @param markAsRead 是否获取消息的同时标记消息为已读
     * @return
     */
    public static EMMessage getMessage(String chatUserName,String messageId,boolean markAsRead)throws Exception {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(chatUserName);
        return conversation.getMessage(messageId,markAsRead);
    }

    /**
     * 导入消息到数据库
     */
    public static void importMsg(List<EMMessage> msgList) {
        EMClient.getInstance().chatManager().importMessages(msgList);
    }

    /**
     * 监听好友状态事件
     *
     * @param mEMContactListener
     */
    public static void setContactListener(EMContactListener mEMContactListener) {
        if (mEMContactListener == null) {
            throw new NullPointerException("EMContactListener==null");
        } else {
            EMClient.getInstance().contactManager().setContactListener(mEMContactListener);
        }
    }

    /**
     * 同意好友请求
     * 默认好友请求是自动同意的，如果要手动同意需要在初始化SDK时调用 opptions.setAcceptInvitationAlways(false);
     *
     * @param username
     * @throws HyphenateException
     */
    public static void acceptInvitation(String username) throws HyphenateException {
        EMClient.getInstance().contactManager().acceptInvitation(username);
    }

    /**
     * 拒绝好友请求
     *
     * @param username
     * @throws HyphenateException
     */
    public static void declineInvitation(String username) throws HyphenateException {
        EMClient.getInstance().contactManager().declineInvitation(username);
    }

    /**
     * 删除好友
     *
     * @param username
     * @throws HyphenateException
     */
    public static void deleteContact(String username) throws HyphenateException {
        EMClient.getInstance().contactManager().deleteContact(username);
    }
}
