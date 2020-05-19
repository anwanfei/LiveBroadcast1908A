package com.example.finalproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.api.ApiSerivce;
import com.example.finalproject.bean.DownLoadProgressBean;
import com.example.finalproject.bean.UploadBean;
import com.example.finalproject.service.DownLoadService;
import com.example.finalproject.utils.InstallUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownUpLoadActivity extends AppCompatActivity {

    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.iv_upload)
    ImageView ivUpload;
    @BindView(R.id.btn_download)
    Button btnDownload;
    @BindView(R.id.pb_download)
    ProgressBar pbDownload;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.btn_download_in_service)
    Button btnDownloadInService;
    private File file = new File(Environment.getExternalStorageDirectory() + "/Pictures/b.jpg");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_up_load);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getProgress(DownLoadProgressBean progressBean){
        long count = progressBean.getCount();
        long contentLength = progressBean.getContentLength();
        pbDownload.setMax((int) contentLength);
        pbDownload.setProgress((int) count);
        tvProgress.setText((int) 100 * count / contentLength + "%");
    }

    @OnClick({R.id.btn_upload, R.id.btn_download, R.id.btn_download_in_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_upload:
                upload();
                break;
            case R.id.btn_download:
                downLoad();
                break;
            case R.id.btn_download_in_service:
                downLoadInservice();
                break;
        }
    }

    private void downLoadInservice() {
        startService(new Intent(this, DownLoadService.class));
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
        pbDownload.setMax((int) contentLength);
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(path));
            int len = -1;
            long count = 0;
            byte[] bytes = new byte[1024 * 10];
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                count += len;
                long finalCount = count;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pbDownload.setProgress((int) finalCount);
                        tvProgress.setText((int) 100 * finalCount / contentLength + "%");
                    }
                });
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2019) {
            InstallUtil.installApk(this, Environment.getExternalStorageDirectory() + "/a.apk");//再次执行安装流程，包含权限判等
        }
    }

    private void upload() {
        //获取retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiSerivce.baseUploadUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //接口服务对象自
        ApiSerivce apiSerivce = retrofit.create(ApiSerivce.class);

        //获取被观察者
        RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part file = MultipartBody.Part.createFormData("file", this.file.getName(), body);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "1908A");
        Observable<UploadBean> observable = apiSerivce.upload(requestBody, file);

        //订阅
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UploadBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UploadBean uploadBean) {
                        int code = uploadBean.getCode();
                        if (code == 200) {
                            Glide.with(DownUpLoadActivity.this).load(uploadBean.getData().getUrl()).into(ivUpload);
                        } else {
                            Toast.makeText(DownUpLoadActivity.this, uploadBean.getRes(), Toast.LENGTH_SHORT).show();
                        }
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
}
