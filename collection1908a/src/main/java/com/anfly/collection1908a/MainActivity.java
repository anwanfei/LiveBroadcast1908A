package com.anfly.collection1908a;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.anfly.collection1908a.adaper.VpAdapter;
import com.anfly.collection1908a.fragment.CollectionFragment;
import com.anfly.collection1908a.fragment.HomeFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp;
    private TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp);
        tab = (TabLayout) findViewById(R.id.tab);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CollectionFragment());

        ArrayList<String> titles = new ArrayList<>();
        titles.add("首页");
        titles.add("收藏");

        VpAdapter adapter = new VpAdapter(getSupportFragmentManager(), fragments, titles);
        vp.setAdapter(adapter);
        tab.setupWithViewPager(vp);
    }
}
