package com.example.qiuchenluoye.hellotrueclient.adapter;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qiuchenluoye.hellotrueclient.R;
import com.example.qiuchenluoye.hellotrueclient.utilsClass.retDataClass.mQuirysInfo;

import java.util.List;

/**
 * Created by QiuChenluoye on 2017/07/18.
 */

public class mBillingInquiryAdapter extends RecyclerView.Adapter<mBillingInquiryAdapter.VH> {

    List<mQuirysInfo> data;
    boolean isFull;
    int count;

    public void setFull(boolean full) {
        isFull = full;
        notifyDataSetChanged();
    }

    //设置适配器数据
    public void setData(List<mQuirysInfo> d) {
        this.data = d;
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH v = new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.minquiryinfocardview, parent, false));
        return v;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        count = position;
        if (count == data.size() && isFull) {
        } else {
            mQuirysInfo m = data.get(position);
            try {
                holder.time.setText(m.createTime);
                holder.ItemName.setText(m.sName);
                holder.phoneNum.setText(m.phone);
            } catch (NullPointerException e) {
                Log.d("QiuChen", "空指针异常!");
            }

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class VH extends RecyclerView.ViewHolder {
        TextView time, phoneNum, ItemName;

        public VH(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.minfo_Time);
            phoneNum = (TextView) itemView.findViewById(R.id.minfo_ItemNum);
            ItemName = (TextView) itemView.findViewById(R.id.minfo_ItemName);
        }
    }

    public void addData(List<mQuirysInfo> d) {
        this.data.addAll(d);
        notifyDataSetChanged();
    }

    public void addData(mQuirysInfo d) {
        this.data.add(d);
        notifyDataSetChanged();
    }
}
