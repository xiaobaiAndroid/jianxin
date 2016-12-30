package com.bzf.jianxin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bzf.jianxin.commonutils.LogTool;

/**
 * app数据库
 * com.bzf.jianxin
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class AppDatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = AppDatabaseHelper.class.getName();

    public static final String TABLE_USERS = "Users";

    public static final String SQL_USERS = "create table " + TABLE_USERS + " (id integer primary key  autoincrement,username text ,psw text,isFirstLogin integer,isCurrent integer)";


    public static final String APP_DATABASE_NAME = "jianxin";
    public static final int VERSION = 1;

    public AppDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_USERS);
        LogTool.i(TAG, "创建数据库成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果表存在就删除表，再重新创建
        db.execSQL("drop table if exists " + TABLE_USERS);
        onCreate(db);
        LogTool.i(TAG, "更新数据库成功");
    }
}
