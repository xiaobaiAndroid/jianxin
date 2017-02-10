package com.bzf.jianxin;

import com.bzf.jianxin.commonutils.FileTool;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        try {
            String content = "张三";
            String charsetName = "ISO-8859-1";
            FileTool.encode(content,charsetName);
            FileTool.decode(content.getBytes(),charsetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}