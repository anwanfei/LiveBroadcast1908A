package com.anfly.livebroadcast1908a;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class OkAdapter extends RecyclerView.Adapter<OkAdapter.ViewHolder> {

    private ArrayList<FoodBean.DataBean> list;
    private Context context;

    public OkAdapter(ArrayList<FoodBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ok, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodBean.DataBean dataBean = list.get(position);
        Glide.with(context).load(dataBean.getPic()).into(holder.iv_item_ok);
        holder.tv_item_ok.setText(dataBean.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_item_ok;
        public TextView tv_item_ok;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_item_ok = (ImageView) itemView.findViewById(R.id.iv_item_ok);
            this.tv_item_ok = (TextView) itemView.findViewById(R.id.tv_item_ok);
        }
    }

    public void updataData(List<FoodBean.DataBean> data) {
        list.clear();
        if (data != null){
            list.addAll(data);
        }
        notifyDataSetChanged();
    }
}
