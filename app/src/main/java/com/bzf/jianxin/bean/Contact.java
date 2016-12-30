package com.bzf.jianxin.bean;

import cn.bmob.v3.BmobObject;

/**
 * 联系人bean
 * com.bzf.jianxin.bean
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class Contact extends BmobObject{

    /**
     * 用户的账号 作为查询所有联系人的条件
     */
    private String usernameId;

    /**
     *联系人账号 作为主键
     */
    private String contactUsername;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 地区
     */
    private String region;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 黑名单
     */
    private Boolean blacklist = false;

    public Boolean getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Boolean blacklist) {
        this.blacklist = blacklist;
    }

    public String getUsernameId() {
        return usernameId;
    }

    public void setUsernameId(String usernameId) {
        this.usernameId = usernameId;
    }

    public String getContactUsername() {
        return contactUsername;
    }

    public void setContactUsername(String contactUsername) {
        this.contactUsername = contactUsername;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
