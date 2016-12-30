package com.bzf.jianxin.commonutils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.Iterator;
import java.util.List;

/**
 * 判断App是否在前台运行的工具
 * com.bzf.jianxin.commonutils
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class AppTool {

    /**
     * 判断App是否运行在前台
     * @param context
     * @return
     */
    public static boolean isRunningForeground(Context context){
        String packageName=getPackageName(context);
        String topActivityClassName=getTopActivityName(context);
        System.out.println("packageName="+packageName+",topActivityClassName="+topActivityClassName);
        if (packageName!=null&&topActivityClassName!=null&&topActivityClassName.startsWith(packageName)) {
            System.out.println("---> isRunningForeGround");
            return true;
        } else {
            System.out.println("---> isRunningBackGround");
            return false;
        }
    }

    private  static String getTopActivityName(Context context){
        String topActivityClassName=null;
        ActivityManager activityManager =
                (ActivityManager)(context.getSystemService(android.content.Context.ACTIVITY_SERVICE )) ;
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1) ;
        if(runningTaskInfos != null){
            ComponentName f=runningTaskInfos.get(0).topActivity;
            topActivityClassName=f.getClassName();
        }
        return topActivityClassName;
    }

    public static String getPackageName(Context context){
        String packageName = context.getPackageName();
        return packageName;
    }

    public static String getAppProcessName(Context context) {
        int pid = android.os.Process.myPid();
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    /**
     * 判断Application是否只初始化了一次,是：true 否:false
     * @return
     */
    public static boolean applicationIsOnlyInit(Context context){
        String processAppName = getAppProcessName(context);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(context.getPackageName())) {
            //application:oncreate已经被调用过
            return false;
        }
        return true;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
