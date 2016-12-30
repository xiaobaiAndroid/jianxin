package com.bzf.jianxin.commonutils;

import android.content.Context;

import com.bzf.jianxin.base.BaseCallbackListener;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Bomb工具类
 * com.bzf.jianxin.commonutils
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class BmobTool {

    private static final String TAG = BmobTool.class.getName();

    /**
     * Bmob SDK初始化必须用到此密钥
     */
    private static final String BMOBAPPLICATIONID = "a08de45f94bfcfb9d1697319657a9b69";

    public static void init(Context context){
        Bmob.initialize(context,BMOBAPPLICATIONID);
    }

    /**
     * 上传文件
     * @param picPath 文件路径
     * @param listener
     * @throws Exception
     */
    public static void uploadFile(String picPath, final BaseCallbackListener<String> listener)throws Exception{
        LogTool.i(TAG,"uploadFile----picPath="+picPath);
        File file = new File(picPath);
        if(file.exists()){
            final BmobFile bmobFile = new BmobFile(file);
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        String fileUrl = bmobFile.getFileUrl();
                        listener.success(fileUrl);
                    }else{
                        listener.fail(e);
                    }
                }
            });
        }else{
            throw new NullPointerException("上传的文件不存在");
        }
    }

}
