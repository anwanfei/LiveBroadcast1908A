package com.example.finalproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.finalproject.R;
import com.example.finalproject.adapter.SmartAdaper;
import com.example.finalproject.bean.SmartBean;
import com.example.finalproject.presenter.ImpSmartPresenter;
import com.example.finalproject.view.SmartView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmartFragment extends Fragment implements SmartView, OnRefreshLoadMoreListener {

    @BindView(R.id.rv_smart)
    RecyclerView rvSmart;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    private Unbinder bind;
    private SmartAdaper adaper;
    private ArrayList<SmartBean.ResultsBean> list;
    private int page = 4;

    public SmartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_smart, container, false);
        bind = ButterKnife.bind(this, inflate);
        initView();
        initData();
        return inflate;
    }

    private void initData() {
        ImpSmartPresenter impSmartPresenter = new ImpSmartPresenter(this);
        impSmartPresenter.getSmarts(page);
    }

    private void initView() {
        rvSmart.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        list = new ArrayList<>();
        adaper = new SmartAdaper(getActivity(), list);
        rvSmart.setAdapter(adaper);

        srl.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    public void onSuccess(SmartBean smartBean) {
        List<SmartBean.ResultsBean> results = smartBean.getResults();
        list.addAll(results);
        adaper.notifyDataSetChanged();
        srl.finishLoadMore();
        srl.finishRefresh();
    }

    @Override
    public void onFail(String error) {
        Log.e("TAG", error);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        initData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 4;
        if (list.size() > 0) {
            list.clear();
            initData();
        }
    }
}
