package com.bzf.jianxin.commonutils;

import android.graphics.Bitmap;

import java.io.OutputStream;

/**
 * com.bzf.jianxin.commonutils
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class BitmapTool {

    /**
     * 保存头像
     * @param bitmap
     * @param quality 压缩率 0~100，如果是100，表示不压缩
     * @param out
     */
    public static void saveBitmap(Bitmap bitmap,int quality,OutputStream out){
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
