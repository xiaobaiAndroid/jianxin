package com.bzf.jianxin.main.model;

import com.bzf.jianxin.base.BaseCallbackListener;
import com.bzf.jianxin.base.BaseModel;
import com.bzf.jianxin.bean.Conversation;
import com.bzf.jianxin.bean.Users;
import com.bzf.jianxin.login.module.UserModelImpl;

import java.util.List;

/**
 * com.bzf.jianxin.main.model
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class ConversationModelImpl extends BaseModel implements ConversationModel{

    private static final String TAG = ConversationModelImpl.class.getName();

    @Override
    public void getConversationList(final BaseCallbackListener<List<Conversation>> listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ConversationDao dao = new ConversationDao(getCurrentLoginUser());
                    dao.beginTrasaction();
                    List<Conversation> conversations = dao.queryAll();
                    dao.endTrasaction();
                    listener.success(conversations);
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.fail(e);
                }

            }
        }).start();
    }

    @Override
    public void getUpdateConversationList(final BaseCallbackListener<List<Conversation>> listener) {
       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   ConversationDao conversationDao = new ConversationDao(getCurrentLoginUser());
                   conversationDao.beginTrasaction();
                   List<Conversation> conversations = conversationDao.queryAll();
                   conversationDao.endTrasaction();
                   listener.success(conversations);
               } catch (Exception e) {
                   e.printStackTrace();
                   listener.fail(e);
               }

           }
       }).start();
    }

    @Override
    public synchronized void setIsRead(final Conversation conversation) {
        conversation.setIsNewMsg(Conversation.IsNewMsgEnum.NOT_NEWMSG.ordinal());
        if(conversation!=null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ConversationDao dao = new ConversationDao(new UserModelImpl().getCurrentUser().getUsername());
                        dao.beginTrasaction();
                        dao.update(conversation);
                        dao.endTrasaction();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 获取当前登录的用户
     * @return
     */
    private String getCurrentLoginUser() {
        String username = null;
        UserModelImpl userModel = new UserModelImpl();
        Users currentUser = userModel.getCurrentUser();
        if(currentUser!=null){
            username =  currentUser.getUsername();
        }
        return username;
    };
}
