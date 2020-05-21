package com.example.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.bean.SmartBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SmartAdaper extends RecyclerView.Adapter<SmartAdaper.ViewHolder> {
    private Context context;
    private ArrayList<SmartBean.ResultsBean> list;

    public SmartAdaper(Context context, ArrayList<SmartBean.ResultsBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_smart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SmartBean.ResultsBean resultsBean = list.get(position);
        Glide.with(context).load(resultsBean.getUrl()).into(holder.ivItemSmart);
        holder.tvItemSmart.setText(resultsBean.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_smart)
        ImageView ivItemSmart;
        @BindView(R.id.tv_item_smart)
        TextView tvItemSmart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
