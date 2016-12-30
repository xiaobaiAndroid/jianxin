package com.bzf.jianxin.bean;

/**
 * 用户表 与本地数据库表相对应
 * com.bzf.jianxin.bean
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class Users {

    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String psw;
    /**
     * 第一次登陆标识,0表示否，1表示是
     */
    private Integer isFirstLogin = 1;

    /**
     * 是否是当前登录用户 0表示否，1表示是
     */
    private Integer isCurrent = 0;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public Integer getFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(Integer firstLogin) {
        isFirstLogin = firstLogin;
    }

    public Integer getCurrent() {
        return isCurrent;
    }

    public void setCurrent(Integer current) {
        isCurrent = current;
    }
}
