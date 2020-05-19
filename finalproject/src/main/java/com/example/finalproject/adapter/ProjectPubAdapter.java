package com.example.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.bean.PublicBean;

import java.util.ArrayList;

public class ProjectPubAdapter extends RecyclerView.Adapter<ProjectPubAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PublicBean.DataBean.DatasBean> list;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ProjectPubAdapter(Context context, ArrayList<PublicBean.DataBean.DatasBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        PublicBean.DataBean.DatasBean datasBean = list.get(position);
        holder.tv_chapter_name.setText(datasBean.getChapterName());
        holder.tv_name.setText(datasBean.getAuthor());
        holder.tv_title.setText(datasBean.getDesc());
        holder.tv_time.setText(datasBean.getNiceShareDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null) {
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
        public ImageView iv_thumb;
        public TextView tv_chapter_name;
        public TextView tv_title;
        public TextView tv_name;
        public TextView tv_time;

        public ViewHolder(@NonNull View rootView) {
            super(rootView);
            this.iv_thumb = (ImageView) rootView.findViewById(R.id.iv_thumb);
            this.tv_chapter_name = (TextView) rootView.findViewById(R.id.tv_chapter_name);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
