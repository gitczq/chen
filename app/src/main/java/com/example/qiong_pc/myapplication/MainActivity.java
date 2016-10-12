package com.example.qiong_pc.myapplication;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.qiong_pc.myapplication.com.example.utils.MD5Utils;
import com.example.qiong_pc.myapplication.com.example.qiong_pcAdapter.HomeAdapter;
import com.example.qiong_pc.myapplication.com.example.qiong_pcDialog.InterPasswordDialog;
import com.example.qiong_pc.myapplication.com.example.qiong_pcDialog.SetUpPasswordDialog;

public class MainActivity extends AppCompatActivity {
    private GridView gv_home;
    private long mExitTime;
    private SharedPreferences msharedPreferences;
    private DevicePolicyManager policyManager;
    private ComponentName componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        msharedPreferences=getSharedPreferences("config",MODE_PRIVATE);
        //初始化GridView
        gv_home = (GridView) findViewById(R.id.act_home_gv_home);
        gv_home.setAdapter(new HomeAdapter(MainActivity.this));
        //设置条目的点击事件
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if ( isSetUpPassword() ) {
                            //弹出输入密码对话框
                            showInterPswdDialog();
                        } else {
                            //弹出设置密码对话框
                            showSetUpPswdDialog();
                        }
                        break;
                    case 1:
                        break;
                    case 2:
                        startActivity(AppManagerActivity.class);
                        break;
                    case 3:
                        startActivity(VirusScanActivity.class);
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        startActivity(SettingsActivity.class);
                        break;


                }
            }

        });
        //获取设备管理员
        policyManager=(DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);

    }



    /**
     * 弹出设置密码对话框
     */
    private void showSetUpPswdDialog() {
        final SetUpPasswordDialog setUpPasswordDialog = new SetUpPasswordDialog(MainActivity.this);
        setUpPasswordDialog.setMyCallBack(new SetUpPasswordDialog.MyCallBack() {
            @Override
            public void ok() {
                String firstPwsd = setUpPasswordDialog.mFirstET.getText().toString().trim();
                String affirmPwsd = setUpPasswordDialog.mSureET.getText().toString().trim();
                if ( !TextUtils.isEmpty(firstPwsd) && !TextUtils.isEmpty(affirmPwsd) ) {
                    if ( firstPwsd.equals(affirmPwsd) ) {
                        savePwsd(affirmPwsd);
                        setUpPasswordDialog.dismiss();
                        //显示输入密码对话框
                        showInterPswdDialog();
                    } else {
                        Toast.makeText(MainActivity.this, "两次密码输入不一样", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void cancel() {
                setUpPasswordDialog.dismiss();
            }
        });
        setUpPasswordDialog.setCancelable(true);
        setUpPasswordDialog.show();
    }

    /**
     * 弹出输入密码对话框
     */
    private void showInterPswdDialog() {
        final String password = getPassword();
        final InterPasswordDialog mInPswdDialog = new InterPasswordDialog(MainActivity.this);
        mInPswdDialog.setMyCallBack(new InterPasswordDialog.MyCallBack() {
            @Override
            public void comfirm() {
                if ( TextUtils.isEmpty(getPassword()) ) {
                    Toast.makeText(MainActivity.this, "密码不能为空",Toast.LENGTH_SHORT).show();
                } else if (password.equals(MD5Utils.encode(getPassword())) ) {
                    //进入防盗主界面
                    mInPswdDialog.dismiss();


                }else {
                    //对话框消失,弹出提示
                    mInPswdDialog.dismiss();
                    Toast.makeText(MainActivity.this,"密码有误,请重新输入",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void cancel() {
                mInPswdDialog.dismiss();
            }
        });
        mInPswdDialog.setCancelable(true);
        mInPswdDialog.show();
    }

    /**
     * 保存密码
     *
     * @param affirmPwsd
     */
    private void savePwsd(String affirmPwsd) {
        SharedPreferences.Editor edio = msharedPreferences.edit();
        //为了防止用户隐私被泄露,因此需要加密密码
        edio.putString("PhoneAntiTheftPWD", MD5Utils.encode(affirmPwsd));
        edio.commit();
    }

    /**
     * 获取密码
     *
     * @return sp 存储的密码
     */
    private String getPassword() {
        String password = msharedPreferences.getString("PhoneAntiTheftPWD", null);
        if ( TextUtils.isEmpty(password) ) {
            return "";
        }
        return password;
    }

    private boolean isSetUpPassword() {
        String password = msharedPreferences.getString("PhoneAntiTheftPWD", null);
        if ( TextUtils.isEmpty(password) ) {
            return false;
        }
        return true;
    }

    /**
     * 开启新的activity不关闭自己
     * @param
     */
    public void startActivity(Class<?> cls){
        Intent mIntent=new Intent(MainActivity.this,cls);
        startActivity(mIntent);

    }
    /**
     * 按两次返回键退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if ((System.currentTimeMillis()-mExitTime)>2000){
                Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                mExitTime=System.currentTimeMillis();
            }else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}






