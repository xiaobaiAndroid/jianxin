package com.bzf.jianxin.bean;

/**
 * 会话实体
 * com.bzf.jianxin.bean
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class Conversation {

    /**
     * 联系人的用户名
     */
    private String contactUsername;
    /**
     * 联系人的昵称
     */
    private String contactNickName;
    /**
     * 会话类型：群聊，单聊，公众号
     */
    private Integer messageType = MessageTypeEnum.SIMPLE_CHAT.ordinal();
    /**
     * 最新的一条消息
     */
    private String bestNewMessage;
    /**
     * 收到最新消息的时间
     */
    private String time;
    /**
     * 联系人头像
     */
    private String avatarUrl;
    /**
     * 新消息的数量
     */
    private Integer newMessageCount;

    /**
     * 会话是否置顶
     */
    private Integer toTop = ToTopEnum.NOTTOTOP.ordinal();

    /**
     * 消息是否免打扰
     */
    private Integer messageIsRemind = MessageIsRemindEnum.REMIND.ordinal();

    /**
     * 是否有新消息的标记
     */
    private Integer isNewMsg = IsNewMsgEnum.NOT_NEWMSG.ordinal();

    public Integer getIsNewMsg() {
        return isNewMsg;
    }

    public void setIsNewMsg(Integer isNewMsg) {
        this.isNewMsg = isNewMsg;
    }

    public String getContactUsername() {
        return contactUsername;
    }

    public void setContactUsername(String contactUsername) {
        this.contactUsername = contactUsername;
    }

    public String getContactNickName() {
        return contactNickName;
    }

    public void setContactNickName(String contactNickName) {
        this.contactNickName = contactNickName;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getBestNewMessage() {
        return bestNewMessage;
    }

    public void setBestNewMessage(String bestNewMessage) {
        this.bestNewMessage = bestNewMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getNewMessageCount() {
        return newMessageCount;
    }

    public void setNewMessageCount(Integer newMessageCount) {
        this.newMessageCount = newMessageCount;
    }

    public Integer getToTop() {
        return toTop;
    }

    public void setToTop(Integer toTop) {
        this.toTop = toTop;
    }

    public Integer getMessageIsRemind() {
        return messageIsRemind;
    }

    public void setMessageIsRemind(Integer messageIsRemind) {
        this.messageIsRemind = messageIsRemind;
    }

    public enum MessageIsRemindEnum{
        NOTREMIND,REMIND;
    }

    public enum ToTopEnum{
        NOTTOTOP,TOTOP;
    }

    public enum MessageTypeEnum{
        SIMPLE_CHAT,GROUP_CHAT;
    }

    public enum IsNewMsgEnum{
        /**新消息*/
        HAS_NEWMSG,
        /**不是新消息*/
        NOT_NEWMSG;
    }


    @Override
    public String toString() {
        return "contactUsername="+contactUsername+",bestNewMessage="+bestNewMessage+"newMessageCount="+newMessageCount;
    }
}
