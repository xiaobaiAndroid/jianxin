package com.bzf.jianxin.commonutils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * com.bzf.jianxin.commonutils
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class FileTool {

    private static final String TAG = FileTool.class.getName();

    /**
     * 创建目录
     *
     * @param pathName
     * @return
     */
    public static File createDir(String pathName) throws Exception {
        String path = Environment.getExternalStorageDirectory() + "/" + pathName;
        LogTool.i(TAG, "createDir--path=" + path);
        File dir = new File(path);
        if (dir != null) {
            if (!dir.exists()) {
                dir.mkdirs();
            }
        } else {
            throw new Exception("创建目录失败");
        }
        return dir;
    }

    /**
     * 创建文件
     *
     * @param path     文件的路径
     * @param fileName 文件名
     * @return
     * @throws Exception
     */
    public static File createFile(String path, String fileName) throws Exception {
        LogTool.i(TAG, "createFile--path=" + path + ",fileName=" + fileName);
        File file = new File(path, fileName);
        if (file == null) {
            throw new NullPointerException("file为null");
        } else {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            return file;
        }
    }

    public static void loadSDcardAllDir(File dir) {
//        File directory = Environment.getExternalStorageDirectory();
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                //获取db类型的文件
                return name.endsWith(".db");
            }
        });
        for (File file : files) {
            if (file.isDirectory()) {
                loadSDcardAllDir(file);
            } else {
                LogTool.i("bzf", "fileName=" + file.getAbsolutePath());
            }
        }
    }

    public static void bufferDemo(String context) throws UnsupportedEncodingException {
        char[] inChars = context.toCharArray();
        int capacity = 256;
        //创建一个容量为capacity的CharBuffer对象
        CharBuffer byteBuffer = CharBuffer.allocate(capacity);
        //放入数据
        byteBuffer.put(inChars);
        char[] outChars = new char[inChars.length];
        //将limit设置为position所在的位置，并将position设置为0，作用就是为输出做准备
        byteBuffer.flip();
        //取出数据,相对访问，position按处理的元素个数增加
        byteBuffer.get(outChars, 0, outChars.length);
        System.out.println(Arrays.toString(outChars));
    }

    /**
     * 文件复制，采用映射方式（在Android中，
     * 为app分配的运存比较小，要考虑文件过大的问题。考虑文件过大带来的性能下降，个人觉得文件不要超过5M）
     * @param sourceFile
     * @param copyFile
     */
    public static void mapCopyFile(File sourceFile, File copyFile) throws Exception {
        FileChannel inChannel = new FileInputStream(sourceFile).getChannel();
        FileChannel outChannel = new FileOutputStream(copyFile).getChannel();

        MappedByteBuffer buffer = null;
        long start = System.currentTimeMillis();
        try {
            buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, sourceFile.length());
            outChannel.write(buffer);
            buffer.clear();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outChannel != null) {
                outChannel.close();
            }
            if (inChannel != null) {
                inChannel.close();
            }
        }
        long end = System.currentTimeMillis();
        LogTool.i("bzf","mapCopyFile---耗时：" + (end - start) + "毫秒");
    }

    /**
     *  复制文件，采用传统方式（多次重复取水）
     */
    public static void traditionCopyFile(File sourceFile, File copyFile) throws Exception {

        FileChannel inChannel = new FileInputStream(sourceFile).getChannel();
        FileChannel outChannel = new FileOutputStream(copyFile).getChannel();
        int capacity = 256;
        long start = System.currentTimeMillis();
        try {
            ByteBuffer buffer = ByteBuffer.allocate(capacity);
            while (inChannel.read(buffer) != -1) {
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outChannel != null) {
                outChannel.close();
            }
            if (inChannel != null) {
                inChannel.close();
            }
        }
        long end = System.currentTimeMillis();
        LogTool.i("bzf","traditionCopyFile---耗时："+(end-start)+"毫秒");
    }

    public static void encode(String content,String charsetName){
        System.out.println("编码前："+content);
        Charset charset = Charset.forName(charsetName);
        ByteBuffer byteBuffer = charset.encode(content);
        byte[] bytes = new byte[content.getBytes().length];
        byteBuffer.flip();
        byteBuffer.get(bytes);
        byteBuffer.clear();
        String encodeContent = new String(bytes);
        System.out.println("编码后："+encodeContent);
    }

    public static void decode(byte[] bytes,String charsetName){
        Charset charset = Charset.forName(charsetName);
        int capacity = bytes.length;
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        charset.decode(byteBuffer);
        System.out.println("解码："+byteBuffer);
        byteBuffer.clear();
    }


}
