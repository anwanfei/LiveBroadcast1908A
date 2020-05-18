package com.example.finalproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.adapter.CollectionAdapter;
import com.example.finalproject.bean.FoodDbBean;
import com.example.finalproject.utils.DbHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment {

    @BindView(R.id.rv_collection)
    RecyclerView rvCollection;
    private Unbinder bind;
    private ArrayList<FoodDbBean> list;
    private CollectionAdapter adapter;

    public CollectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        bind = ButterKnife.bind(this, view);
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
        List<FoodDbBean> foodDbBeans = DbHelper.getDbHelper().queryAll();
        list.addAll(foodDbBeans);
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        rvCollection.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        adapter = new CollectionAdapter(getActivity(), list);
        rvCollection.setAdapter(adapter);
        adapter.setOnItemClickListener(new CollectionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FoodDbBean foodDbBean = list.get(position);
                boolean delete = DbHelper.getDbHelper().delete(foodDbBean);
                if (delete) {
                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
