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
import com.anfly.collectionmvp.adapter.CollectionAdapter;
import com.anfly.collectionmvp.bean.ConllectionDbBean;
import com.anfly.collectionmvp.presenter.ImpCollectionPresenter;
import com.anfly.collectionmvp.view.CollectionVieiw;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment implements CollectionVieiw, CollectionAdapter.OnItemClickListener {
    private RecyclerView rv_collection;
    private ArrayList<ConllectionDbBean> list;
    private CollectionAdapter adapter;
    private ImpCollectionPresenter presenter;
    private int position;

    public CollectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        presenter = new ImpCollectionPresenter(this);
        initView(view);
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
        presenter.query();
    }

    private void initView(View view) {
        rv_collection = view.findViewById(R.id.rv_collection);
        rv_collection.setLayoutManager(new LinearLayoutManager(getActivity()));

        list = new ArrayList<>();
        adapter = new CollectionAdapter(list, getActivity());

        rv_collection.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onQuerySuccess(List<ConllectionDbBean> datas) {
        list.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onQueryFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteSuccess(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        list.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int position) {
        this.position = position;
        ConllectionDbBean conllectionDbBean = list.get(position);
        presenter.delete(conllectionDbBean);
    }
}
