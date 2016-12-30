package com.bzf.jianxin.commonutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * com.bzf.jianxin.commonutils
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class DateTool {

    public static final String PATTERN_1 ="yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_2 ="MM-dd";
    public static final String PATTERN_3 ="HH:mm";
    public static final String PATTERN_4 ="yyyy-MM-dd HH:mm";
    /**
     * 转换成String类型的日期
     * @param date
     * @param pattern 格式
     * @return
     * @throws ParseException
     */
    public static String fromateDate(Date date, String pattern)throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 把字符串类型的时间，转成Date类型
     * @param strDate
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parse(String strDate,String pattern) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(strDate);
    }
}
