package com.example.finalproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.example.finalproject.api.ApiSerivce;
import com.example.finalproject.bean.DownLoadProgressBean;
import com.example.finalproject.utils.InstallUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownLoadService extends Service {
    public DownLoadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        downLoad();
        super.onCreate();
    }

    private void downLoad() {
        //获取retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiSerivce.baseDownUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        //获取服务接口对象
        ApiSerivce apiSerivce = retrofit.create(ApiSerivce.class);

        //获取被观察者
        Observable<ResponseBody> observable = apiSerivce.downLoad();

        //订阅获取网络数据
        observable.subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        InputStream inputStream = responseBody.byteStream();
                        long contentLength = responseBody.contentLength();
                        savaFile(inputStream, contentLength, Environment.getExternalStorageDirectory() + "/re.apk");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "error:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void savaFile(InputStream inputStream, long contentLength, String path) {
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(path));
            int len = -1;
            long count = 0;
            byte[] bytes = new byte[1024 * 10];
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                count += len;
                EventBus.getDefault().post(new DownLoadProgressBean(count, contentLength));
            }

            inputStream.close();
            outputStream.close();

            Log.e("TAG", "下载完成");
            InstallUtil.installApk(this, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
