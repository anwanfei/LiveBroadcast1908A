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
import com.example.finalproject.bean.BannerBean;
import com.example.finalproject.bean.PublicBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<PublicBean.DataBean.DatasBean> list;
    private ArrayList<BannerBean.DataBean> banners;
    private int VIEW_TYPE_ONE = 1;
    private int VIEW_TYPE_TWO = 2;
    private final LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RxAdapter(Context context, ArrayList<PublicBean.DataBean.DatasBean> list, ArrayList<BannerBean.DataBean> banners) {
        this.context = context;
        this.list = list;
        this.banners = banners;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ONE) {
            View view = inflater.inflate(R.layout.item_home_banner, parent, false);
            return new ViewHolderOne(view);
        } else {
            View view = inflater.inflate(R.layout.item_home_list, parent, false);
            return new ViewHolderTwo(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = holder.getItemViewType();
        if (itemViewType == VIEW_TYPE_ONE) {
            ViewHolderOne viewHolderOne = (ViewHolderOne) holder;

            ArrayList<String> imagePath = new ArrayList<>();
            ArrayList<String> titles = new ArrayList<>();
            for (int i = 0; i < banners.size(); i++) {
                imagePath.add(banners.get(i).getImagePath());
                titles.add(banners.get(i).getTitle());
            }
            viewHolderOne.banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                    .setBannerTitles(titles)
                    .setImages(imagePath)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            Glide.with(context).load(path).into(imageView);
                        }
                    }).start();
        } else {
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            PublicBean.DataBean.DatasBean datasBean = list.get(position - 1);
            viewHolderTwo.tvHomeItem.setText(datasBean.getTitle());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position - 1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_ONE;
        } else {
            return VIEW_TYPE_TWO;
        }
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        @BindView(R.id.banner)
        Banner banner;

        ViewHolderOne(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_hoem_item)
        ImageView ivHoemItem;
        @BindView(R.id.tv_home_item)
        TextView tvHomeItem;

        ViewHolderTwo(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
