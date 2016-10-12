package com.example.qiong_pc.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qiong_pc.myapplication.com.example.utils.SystemInfoUtils;
import com.example.qiong_pc.myapplication.com.example.widget.SettingView;

/**
 * Created by Qiong-PC on 2016/10/2.
 */
public class SettingsActivity extends Activity implements View.OnClickListener,SettingView.OnCheckedStatusIsChanged {

    private SettingView mBlackNumSV;
    private SettingView mAppLockSV;
    private SharedPreferences mSP;
    private boolean running;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);
        mSP=getSharedPreferences("config",MODE_PRIVATE);
        initView();
    }
    private void initView(){
        findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.head));
        ((TextView)findViewById(R.id.tv_head)).setText("设置中心");
        ImageView mLeftImgv=(ImageView) findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back1);
        mBlackNumSV=(SettingView) findViewById(R.id.sv_blacknumber_set);
        mAppLockSV=(SettingView) findViewById(R.id.sv_applock_set);
        mBlackNumSV.setOnCheckedStatusIsChanged(this);
        mAppLockSV.setOnCheckedStatusIsChanged(this);
    }

    @Override
    protected void onStart() {
        running= SystemInfoUtils.isServiceRunning(this,"");
        mAppLockSV.setChecked(running);
        mBlackNumSV.setChecked(mSP.getBoolean("BlackNumStatus",true));
        super.onStart();
    }

    @Override
    public void onCheckedChanged(View view, boolean isChecked) {
        switch (view.getId()){
            case R.id.sv_blacknumber_set:
                saveStatus("BlackNumStatus",isChecked);
                break;
//            case R.id.sv_applock_set:
//                //开启或者关闭程序锁
//                if (isChecked){
//
//                }
//                break;
        }
    }
    private void saveStatus(String keyname,boolean isChecked){
        if (!TextUtils.isEmpty(keyname)){
            SharedPreferences.Editor editor=mSP.edit();
            editor.putBoolean(keyname,isChecked);
            editor.commit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgv_leftbtn:
                finish();
                break;
        }
    }

}
