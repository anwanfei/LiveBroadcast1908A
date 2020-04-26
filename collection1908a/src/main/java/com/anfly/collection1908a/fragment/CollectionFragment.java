package com.anfly.collection1908a.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anfly.collection1908a.ConllectionDbBean;
import com.anfly.collection1908a.DbHelper;
import com.anfly.collection1908a.R;
import com.anfly.collection1908a.adaper.CollectionAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment {

    private RecyclerView rv_collection;
    private ArrayList<ConllectionDbBean> list;
    private CollectionAdapter adapter;

    public CollectionFragment() {
        // Required empty public constructor
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        initView(view);
        return view;
    }

    private void initData() {
        List<ConllectionDbBean> conllectionDbBeans = DbHelper.getInstancec().queryAll();
        adapter.updataData(conllectionDbBeans);
    }

    private void initView(View view) {
        rv_collection = view.findViewById(R.id.rv_collection);
        rv_collection.setLayoutManager(new LinearLayoutManager(getActivity()));

        rv_collection.addItemDecoration(new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL));

        list = new ArrayList<>();
        adapter = new CollectionAdapter(list, getActivity());

        rv_collection.setAdapter(adapter);

        adapter.setOnItemClickListener(new CollectionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ConllectionDbBean conllectionDbBean = list.get(position);
                boolean delete = DbHelper.getInstancec().delete(conllectionDbBean);
                if (delete) {
                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                }
                list.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
