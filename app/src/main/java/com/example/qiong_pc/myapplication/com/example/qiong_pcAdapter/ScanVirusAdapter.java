package com.example.qiong_pc.myapplication.com.example.qiong_pcAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qiong_pc.myapplication.com.example.Info.ScanAppinfo;
import com.example.qiong_pc.myapplication.R;
import java.util.List;

/**
 * Created by Qiong-PC on 2016/10/1.
 */
public class ScanVirusAdapter extends BaseAdapter {

    private List<ScanAppinfo> mScanAppInfos;
    private Context context;
    public ScanVirusAdapter(List<ScanAppinfo> scanAppinfo,Context context){
        super();
        mScanAppInfos=scanAppinfo;
        this.context=context;
    }

    @Override
    public int getCount() {
        return mScanAppInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mScanAppInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            //由于程序锁的条目与病毒扫描内容基本一致,因此可以重用程序锁的Item布局
            convertView = View.inflate(context, R.layout.activity_virusscanspeed_item_list_applock, null);
            holder = new ViewHolder();
            holder.mAppIconImgv = (ImageView) convertView.findViewById(R.id.imgv_appicon);
            holder.mAppNameTV = (TextView) convertView.findViewById(R.id.tv_appname);
            holder.mScanIconImgv = (ImageView) convertView.findViewById(R.id.imgv_lock);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ScanAppinfo scanAppinfo = mScanAppInfos.get(position);
        if (!scanAppinfo.isVirus) {
            holder.mScanIconImgv.setBackgroundResource(R.drawable.activity_virusscanspeed_item_list_applock_blue_right_icon);
            holder.mAppNameTV.setTextColor(context.getResources().getColor(R.color.act_home_item_textColor));
            holder.mAppNameTV.setText(scanAppinfo.appName);
        } else{
            holder.mAppNameTV.setTextColor(context.getResources().getColor(R.color.activity_virusscanspeed_item_list_textcolor));
            holder.mAppNameTV.setText(scanAppinfo.appName+"("+scanAppinfo.description+")");
        }
        holder.mAppIconImgv.setImageDrawable(scanAppinfo.appicon);
        return convertView;
    }
    static class ViewHolder {
        ImageView mAppIconImgv;
        TextView mAppNameTV;
        ImageView mScanIconImgv;
    }
}
