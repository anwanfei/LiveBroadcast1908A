package com.example.finalproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.finalproject.R;
import com.example.finalproject.adapter.WeChatAdapter;
import com.example.finalproject.api.ApiSerivce;
import com.example.finalproject.bean.TabBean;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class WechatFragment extends Fragment {

    @BindView(R.id.tab_wechat)
    TabLayout tabWechat;
    @BindView(R.id.vp_wechat)
    ViewPager vpWechat;
    private Unbinder bind;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private WeChatAdapter adapter;

    public WechatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wechat, container, false);
        bind = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        tabWechat.setTabMode(TabLayout.MODE_SCROLLABLE);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiSerivce.baseBannerUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiSerivce apiSerivce = retrofit.create(ApiSerivce.class);
        Observable<TabBean> tabs = apiSerivce.getTabs();
        tabs.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TabBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TabBean tabBean) {
                        List<TabBean.DataBean> data = tabBean.getData();
                        for (int i = 0; i < data.size(); i++) {
                            PublicWechatFragment publicWechatFragment = new PublicWechatFragment(data.get(i).getId());
                            fragments.add(publicWechatFragment);
                            titles.add(data.get(i).getName());
                        }
                        adapter = new WeChatAdapter(getChildFragmentManager(), fragments, titles);
                        vpWechat.setAdapter(adapter);
                        tabWechat.setupWithViewPager(vpWechat);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "公众号网络错误：" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
