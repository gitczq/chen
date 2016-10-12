package com.example.qiong_pc.myapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qiong_pc.myapplication.com.example.Info.ScanAppinfo;
import com.example.qiong_pc.myapplication.com.example.dao.AntiVirusDao;
import com.example.qiong_pc.myapplication.com.example.qiong_pcAdapter.ScanVirusAdapter;
import com.example.qiong_pc.myapplication.com.example.utils.MD5Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Qiong-PC on 2016/10/1.
 */
public class VirusScanSpeedActivity extends Activity implements View.OnClickListener{
    protected static final int SCAN_BENGIN=100;
    protected static final int SCANNING=101;
    protected static final int SCAN_FINISH=102;
    private int total;
    private int process;
    private TextView mProcessTV;
    private PackageManager pm;
    private boolean flag;
    private boolean isStop;
    private TextView mScanAppTV;
    private Button mCancleBtn;
    private ImageView mScanningIcon;
    private RotateAnimation rani;
    private ListView mScanListView;
    private ScanVirusAdapter adapter;
    private List<ScanAppinfo> mScanAppInfos=new ArrayList<ScanAppinfo>();
    private SharedPreferences mSP;

    private Handler mHandler=new Handler(){
        public void handleMessage(android.os.Message msg){
            switch (msg.what){
                case SCAN_BENGIN:
                    mScanAppTV.setText("初始化杀毒引擎中...");
                    break;
                case SCANNING:
                    ScanAppinfo info=(ScanAppinfo)msg.obj;
                    mScanAppTV.setText("正在扫描："+info.appName);
                    int speed=msg.arg1;
                    mProcessTV.setText((speed*100/total)+"%");
                    mScanAppInfos.add(info);
                    adapter.notifyDataSetChanged();
                    mScanListView.setSelection(mScanAppInfos.size());
                    break;
                case SCAN_FINISH:
                    mScanAppTV.setText("扫描完成！");
                    mScanningIcon.clearAnimation();
                    mCancleBtn.setBackgroundResource(R.drawable.activity_virusscanspeed_scan_complete);
                    saveScanTime();
                    break;
            }
        }
        private void saveScanTime(){
            SharedPreferences.Editor editor=mSP.edit();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String currentTime=sdf.format(new Date());
            currentTime="上次查杀："+currentTime;
            editor.putString("lastVirusScan",currentTime);
            editor.commit();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_virusscanspeed);
        pm=getPackageManager();
        mSP=getSharedPreferences("config",MODE_PRIVATE);
        initView();
        scanVirus();
    }

    /**
     *扫描病毒
     */
    private void scanVirus(){
        flag=true;
        isStop=false;
        process=0;
        mScanAppInfos.clear();
        new Thread(){
            public void run(){
                Message msg=Message.obtain();
                msg.what=SCAN_BENGIN;
                mHandler.sendMessage(msg);
                List<PackageInfo> installedPackages=pm.getInstalledPackages(0);
                total=installedPackages.size();
                for (PackageInfo info:installedPackages){
                    if (!flag){
                        isStop=true;
                        return;
                    }
                    String apkpath=info.applicationInfo.sourceDir;
                    //检查获取这个文件的特征码
                    String md5info= MD5Utils.getFileMd5(apkpath);
                    String result= AntiVirusDao.checkVirus(md5info);
                    msg=Message.obtain();
                    msg.what=SCANNING;
                    ScanAppinfo scanAppinfo=new ScanAppinfo();
                    if (result==null){
                        scanAppinfo.description="扫描安全";
                        scanAppinfo.isVirus=false;
                    }else {
                        scanAppinfo.description=result;
                        scanAppinfo.isVirus=true;
                    }
                    process++;
                    scanAppinfo.packagename=info.packageName;
                    scanAppinfo.appName=info.applicationInfo.loadLabel(pm).toString();
                    scanAppinfo.appicon=info.applicationInfo.loadIcon(pm);
                    msg.obj=scanAppinfo;
                    msg.arg1=process;
                    mHandler.sendMessage(msg);
                    try {
                        Thread.sleep(300);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                msg=Message.obtain();
                msg.what=SCAN_FINISH;
                mHandler.sendMessage(msg);
            }
        }.start();
    }

    private void initView(){
        findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.head));
        ((TextView)findViewById(R.id.tv_head)).setText("病毒查杀进度");
        ImageView mLeftImgv=(ImageView) findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back1);
        mProcessTV=(TextView) findViewById(R.id.tv_scanprocess);
        mScanAppTV=(TextView) findViewById(R.id.tv_scansapp);
        mCancleBtn=(Button) findViewById(R.id.btn_canclescan);
        mCancleBtn.setOnClickListener(this);
        mScanListView=(ListView) findViewById(R.id.lv_scansapp);
        adapter=new ScanVirusAdapter(mScanAppInfos,this);
        mScanListView.setAdapter(adapter);
        mScanningIcon=(ImageView) findViewById(R.id.imgv_scanningicon);
        startAnim();
    }
    private void startAnim(){
        if (rani==null){
            rani=new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        }
        rani.setRepeatCount(Animation.INFINITE);
        rani.setDuration(2000);
        mScanningIcon.startAnimation(rani);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.btn_canclescan:
                if (process==total&process>0){
                    //扫描已完成
                    finish();
                }else if (process>0&process<total&isStop==false){
                    mScanningIcon.clearAnimation();
                    //取消扫描
                    flag=false;
                    //更换背景图片
                    mCancleBtn.setBackgroundResource(R.drawable.activity_virusscanspeed_restart_scan_btn);
                }else if (isStop){
                    startAnim();
                    //重新扫描
                    scanVirus();
                    //更换背景图片
                    mCancleBtn.setBackgroundResource(R.drawable.activity_virusscanspeed_cancle_scan_btn_selector);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        flag=false;
        super.onDestroy();
    }
}
