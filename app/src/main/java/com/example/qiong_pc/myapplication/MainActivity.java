package com.example.qiong_pc.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.qiong_pc.myapplication.com.example.qiong_pcAdapter.HomeAdapter;


public class MainActivity extends AppCompatActivity {
    private GridView gv_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //初始化GridView
        gv_home=(GridView) findViewById(R.id.act_home_gv_home);
        gv_home.setAdapter(new HomeAdapter(MainActivity.this));
    }
}
