package com.bzf.jianxin.commonutils;

import android.text.TextUtils;
import android.widget.Toast;

import com.bzf.jianxin.MyApplication;

/**
 * com.bzf.jianxin.commonutils
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class ToastTool {

    private static  final boolean TOGGLE = true;

    public static void shotMessage(String content){
        if(!TextUtils.isEmpty(content) && TOGGLE){
            Toast.makeText(MyApplication.getIns(),content,Toast.LENGTH_SHORT).show();
        }
    }

    public static void longMessage(String content){
        if(!TextUtils.isEmpty(content) && TOGGLE){
            Toast.makeText(MyApplication.getIns(),content,Toast.LENGTH_LONG).show();
        }
    }
}
