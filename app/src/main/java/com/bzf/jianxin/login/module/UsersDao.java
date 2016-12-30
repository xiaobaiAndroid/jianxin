package com.bzf.jianxin.login.module;

import android.content.ContentValues;
import android.database.Cursor;

import com.bzf.jianxin.AppDatabaseHelper;
import com.bzf.jianxin.MyApplication;
import com.bzf.jianxin.base.BaseDao;
import com.bzf.jianxin.bean.Users;

/**
 * com.bzf.jianxin.login.module
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class UsersDao extends BaseDao{

    public UsersDao(String databaseName) throws Exception {
        super(databaseName);
    }

    @Override
    protected void initDatabase(String databaseName) {
        try {
            AppDatabaseHelper helper = new AppDatabaseHelper(MyApplication.getIns(), databaseName, null,AppDatabaseHelper.VERSION);
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询当前登录用户
     *
     * @param isCurrentUser 是否是当前登录用户 0表示否，1表示是
     * @return
     */
    public Users queryUser(Integer isCurrentUser) {
        Users users = null;
        Cursor cursor = null;
        try {
            if (db.isOpen()) {
                String[] selectionArgs = {String.valueOf(isCurrentUser)};
                String selection = "isCurrent=?";
                cursor =  db.query(AppDatabaseHelper.TABLE_USERS, null, selection, selectionArgs, null, null, null);
                if (cursor.moveToFirst()) {
                    users = new Users();
                    String username = cursor.getString(cursor.getColumnIndex("username"));
                    String psw = cursor.getString(cursor.getColumnIndex("psw"));
                    int isFirstLogin = cursor.getInt(cursor.getColumnIndex("isFirstLogin"));
                    int isCurrent = cursor.getInt(cursor.getColumnIndex("isCurrent"));
                    users.setUsername(username);
                    users.setPsw(psw);
                    users.setFirstLogin(isFirstLogin);
                    users.setCurrent(isCurrent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return users;
    }

    ;

    /**
     * 保存当前用户
     *
     * @param users
     */
    public void saveUsers(Users users) {
        try {
            if (db.isOpen()) {
                ContentValues values = new ContentValues();
                values.put("username", users.getUsername());
                values.put("psw", users.getPsw());
                values.put("isFirstLogin", users.getFirstLogin());
                values.put("isCurrent", users.getCurrent());
                db.insert(AppDatabaseHelper.TABLE_USERS, null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUsers(Users users) {
        try {
            if (db.isOpen()) {
                ContentValues values = new ContentValues();
                values.put("username", users.getUsername());
                values.put("psw", users.getPsw());
                values.put("isFirstLogin", users.getFirstLogin());
                values.put("isCurrent", users.getCurrent());
                db.update(AppDatabaseHelper.TABLE_USERS, values, "username=?", new String[]{users.getUsername()});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断用户是否存在
     *
     * @param userName
     */
    public boolean isExist(String userName) {
        boolean isExist = false;
        Cursor cursor = null;
        try {
            if (db.isOpen()) {
                String selection = "username=?";
                String[] selectionArgs = {userName};
                cursor = db.query(AppDatabaseHelper.TABLE_USERS, null, selection, selectionArgs, null, null, null);
                if (cursor.moveToFirst()) {
                    isExist = true;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return isExist;
    }

    public enum FirstLoginEnum {

        /**
         * 不是第一次登录
         */
        NOFIRST,

        /**
         * 是第一次登录
         */
        ISFIRST;
    }

    public enum CurrentUserEnum {
        /**
         * 不是当前登录用户
         */
        NOCURRENT,
        /**
         * 是当前登录用户
         */
        ISCURRENT;
    }
}
