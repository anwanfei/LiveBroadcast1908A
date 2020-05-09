package com.anfly.exercise11.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anfly.exercise11.R;
import com.anfly.exercise11.adapter.CollectionAdapter;
import com.anfly.exercise11.bean.FoodDbBean;
import com.anfly.exercise11.utils.DbHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CollectionFragment extends Fragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    private Unbinder unbinder;
    private ArrayList<FoodDbBean> list;
    private CollectionAdapter collectionAdapter;

    public CollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initData();
        } else {
            if (list != null && list.size() > 0) {
                list.clear();
            }
        }
    }

    private void initData() {
        List<FoodDbBean> foodDbBeans = DbHelper.getHelper().queryAll();
        list.addAll(foodDbBeans);
        collectionAdapter.notifyDataSetChanged();
    }

    private void initView() {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        collectionAdapter = new CollectionAdapter(getActivity(), list);
        rv.setAdapter(collectionAdapter);

        collectionAdapter.setOnItemLongClickListener(new CollectionAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                boolean delete = DbHelper.getHelper().delete(list.get(position));
                if (delete) {
                    list.remove(position);
                    collectionAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
