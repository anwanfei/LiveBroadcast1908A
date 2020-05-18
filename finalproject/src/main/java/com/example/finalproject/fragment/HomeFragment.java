package com.example.finalproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.adapter.HomeAdapter;
import com.example.finalproject.api.ApiSerivce;
import com.example.finalproject.bean.BannerBean;
import com.example.finalproject.bean.FoodBean;
import com.example.finalproject.bean.FoodDbBean;
import com.example.finalproject.presenter.ImpHomePrensenter;
import com.example.finalproject.utils.DbHelper;
import com.example.finalproject.view.HomeView;

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
public class HomeFragment extends Fragment implements HomeView {

    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    private Unbinder bind;
    private ArrayList<FoodBean.DataBean> list;
    private ArrayList<BannerBean.DataBean> banners;
    private HomeAdapter adapter;

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
        ImpHomePrensenter prensenter = new ImpHomePrensenter(this);
        prensenter.getHomeList();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiSerivce.baseBannerUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiSerivce serivce = retrofit.create(ApiSerivce.class);
        Observable<BannerBean> bannner = serivce.getBannner();
        bannner.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BannerBean bannerBean) {
                        List<BannerBean.DataBean> data = bannerBean.getData();
                        banners.addAll(data);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "banner eror：" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvHome.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        banners = new ArrayList<>();
        adapter = new HomeAdapter(getActivity(), list, banners);
        rvHome.setAdapter(adapter);
        adapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FoodBean.DataBean dataBean = list.get(position);
                FoodDbBean foodDbBean = new FoodDbBean();
                foodDbBean.setId(Long.valueOf(position));
                foodDbBean.setPath(dataBean.getPic());
                foodDbBean.setTitle(dataBean.getTitle());
                long insert = DbHelper.getDbHelper().insert(foodDbBean);
                if (insert >= 0) {
                    Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "收藏失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    public void onSuccess(FoodBean foodBean) {
        List<FoodBean.DataBean> data = foodBean.getData();
        list.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}
