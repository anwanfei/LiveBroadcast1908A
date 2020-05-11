package com.example.upload;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_upload_ok;
    private File file = new File(Environment.getExternalStorageDirectory() + "/Pictures/b.jpg");
    private ImageView iv;
    private Button btn_upload_retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_upload_ok = (Button) findViewById(R.id.btn_upload_ok);

        btn_upload_ok.setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.iv);
        btn_upload_retrofit = (Button) findViewById(R.id.btn_upload_retrofit);
        btn_upload_retrofit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upload_ok:
                uploadOk();
                break;
            case R.id.btn_upload_retrofit:
                uploadRetrofit();
                break;
        }
    }

    private void uploadRetrofit() {
        //获取retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //接口服务对象
        ApiService service = retrofit.create(ApiService.class);

        //封装请求体
        RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part file = MultipartBody.Part.createFormData("file", this.file.getName(), body);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "1908A");

        Observable<UploadBean> observable = service.upload(requestBody, file);
        //发送网络请求并处理结果
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UploadBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UploadBean uploadBean) {
                        Toast.makeText(MainActivity.this, uploadBean.getRes(), Toast.LENGTH_SHORT).show();
                        Glide.with(MainActivity.this).load(uploadBean.getData().getUrl()).into(iv);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "net error:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 真机测试补习动态添加文件权限
     */
    private void uploadOk() {
        //获取ok对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        //获取请求体
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);

        //多类型的请求体
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "1909A")
                .addFormDataPart("file", file.getName(), requestBody)
                .build();

        //构建请求
        Request request = new Request.Builder()
                .url("http://yun918.cn/study/public/file_upload.php")
                .post(body)
                .build();

        //获取call对象
        Call call = okHttpClient.newCall(request);
        //call执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "网络错误：" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                final UploadBean uploadBean = new Gson().fromJson(json, UploadBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, uploadBean.getRes(), Toast.LENGTH_SHORT).show();
                        Glide.with(MainActivity.this).load(uploadBean.getData().getUrl()).into(iv);
                    }
                });
            }
        });
    }
}
