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
public class InterPasswordDialog extends Dialog implements android.view.View.OnClickListener {

    private TextView mTextView;
    public EditText mFirstET;
    public EditText mSureET;
    //回调接口
    private MyCallBack myCallBack;

    public InterPasswordDialog(Context context) {
        super(context, R.style.dialog_custom);
    }

    public void setMyCallBack(MyCallBack myCallBack) {
        this.myCallBack = myCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inter_password_dialog);
        initView();
    }

    private void initView() {
        mTextView = (TextView) findViewById(R.id.tv_interpwd_title);
        mFirstET = (EditText) findViewById(R.id.et_firstpassword);
        mSureET = (EditText) findViewById(R.id.et_inter_password);
        findViewById(R.id.btn_comfirm).setOnClickListener(this);
        findViewById(R.id.btn_dismiss).setOnClickListener(this);
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        if ( !TextUtils.isEmpty(title) ) {
            mTextView.setText(title);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_comfirm:
                myCallBack.comfirm();
                break;
            case R.id.btn_dismiss:
                myCallBack.cancel();
                break;
        }
    }




    public interface MyCallBack {
        void comfirm();
        void cancel();
    }
}
