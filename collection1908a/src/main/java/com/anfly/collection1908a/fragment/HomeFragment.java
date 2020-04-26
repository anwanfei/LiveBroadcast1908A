package com.anfly.collection1908a.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anfly.collection1908a.ApiService;
import com.anfly.collection1908a.ConllectionDbBean;
import com.anfly.collection1908a.DbHelper;
import com.anfly.collection1908a.FoodBean;
import com.anfly.collection1908a.R;
import com.anfly.collection1908a.adaper.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView rv_home;
    private ArrayList<FoodBean.DataBean> list;
    private HomeAdapter adapter;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        //获取retrofit对象
        Retrofit retrfit = new Retrofit.Builder()
                .baseUrl(ApiService.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //获取服务对象
        ApiService apiService = retrfit.create(ApiService.class);

        //获取call
        Call<FoodBean> call = apiService.getDadta();

        //call执行请求
        call.enqueue(new Callback<FoodBean>() {
            @Override
            public void onResponse(Call<FoodBean> call, Response<FoodBean> response) {
                FoodBean foodBean = response.body();
                List<FoodBean.DataBean> data = foodBean.getData();
                adapter.updataData(data);
            }

            @Override
            public void onFailure(Call<FoodBean> call, Throwable t) {
                Log.e("TAG", "网络错误：" + t.getMessage());
            }
        });
    }

    private void initView(View view) {
        rv_home = view.findViewById(R.id.rv_home);
        rv_home.setLayoutManager(new LinearLayoutManager(getActivity()));

        rv_home.addItemDecoration(new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL));

        list = new ArrayList<>();
        adapter = new HomeAdapter(list, getActivity());

        rv_home.setAdapter(adapter);

        adapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FoodBean.DataBean dataBean = list.get(position);
                ConllectionDbBean conllectionDbBean = new ConllectionDbBean();
                conllectionDbBean.setId(position);
                conllectionDbBean.setTitle(dataBean.getTitle());
                conllectionDbBean.setUrlPath(dataBean.getPic());
                long insert = DbHelper.getInstancec().insert(conllectionDbBean);
                if (insert >= 0) {
                    Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "收藏失敗", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
