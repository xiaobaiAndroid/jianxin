package com.bzf.jianxin.login.module;

import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.commonutils.LogTool;
import com.bzf.jianxin.register.module.RegisterListener;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * com.bzf.jianxin.login.module
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class UserModelImpl implements UserModel{

    //先登录环信，再登录Bmob
    @Override
    public void login(final String userName, final String psw, final LoginListener listener) {
        LogTool.i("http","userName="+userName+",psw="+psw);
        EMClient.getInstance().login(userName, psw, new EMCallBack() {
            @Override
            public void onSuccess() {
                //保证进入主页面后本地会话和群组都 load 完毕
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                LogTool.i("http","登录环信成功");
                loginBmob(userName,psw,listener);
            }

            @Override
            public void onError(int i, String s) {
                listener.fail(new Throwable(s));
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

    }

    @Override
    public void register(final String username, final String psw, final RegisterListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //注册失败会抛出HyphenateException
                    //注册用户名会自动转为小写字母，所以建议用户名均以小写注册。（强烈建议开发者通过后台调用 REST 接口去注册环信 ID，客户端注册方法不提倡使用。）
                    EMClient.getInstance().createAccount(username,psw);
                    LogTool.i("http","环信注册成功");
                    registerBmob(username,psw,listener);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    LogTool.i("http","环信注册失败");
                    listener.fail(e);
                }
            }
        }).start();
    }

    private void registerBmob(String username, String psw, final RegisterListener listener) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(psw);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e==null){
                    listener.success(user);
                    LogTool.i("http","Bmob注册成功");
                }else{
                    listener.fail(e);
                    LogTool.i("http","Bmob注册失败");
                }
            }
        });
    }

    private void loginBmob(String userName, String psw, final LoginListener listener) {
        User user = new User();
        user.setUsername(userName);
        user.setPassword(psw);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e==null){
                    LogTool.i("http","登录Bmob成功");
                    listener.success(user);
                }else{
                    LogTool.i("http","登录Bmob失败");
                    listener.fail(e);
                }
            }
        });
    }
}
