package com.example.qiong_pc.myapplication.com.example.utils;

import android.content.Context;

/**
 * Created by Qiong-PC on 2016/9/14.
 */
public class DensityUtil {
    /**
     * dip转像素px
     */
    public static int dip2px(Context mContext,float dpValue){
          try{
              final float scale=mContext.getResources().getDisplayMetrics().density;
              return (int)(dpValue*scale+0.5f);
          }catch (Exception e){
              e.printStackTrace();
          }
        return (int) dpValue;
    }
    /**
     * 像素px转dip
     */
    public static int px2dip(Context mContext,float pxValue) {
        try {
            final float scale = mContext.getResources().getDisplayMetrics().density;
            return (int) (pxValue * scale + 0.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) pxValue;
    }
}
