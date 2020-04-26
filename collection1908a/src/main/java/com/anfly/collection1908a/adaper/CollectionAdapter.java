package com.anfly.collection1908a.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anfly.collection1908a.ConllectionDbBean;
import com.anfly.collection1908a.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    private ArrayList<ConllectionDbBean> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CollectionAdapter(ArrayList<ConllectionDbBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ConllectionDbBean dataBean = list.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.circleCrop();
        Glide.with(context).load(dataBean.getUrlPath()).apply(requestOptions).into(holder.iv_item_ok);
        holder.tv_item_ok.setText(dataBean.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
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

    public void updataData(List<ConllectionDbBean> data) {
        list.clear();
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
