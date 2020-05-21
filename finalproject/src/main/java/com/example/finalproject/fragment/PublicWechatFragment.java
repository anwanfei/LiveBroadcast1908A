package com.example.finalproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.activity.WebviewActivity;
import com.example.finalproject.adapter.ProjectPubAdapter;
import com.example.finalproject.api.ApiSerivce;
import com.example.finalproject.bean.PublicBean;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
                .client(getOkHttpClient())
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

    //离线缓存，只能用于get请求
    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new MyCacheinterceptor())
                .addNetworkInterceptor(new MyCacheinterceptor())
                .cache(new Cache(new File(getActivity().getCacheDir(), "cache"), 1024 * 1024 * 10))
                .build();
    }

    private void initView() {
        rvPublicFragment.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        adapter = new ProjectPubAdapter(getActivity(), list);
        rvPublicFragment.setAdapter(adapter);
        adapter.setOnItemClickListener(new ProjectPubAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String link = list.get(position).getLink();
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                startActivity(intent);
                EventBus.getDefault().postSticky(link);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    class MyCacheinterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //1.获取请求数据
            Request request = chain.request();
            //2.判断如果无网时，设置缓存协议
            if (!isNetworkAvailable(getActivity())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();

            }
            //3.开始请求网络，获取响应数据
            Response originalResponse = chain.proceed(request);
            //4.判断是否有网
            if (isNetworkAvailable(getActivity())) {
                //有网，获取网络数据
                int maxAge = 0;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                //没有网络，获取缓存数据
                int maxStale = 15 * 60;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }

        }
    }

    /**
     * 检测是否有网
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }
}
