package com.bzf.jianxin.bean;

import cn.bmob.v3.BmobUser;

/**
 * 用户表
 * com.bzf.jianxin.bean
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class User extends BmobUser{

    /**
     * 头像名称
     */
    private String avatarName;

    /**
     * 头像的Url
     */
    private String avatarUrl;

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

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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
