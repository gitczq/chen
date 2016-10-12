package com.example.qiong_pc.myapplication.com.example.qiong_pcAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qiong_pc.myapplication.R;

/**
 * Created by Qiong-PC on 2016/9/7.
 */
public class HomeAdapter extends BaseAdapter {

    int[] imageId={R.drawable.act_home_safe,R.drawable.act_home_callmsgsafe,R.drawable.act_home_app,R.drawable.act_home_trojan,
            R.drawable.act_home_sysoptimize,R.drawable.act_home_taskmanager,R.drawable.act_home_netmanager,R.drawable.act_home_atools,
            R.drawable.act_home_settings,};
    String[] names={"手机防盗","通讯卫士","软件管家","手机杀毒","缓存清理","进程管理","流量统计","高级工具","设置中心"};
    private Context context;
    public HomeAdapter(Context context){
        this.context=context;
    }


    @Override
    public int getCount() {
        return imageId.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(context,R.layout.activity_home_item,null);
        ImageView iv_icon=(ImageView) view.findViewById(R.id.iv_icon);
        TextView tv_name=(TextView) view.findViewById(R.id.tv_name);
        iv_icon.setImageResource(imageId[position]);
        tv_name.setText(names[position]);
        return view;
    }
}
