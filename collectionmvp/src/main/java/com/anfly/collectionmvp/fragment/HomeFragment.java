package com.anfly.collectionmvp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anfly.collectionmvp.R;
import com.anfly.collectionmvp.adapter.HomeAdapter;
import com.anfly.collectionmvp.bean.ConllectionDbBean;
import com.anfly.collectionmvp.bean.FoodBean;
import com.anfly.collectionmvp.presenter.ImpHomePresenter;
import com.anfly.collectionmvp.view.HomeView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeView, HomeAdapter.OnItemClickListener {

    private RecyclerView rv_home;
    private ArrayList<FoodBean.DataBean> list;
    private HomeAdapter adapter;
    private ImpHomePresenter presenter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        presenter = new ImpHomePresenter(this);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        presenter.homeData();
    }

    private void initView(View view) {
        rv_home = view.findViewById(R.id.rv_home);

        rv_home.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        adapter = new HomeAdapter(list, getActivity());
        rv_home.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onSuccess(FoodBean foodBean) {
        List<FoodBean.DataBean> data = foodBean.getData();
        list.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onInsertSuccess(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInsertFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int position) {
        FoodBean.DataBean dataBean = list.get(position);
        ConllectionDbBean conllectionDbBean = new ConllectionDbBean();
        conllectionDbBean.setId(position);
        conllectionDbBean.setTitle(dataBean.getTitle());
        conllectionDbBean.setUrlPath(dataBean.getPic());
        presenter.insert(conllectionDbBean);
    }
}
