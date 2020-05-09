package com.anfly.exercise11.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.anfly.exercise11.R;
import com.anfly.exercise11.bean.FoodBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class VpPhotoAdaspter extends PagerAdapter {

    private Context context;
    private List<FoodBean.DataBean> list;

    public VpPhotoAdaspter(Context context, List<FoodBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, null);

        ImageView iv = view.findViewById(R.id.iv);
        TextView tv = view.findViewById(R.id.tv);

        tv.setText("第" + (position + 1) + "张 /  共" + list.size() + "张");

        Glide.with(context).load(list.get(position).getPic()).into(iv);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
