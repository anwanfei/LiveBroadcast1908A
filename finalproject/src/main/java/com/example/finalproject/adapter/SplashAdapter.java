package com.example.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;

import java.util.ArrayList;

public class SplashAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> list;

    public SplashAdapter(Context context, ArrayList<String> list) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_splash, null);

        ImageView iv_splash = view.findViewById(R.id.iv_splash);
        TextView tv_splash = view.findViewById(R.id.tv_splash);
        TextView tv_splash_page = view.findViewById(R.id.tv_splash_page);
        Glide.with(context).load(R.drawable.ic_launcher_background).into(iv_splash);
        tv_splash.setText(list.get(position));
        tv_splash_page.setText((position + 1) + " / " + list.size());

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
