package com.bzf.jianxin.base;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.bzf.jianxin.AppDatabaseHelper;
import com.bzf.jianxin.MyApplication;
import com.bzf.jianxin.main.UserDatabaseHelper;

/**
 * com.bzf.jianxin.base
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class BaseDao {

    protected SQLiteDatabase db;

    public BaseDao(String databaseName) throws Exception {
        if(TextUtils.isEmpty(databaseName)){
            throw new Exception("数据库名为null");
        }
        initDatabase(databaseName+".db");
    }

    protected void initDatabase(String databaseName) {
        try {
            UserDatabaseHelper helper = new UserDatabaseHelper(MyApplication.getIns(), databaseName, null,AppDatabaseHelper.VERSION);
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 开始事务，表操作执行前调用
     */
    public void beginTrasaction() {
        if (db != null) {
            db.beginTransaction();
        }
    }

    /**
     * 结束事务，表操作执行完后必须调用
     */
    public void endTrasaction() {
        try {
            if (db != null) {
                db.setTransactionSuccessful();
                db.endTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(db!=null){
                db.close();
            }
        }
    }
}
