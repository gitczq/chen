package com.example.qiong_pc.myapplication.com.example.qiong_pcDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qiong_pc.myapplication.R;

/**
 * Created by Qiong-PC on 2016/9/12.
 */
public class SetUpPasswordDialog extends Dialog implements android.view.View.OnClickListener{

    private TextView mTextView;
    public EditText mFirstET;
    public EditText mSureET;
    //回调接口
    private MyCallBack myCallBack;

    public SetUpPasswordDialog(Context context) {
        super(context,R.style.dialog_custom);
    }

    public void setMyCallBack(MyCallBack myCallBack){
        this.myCallBack=myCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_password_diolog);
        initView();
    }

    private void initView() {
        mTextView=(TextView)findViewById(R.id.tv_setuppwd_title);
        mFirstET=(EditText)findViewById(R.id.et_firstpassword);
        mSureET=(EditText)findViewById(R.id.et_sure_password);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title){
        if (!TextUtils.isEmpty(title)){
            mTextView.setText(title);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                myCallBack.ok();
                break;
            case R.id.btn_cancel:
                myCallBack.cancel();
                break;
        }
    }



    public interface MyCallBack{
        void ok();
        void  cancel();
    }
}
