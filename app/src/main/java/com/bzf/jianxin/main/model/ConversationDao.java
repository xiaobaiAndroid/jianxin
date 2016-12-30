package com.bzf.jianxin.main.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.bzf.jianxin.base.BaseDao;
import com.bzf.jianxin.bean.Conversation;
import com.bzf.jianxin.commonutils.LogTool;
import com.bzf.jianxin.main.UserDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * com.bzf.jianxin.main.model
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class ConversationDao extends BaseDao{

    private static final String TAG = ConversationDao.class.getName();

    public ConversationDao(String databaseName) throws Exception {
        super(databaseName);
    }

    /**
     * 插入
     * @param conversation
     */
    public void insert(Conversation conversation){
        if(db.isOpen()){

            ContentValues values = new ContentValues();
            values.put("contactUsername",conversation.getContactUsername());
            values.put("contactNickName",conversation.getContactNickName());
            values.put("messageType",conversation.getMessageType());
            values.put("bestNewMessage",conversation.getBestNewMessage());
            values.put("time",conversation.getTime());
            values.put("avatarUrl",conversation.getAvatarUrl());
            values.put("newMessageCount",conversation.getNewMessageCount());
            values.put("toTop",conversation.getToTop());
            values.put("messageIsRemind",conversation.getMessageIsRemind());
            values.put("isNewMsg",conversation.getIsNewMsg());
            db.insert(UserDatabaseHelper.TABLE_CONVERSATION,null,values);
        }
    }

    /**
     * 更新
     * @param conversation
     */
    public void update(Conversation conversation){
        if(db.isOpen()){
            ContentValues values = new ContentValues();
            values.put("contactUsername",conversation.getContactUsername());
            values.put("contactNickName",conversation.getContactNickName());
            values.put("messageType",conversation.getMessageType());
            values.put("bestNewMessage",conversation.getBestNewMessage());
            values.put("time",conversation.getTime());
            values.put("avatarUrl",conversation.getAvatarUrl());
            values.put("newMessageCount",conversation.getNewMessageCount());
            values.put("toTop",conversation.getToTop());
            values.put("messageIsRemind",conversation.getMessageIsRemind());
            values.put("isNewMsg",conversation.getIsNewMsg());
            db.update(UserDatabaseHelper.TABLE_CONVERSATION,values,null,null);
        }
    }

    /**
     * 删除
     * @param contactUsername
     */
    public void delete(String contactUsername){

    }

    /**
     * @return
     */
    public List<Conversation> queryAll(){
        List<Conversation> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            if(db.isOpen()){
                cursor = db.query(UserDatabaseHelper.TABLE_CONVERSATION, null, null, null, null, null, null);
                if(cursor.moveToNext()){
                    Conversation conversation = new Conversation();
                    String contactUsername = cursor.getString(cursor.getColumnIndex("contactUsername"));
                    String contactNickName = cursor.getString(cursor.getColumnIndex("contactNickName"));
                    int messageType = cursor.getInt(cursor.getColumnIndex("messageType"));
                    String bestNewMessage = cursor.getString(cursor.getColumnIndex("bestNewMessage"));
                    String time = cursor.getString(cursor.getColumnIndex("time"));
                    String avatarUrl = cursor.getString(cursor.getColumnIndex("avatarUrl"));
                    int newMessageCount = cursor.getInt(cursor.getColumnIndex("newMessageCount"));
                    int toTop = cursor.getInt(cursor.getColumnIndex("toTop"));
                    int messageIsRemind = cursor.getInt(cursor.getColumnIndex("messageIsRemind"));
                    int isNewMsg = cursor.getInt(cursor.getColumnIndex("isNewMsg"));
                    conversation.setContactUsername(contactUsername);
                    conversation.setContactNickName(contactNickName);
                    conversation.setMessageType(messageType);
                    conversation.setBestNewMessage(bestNewMessage);
                    conversation.setTime(time);
                    conversation.setAvatarUrl(avatarUrl);
                    conversation.setNewMessageCount(newMessageCount);
                    conversation.setToTop(toTop);
                    conversation.setMessageIsRemind(messageIsRemind);
                    conversation.setIsNewMsg(isNewMsg);
                    list.add(conversation);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        LogTool.i(TAG,list.toString());
        return list;
    }

    /**
     * 插入多条数据
     * @param list
     */
    public void insertList(List<Conversation> list) {
        if(list==null || list.size()<=0){
            return;
        }
        for(Conversation conversation : list) {
            try {
                if (conversation == null) {
                    continue;
                }
                //查询会话存不存在，存在就更新，不存在就插入
                if (isExit(conversation.getContactUsername())) {
                    update(conversation);
                } else {
                    insert(conversation);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据 contactUsername判断 数据存不存在
     * @param contactUsername
     * @return
     */
    private boolean isExit(String contactUsername) {
        Cursor cursor = null;
        try {
            if(db.isOpen()){
                String selection = "contactUsername=?";
                String[] selectionArgs = {contactUsername};
                 cursor = db.query(UserDatabaseHelper.TABLE_CONVERSATION, null, selection, selectionArgs, null, null, null);
                if(cursor.moveToNext()){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return false;
    }

    /**
     * 根据Username查询
     * @param usernames
     */
    public List<Conversation> queryByUsernames(ArrayList<String> usernames) throws Exception{
        List<Conversation> conversations = new ArrayList<>();
        Cursor cursor = null;
        try {
            if(db.isOpen()){
                String selection = "contactUsername";
                String[] selectionArgs = (String[]) usernames.toArray();
                cursor = db.query(UserDatabaseHelper.TABLE_CONVERSATION, null, selection, selectionArgs, null, null, null);
                while(cursor.moveToNext()){
                    Conversation conversation = new Conversation();
                    conversation.setContactUsername(cursor.getString(cursor.getColumnIndex("contactUsername")));
                    conversation.setContactNickName(cursor.getString(cursor.getColumnIndex("contactNickName")));
                    conversation.setBestNewMessage(cursor.getString(cursor.getColumnIndex("bestNewMessage")));
                    conversation.setTime(cursor.getString(cursor.getColumnIndex("time")));
                    conversation.setAvatarUrl(cursor.getString(cursor.getColumnIndex("avatarUrl")));
                    conversation.setNewMessageCount(cursor.getInt(cursor.getColumnIndex("newMessageCount")));
                    conversation.setMessageIsRemind(cursor.getInt(cursor.getColumnIndex("messageIsRemind")));
                    conversation.setMessageType(cursor.getInt(cursor.getColumnIndex("messageType")));
                    conversation.setToTop(cursor.getInt(cursor.getColumnIndex("toTop")));
                    conversation.setIsNewMsg(cursor.getInt(cursor.getColumnIndex("isNewMsg")));
                    conversations.add(conversation);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        LogTool.i(TAG,"queryByUsernames---conversations="+conversations.toArray());
        return conversations;
    }
}
