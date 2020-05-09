package com.anfly.exercise11.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anfly.exercise11.R;
import com.anfly.exercise11.adapter.HomeAdapter;
import com.anfly.exercise11.bean.FoodBean;
import com.anfly.exercise11.bean.FoodDbBean;
import com.anfly.exercise11.presenter.ImpHomePresenter;
import com.anfly.exercise11.receiver.ExerciseReceiver;
import com.anfly.exercise11.utils.DbHelper;
import com.anfly.exercise11.view.HomeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeView {

    @BindView(R.id.rv)
    RecyclerView rv;
    private Unbinder bind;
    private ArrayList<FoodBean.DataBean> list;
    private HomeAdapter adapter;
    private int position;
    private FoodBean foodBean;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bind = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initData() {
        ImpHomePresenter presenter = new ImpHomePresenter(this);
        presenter.getFoodData();
    }

    private void initView() {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        adapter = new HomeAdapter(getActivity(), list);
        rv.setAdapter(adapter);
        adapter.setOnItemLongClickListener(new HomeAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int newposition) {
                position = newposition;
            }
        });

        adapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FoodBean.DataBean dataBean = list.get(position);
                FoodDbBean foodDbBean = new FoodDbBean();
                foodDbBean.setId(Long.valueOf(position));
                foodDbBean.setName(dataBean.getTitle());
                foodDbBean.setUrl(dataBean.getPic());
                long insert = DbHelper.getHelper().insert(foodDbBean);
                if (insert >= 0) {
                    Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "收藏失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerForContextMenu(rv);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 0, 0, "发送广播");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Intent intent = new Intent(getActivity(), ExerciseReceiver.class);
                intent.putExtra("data", foodBean);
                getActivity().sendBroadcast(intent);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    public void onSuccess(FoodBean foodBean) {
        List<FoodBean.DataBean> data = foodBean.getData();
        this.foodBean = foodBean;
        list.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}
