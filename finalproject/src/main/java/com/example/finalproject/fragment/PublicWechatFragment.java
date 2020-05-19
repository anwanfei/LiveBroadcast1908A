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
import com.example.finalproject.adapter.ProjectPubAdapter;
import com.example.finalproject.adapter.WeChatAdapter;
import com.example.finalproject.api.ApiSerivce;
import com.example.finalproject.bean.PublicBean;
import com.example.finalproject.bean.TabBean;

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
public class PublicWechatFragment extends Fragment {

    @BindView(R.id.rv_public_fragment)
    RecyclerView rvPublicFragment;
    private int id;
    private Unbinder bind;
    private ProjectPubAdapter adapter;
    private ArrayList<PublicBean.DataBean.DatasBean> list;

    public PublicWechatFragment(int id) {
        this.id = id;
    }

    public PublicWechatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_public_wechat, container, false);
        bind = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiSerivce.baseBannerUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiSerivce apiSerivce = retrofit.create(ApiSerivce.class);
        Observable<PublicBean> tabs = apiSerivce.getPublicFragmentList(id);
        tabs.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PublicBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PublicBean tabBean) {
                        list.addAll(tabBean.getData().getDatas());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "网络错误：" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initView() {
        rvPublicFragment.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        adapter = new ProjectPubAdapter(getActivity(), list);
        rvPublicFragment.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
