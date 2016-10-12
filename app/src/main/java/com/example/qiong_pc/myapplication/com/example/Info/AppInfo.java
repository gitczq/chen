package com.example.qiong_pc.myapplication.com.example.Info;

import android.graphics.drawable.Drawable;

/**
 * Created by Qiong-PC on 2016/9/14.
 */
public class AppInfo {
    public String packageName;
    public Drawable icon;
    public String appName;
    public String apkPath;
    public long appSize;
    public boolean isInRoom;
    public boolean isUserApp;
    public boolean isSelectes=false;
    public String getAppLocation(boolean isInRoom){
        if (isInRoom ){
            return "手机内存";
        }else {
            return "外部储存";
        }
    }
}
