package com.example.finalproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.adapter.HomeAdapter;
import com.example.finalproject.adapter.RxAdapter;
import com.example.finalproject.bean.BannerBean;
import com.example.finalproject.bean.ComPoseBean;
import com.example.finalproject.bean.FoodBean;
import com.example.finalproject.bean.PublicBean;
import com.example.finalproject.presenter.ImpRxPresenter;
import com.example.finalproject.view.RxView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RxjavaFragment extends Fragment implements RxView {

    @BindView(R.id.rv_rx)
    RecyclerView rvRx;
    private Unbinder bind;
    private ArrayList<PublicBean.DataBean.DatasBean> list;
    private ArrayList<BannerBean.DataBean> banners;
    private RxAdapter adapter;

    public RxjavaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rxjava, container, false);
        bind = ButterKnife.bind(this, view);
        intiView();
        initData();
        return view;
    }

    private void initData() {
        new ImpRxPresenter(this).getRxData();
    }

    private void intiView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvRx.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        banners = new ArrayList<>();
        adapter = new RxAdapter(getActivity(), list, banners);
        rvRx.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    public void onSuccess(ComPoseBean comPoseBean) {
        List<BannerBean.DataBean> bannerList = comPoseBean.getBannerBean().getData();
        List<PublicBean.DataBean.DatasBean> articles = comPoseBean.getPublicBean().getData().getDatas();
        banners.addAll(bannerList);
        list.addAll(articles);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFail(String error) {
        Log.e("TAG", error);
    }
}
