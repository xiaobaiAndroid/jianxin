package com.bzf.jianxin;

import android.os.Environment;
import android.test.AndroidTestCase;

import com.bzf.jianxin.commonutils.FileTool;

import java.io.File;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends AndroidTestCase{

    public void testCopyFile(){
        File file = Environment.getExternalStorageDirectory();
        File sourceFile = new File(file,"jiaxin_avatar.jpg");
        File copyFile = new File(file,"jianxin_avatar_copy.jpg");
        try {
            FileTool.mapCopyFile(sourceFile,copyFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}