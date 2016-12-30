package com.bzf.jianxin.login.module;

import android.text.TextUtils;

import com.bzf.jianxin.AppDatabaseHelper;
import com.bzf.jianxin.base.BaseCallbackListener;
import com.bzf.jianxin.bean.Contact;
import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.bean.Users;
import com.bzf.jianxin.commonutils.BmobTool;
import com.bzf.jianxin.commonutils.HuanXinTool;
import com.bzf.jianxin.commonutils.LogTool;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * com.bzf.jianxin.login.module
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class UserModelImpl implements UserModel {

    private static final String TAG = UserModelImpl.class.getName();

    //先登录环信，再登录Bmob
    @Override
    public void login(final String userName, final String psw, final BaseCallbackListener listener) {
        LogTool.i("http", "userName=" + userName + ",psw=" + psw);
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(psw)) {
            listener.fail(new Throwable("账号或密码为空"));
            return;
        }
        EMClient.getInstance().login(userName, psw, new EMCallBack() {
            @Override
            public void onSuccess() {
                //保证进入主页面后本地会话和群组都 load 完毕
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                LogTool.i("http", "登录环信成功");
                loginBmob(userName, psw, listener);
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
    public void register(final String username, final String psw, final String nickName, final BaseCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //注册失败会抛出HyphenateException
                    //注册用户名会自动转为小写字母，所以建议用户名均以小写注册。（强烈建议开发者通过后台调用 REST 接口去注册环信 ID，客户端注册方法不提倡使用。）
                    EMClient.getInstance().createAccount(username, psw);
                    LogTool.i(TAG, "环信注册成功");
                    registerBmob(username, psw, nickName, listener);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogTool.i(TAG, "环信注册失败");
                    listener.fail(e);
                }
            }
        }).start();
    }

    private void registerBmob(String username, String psw, String nickName, final BaseCallbackListener<User> listener) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(psw);
        user.setNickName(nickName);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    listener.success(user);
                    LogTool.i(TAG, "Bmob注册成功");
                } else {
                    listener.fail(e);
                    LogTool.i(TAG, "Bmob注册失败");
                }
            }
        });
    }

    private void loginBmob(final String userName, final String psw, final BaseCallbackListener<User> listener) {
        User user = new User();
        user.setUsername(userName);
        user.setPassword(psw);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    LogTool.i(TAG, "登录Bmob成功");
                    saveDatabase(userName, psw);
                    listener.success(user);
                } else {
                    LogTool.i(TAG, "登录Bmob失败");
                    listener.fail(e);
                }
            }
        });
    }

    /**
     * 把当前登录用户保存到Users表中
     *
     * @param userName
     * @param psw
     */
    private void saveDatabase(String userName, String psw) {
        try {
            UsersDao dao = new UsersDao(AppDatabaseHelper.APP_DATABASE_NAME);
            Users users = new Users();
            users.setUsername(userName);
            users.setPsw(psw);
            users.setCurrent(UsersDao.CurrentUserEnum.ISCURRENT.ordinal());
            dao.beginTrasaction();
            if (dao.isExist(userName)) {
                users.setFirstLogin(UsersDao.FirstLoginEnum.NOFIRST.ordinal());
                dao.updateUsers(users);
            } else {
                users.setFirstLogin(UsersDao.FirstLoginEnum.ISFIRST.ordinal());
                dao.saveUsers(users);
            }
            dao.endTrasaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void getContactList(final BaseCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> usernames = HuanXinTool.getAllContacts();
                    LogTool.i(TAG, "获取环信所有好友成功：");
                    if (usernames != null) {
                        LogTool.i(TAG, usernames.toString());
                    }
                    if (usernames != null && usernames.size() > 0) {
                        String currentUser = EMClient.getInstance().getCurrentUser();
                        LogTool.i(TAG, "usernameId=" + currentUser);
                        //到Bomb上获取好友信息,需要在Bmob中建一个Contact表，
                        BmobQuery<Contact> query = new BmobQuery<Contact>();
                        query.setLimit(500);
                        query.addWhereEqualTo("usernameId", currentUser);
                        query.findObjects(new FindListener<Contact>() {
                            @Override
                            public void done(List<Contact> list, BmobException e) {
                                if (e == null) {
                                    LogTool.i(TAG, "获取Bmob上的好友列表成功");
                                    listener.success(list);
                                } else {
                                    LogTool.i(TAG, "获取Bmob上的好友列表失败");
                                    listener.fail(e);

                                }
                            }
                        });

                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    LogTool.i(TAG, "获取环信所有好友失败：");
                    listener.fail(e);
                }
            }
        }).start();

    }

    @Override
    public Users getCurrentUser() {
        Users users = null;
        try {
            UsersDao dao = new UsersDao(AppDatabaseHelper.APP_DATABASE_NAME);
            dao.beginTrasaction();
            users = dao.queryUser(UsersDao.CurrentUserEnum.ISCURRENT.ordinal());
            dao.endTrasaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    //退出环信，再退出Bmob,最后到本地数据库Users表查找当前用户，把isCurrent标志改为不是当前用户，把psw置为null
    @Override
    public void exitLogin(final BaseCallbackListener<String> listener) {
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                BmobUser.logOut();
                updateUsers();
                listener.success("退出登录成功");
                LogTool.i(TAG, "退出登录成功");
            }

            @Override
            public void onError(int i, String s) {
                listener.fail(new Throwable(s));
                LogTool.i(TAG, "退出登录失败：" + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    @Override
    public void queryUser(String username, final BaseCallbackListener<List<User>> listener) {
        LogTool.i(TAG, "queryUser()---username==" + username);
        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        userBmobQuery.addWhereEqualTo("username", username);
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    LogTool.i(TAG, "queryUser()---list==" + list.size());
                    listener.success(list);
                } else {
                    LogTool.i(TAG, "queryUser()---BmobException==" + e.getMessage());
                    listener.fail(e);
                }
            }
        });
    }

    @Override
    public void addUser(String username, String reason) {
        try {
            HuanXinTool.addContact(username, reason);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserAvatar(final File imageFile, final String username, final BaseCallbackListener<String> listener) {
        if(imageFile==null || !imageFile.exists() ){
            listener.fail(new Exception("设置头像不存在"));
            return ;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    final String imageName = "avatar_" + username + ".jpg";
                    final File mAvatarFile = new File(imageFile.getParent(), imageName);
                    imageFile.renameTo(mAvatarFile);
                    imageFile.delete();
                    BmobTool.uploadFile(mAvatarFile.getAbsolutePath(), new BaseCallbackListener<String>() {
                        @Override
                        public void success(String imageUrl) {
                            User user = new User();
                            user.setAvatarUrl(imageUrl);
                            user.setAvatarName(imageName);
                            updateUserTable(user,listener);
                        }

                        @Override
                        public void fail(Throwable e) {
                            listener.fail(e);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.fail(e);
                }
            }
        }.start();
    }

    /**
     * 更新User表
     * @param user
     * @param listener
     */
    private void updateUserTable(User user, final BaseCallbackListener<String> listener) {
        User currentUser = BmobUser.getCurrentUser(User.class);
        user.update(currentUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    listener.success("更新用户头像成功");
                } else {
                    listener.fail(e);
                }
            }
        });
    }

    private void updateUsers() {
        try {
            UsersDao dao = new UsersDao(AppDatabaseHelper.APP_DATABASE_NAME);
            dao.beginTrasaction();
            Users users = dao.queryUser(UsersDao.CurrentUserEnum.ISCURRENT.ordinal());
            if (users != null) {
                users.setCurrent(UsersDao.CurrentUserEnum.NOCURRENT.ordinal());
                users.setPsw("");
                dao.updateUsers(users);
            }
            dao.endTrasaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
