package com.anfly.exercise11.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.anfly.exercise11.R;
import com.anfly.exercise11.adapter.VpPhotoAdaspter;
import com.anfly.exercise11.bean.FoodBean;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VpActivity extends AppCompatActivity {

    @BindView(R.id.vp)
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp);
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        FoodBean data = (FoodBean) getIntent().getSerializableExtra("data");
        List<FoodBean.DataBean> list = data.getData();

        VpPhotoAdaspter adapter = new VpPhotoAdaspter(this, list);
        vp.setAdapter(adapter);
    }
}
