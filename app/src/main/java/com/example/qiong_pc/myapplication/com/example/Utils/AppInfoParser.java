package com.example.qiong_pc.myapplication.com.example.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.example.qiong_pc.myapplication.com.example.Info.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qiong-PC on 2016/9/14.
 */
public class AppInfoParser {
    /**
     * 获取手机里面的所有应用程序
     * @param mContext 上下文
     * @return
     */
    public static List<AppInfo> getAppInfos(Context mContext){
        PackageManager pm=mContext.getPackageManager();
        List<PackageInfo> packInfos=pm.getInstalledPackages(0);
        List<AppInfo> appinfos=new ArrayList<AppInfo>();
        for (PackageInfo packageInfo:packInfos){
            AppInfo appinfo=new AppInfo();
            String packname=packageInfo.packageName;
            appinfo.packageName=packname;
            Drawable icon=packageInfo.applicationInfo.loadIcon(pm);
            appinfo.icon=icon;
            String appname=packageInfo.applicationInfo.loadLabel(pm).toString();
            appinfo.appName=appname;
            //应用程序apk包的路径
            String apkPath=packageInfo.applicationInfo.sourceDir;
            appinfo.apkPath=apkPath;
            File mFile=new File(apkPath);
            long appSize=mFile.length();
            appinfo.appSize=appSize;
            //应用程序安装的位置
            int flags=packageInfo.applicationInfo.flags;//二进制
            if ((ApplicationInfo.FLAG_EXTERNAL_STORAGE&flags)!=0){
                //外部存储
                appinfo.isInRoom=false;
            }else {
                //手机内存
                appinfo.isInRoom=true;
            }
            if ((ApplicationInfo.FLAG_SYSTEM&flags)!=0){
                //系统应用
                appinfo.isUserApp=false;
            }else {
                appinfo.isUserApp=true;
            }
            appinfos.add(appinfo);
            appinfo=null;
        }
        return appinfos;
    }
}
