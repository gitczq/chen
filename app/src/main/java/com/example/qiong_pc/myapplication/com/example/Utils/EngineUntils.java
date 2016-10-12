package com.example.qiong_pc.myapplication.com.example.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.example.qiong_pc.myapplication.com.example.Info.AppInfo;
import com.stericson.RootTools.RootTools;

/**
 * Created by Qiong-PC on 2016/9/14.
 */
public class EngineUntils {

    /**
     * 分享应用
     */
    public static void shareApplication(Context mContext, AppInfo appInfo) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "推荐您使用一款软件，名称叫:" + appInfo.appName +
                "下载路径:https://play.google.com/store/apps/details?id=" + appInfo.packageName);
        mContext.startActivity(intent);
    }

    /**
     * 开启应用
     */
    public static void startApplication(Context mContext, AppInfo appInfo) {
        PackageManager pm = mContext.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(appInfo.packageName);
        intent.addCategory("android.intent.category.DEFAULT");
        if ( intent != null ) {
            mContext.startActivity(intent);
        } else {
            Toast.makeText(mContext, "该应用没有启动界面", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 开启应用设置页面
     * @param mContext
     * @param appInfo
     */
    public static void SettingApplication(Context mContext, AppInfo appInfo) {
        Intent intent=new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:"+appInfo.packageName));
        mContext.startActivity(intent);
    }
    /**
     * 卸载应用
     */
    public static void uninstallApplication(Context mContext, AppInfo appInfo){
        if (appInfo.isUserApp){
            Intent mIntent=new Intent();
            mIntent.setAction(Intent.ACTION_DELETE);
            mIntent.setData(Uri.parse("package:"+appInfo.packageName));
            mContext.startActivity(mIntent);
        }
        else {
            if (!RootTools.isRootAvailable()){
                Toast.makeText(mContext,"卸载系统应用,必须要root权限",Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                if ( !RootTools.isAccessGiven() ) {
                    Toast.makeText(mContext, "请授权手机卫士root权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                RootTools.sendShell("mount -o remount,rw/systrm",3000);
                RootTools.sendShell("rm -r "+appInfo.apkPath,30000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }




}
