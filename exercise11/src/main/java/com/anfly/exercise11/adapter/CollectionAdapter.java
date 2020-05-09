package com.anfly.exercise11.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anfly.exercise11.R;
import com.anfly.exercise11.bean.FoodDbBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<FoodDbBean> list;
    private final LayoutInflater inflater;
    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public CollectionAdapter(Context context, ArrayList<FoodDbBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    private int VIEW_TYPE_ONE = 1;
    private int VIEW_TYPE_TWO = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ONE) {
            View view = inflater.inflate(R.layout.item1, parent, false);
            return new ViewHolder1(view);
        } else {
            View view = inflater.inflate(R.layout.item2, parent, false);
            return new ViewHolder2(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FoodDbBean dataBean = list.get(position);
        int itemViewType = holder.getItemViewType();
        if (itemViewType == VIEW_TYPE_ONE) {
            ViewHolder1 viewHolder1 = (ViewHolder1) holder;
            viewHolder1.tvItem1.setText(dataBean.getName());
            Glide.with(context).load(dataBean.getUrl()).into(viewHolder1.ivItem1);
        } else {
            ViewHolder2 viewHolder2 = (ViewHolder2) holder;
            viewHolder2.tvItem2.setText(dataBean.getName());
            Glide.with(context).load(dataBean.getUrl()).into(viewHolder2.ivItem2);
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemLongClickListener!=null) {
                    onItemLongClickListener.onItemLongClick(position);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 3 == 0) {
            return VIEW_TYPE_ONE;
        } else {
            return VIEW_TYPE_TWO;
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item1)
        ImageView ivItem1;
        @BindView(R.id.tv_item1)
        TextView tvItem1;

        ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item2)
        ImageView ivItem2;
        @BindView(R.id.tv_item2)
        TextView tvItem2;

        ViewHolder2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }
}
