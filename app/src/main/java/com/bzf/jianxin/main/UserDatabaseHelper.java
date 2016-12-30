package com.bzf.jianxin.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * com.bzf.jianxin.main
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class UserDatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_CONVERSATION = "Conversation";
    public static final String SQL_CONVERSATION = "create table " + TABLE_CONVERSATION + "(id integer primary key autoincrement,contactUsername text,contactNickName text,messageType integer,bestNewMessage text,time text,avatarUrl text,newMessageCount integer,toTop integer,messageIsRemind integer,isNewMsg integer)";
    public static final int VERSION = 2;

    public UserDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CONVERSATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + SQL_CONVERSATION);
        onCreate(db);
    }
}
