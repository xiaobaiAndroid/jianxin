package com.bzf.jianxin.commonutils;

import android.os.Environment;

import java.io.File;

/**
 * com.bzf.jianxin.commonutils
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class FileTool {

    private static final String TAG = FileTool.class.getName();

    /**
     * 创建目录 
     * @param pathName
     * @return
     */
    public static File createDir(String pathName) throws Exception {
        String path = Environment.getExternalStorageDirectory()+"/"+pathName;
        LogTool.i(TAG,"createDir--path="+path);
        File dir = new File(path);
        if(dir!=null){
            if(!dir.exists()){
                dir.mkdirs();
            }
        }else{
            throw new Exception("创建目录失败");
        }
        return dir;
    }

    /**
     * 创建文件
     * @param path 文件的路径
     * @param fileName 文件名
     * @return
     * @throws Exception
     */
    public static File createFile(String path,String fileName) throws Exception {
        LogTool.i(TAG,"createFile--path="+path+",fileName="+fileName);
        File file = new File(path,fileName);
        if(file==null){
            throw new NullPointerException("file为null");
        }else{
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();
            return file;
        }
    }

}
