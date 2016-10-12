package com.example.qiong_pc.myapplication.com.example.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Qiong-PC on 2016/9/12.
 */
public class MD5Utils {
    /**
     * md5 加密算法
     * @param text
     * @return
     */

    public static String encode(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] results = digest.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : results) {
                int number = b & 0xff;
                String hex = Integer.toHexString(number);
                if ( hex.length() == 1 ) {
                    sb.append("0" + hex);
                } else {
                    sb.append(hex);
                }
            }
                return sb.toString();
            }catch(NoSuchAlgorithmException e){
                e.printStackTrace();
                return "";
            }
        }
    public static String getFileMd5(String path){
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            File file=new File(path);
            FileInputStream fits=new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len=-1;
            while ((len=fits.read(buffer))!=-1){
                digest.update(buffer,0,len);
            }
            byte[] results = digest.digest();
            StringBuilder sb=new StringBuilder();
            for (byte b:results){
                int number = b & 0xff;
                String hex = Integer.toHexString(number);
                if ( hex.length() == 1 ) {
                    sb.append("0" + hex);
                } else {
                    sb.append(hex);
                }
            }
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    }