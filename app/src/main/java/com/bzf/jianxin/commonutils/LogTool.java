package com.bzf.jianxin.commonutils;

import android.util.Log;

/**
 * Created by baizhengfu
 * Email：709889312@qq.com
 */
public class LogTool {

    private static final boolean IS_SHOW = true;

    public static void i(String tag,String msg){
        Log.i(tag,msg);
    }
    public static void e(String tag,String msg){
        Log.e(tag,msg);
    }


}
