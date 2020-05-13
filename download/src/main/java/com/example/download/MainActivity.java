package com.example.download;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_ok;
    private String downloadUrl = "http://cdn.banmi.com/banmiapp/apk/banmi_330.apk";
    private Button btn_retrofit;
    private Button btn_con;
    private ProgressBar pb1;
    private int count;
    private TextView tv1;
    private Button btn_multi;
    private ProgressBar pb2;
    private TextView tv2;
    private ProgressBar pb3;
    private TextView tv3;
    private String path = Environment.getExternalStorageDirectory() + "/con.apk";
    private int THREAD_COUNT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_ok = (Button) findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(this);
        btn_retrofit = (Button) findViewById(R.id.btn_retrofit);
        btn_retrofit.setOnClickListener(this);
        btn_con = (Button) findViewById(R.id.btn_con);
        btn_con.setOnClickListener(this);
        pb1 = (ProgressBar) findViewById(R.id.pb1);
        pb1.setOnClickListener(this);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setOnClickListener(this);
        btn_multi = (Button) findViewById(R.id.btn_multi);
        btn_multi.setOnClickListener(this);
        pb2 = (ProgressBar) findViewById(R.id.pb2);
        pb2.setOnClickListener(this);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv2.setOnClickListener(this);
        pb3 = (ProgressBar) findViewById(R.id.pb3);
        pb3.setOnClickListener(this);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                downLoadOk();
                break;
            case R.id.btn_retrofit:
                downLoadRetrofit();
                break;
            case R.id.btn_con:
                downloadCon();
                break;
            case R.id.btn_multi:
                downLoadMulti();
                break;
        }
    }

    private void downLoadMulti() {
        ThreadManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //1.获取文件大小
                    URL url = new URL(downloadUrl);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    long contentLength = con.getContentLength();

                    //2.创建一个与文件一样大的空壳文件
                    RandomAccessFile raf = new RandomAccessFile(path, "rw");
                    raf.setLength(contentLength);

                    //3.分块
                    long block = contentLength / THREAD_COUNT;

                    //4.计算每一块范围
                    for (int i = 0; i < THREAD_COUNT; i++) {
                        long start = i * block;
                        long end = (i + 1) * block - 1;
                        if (i == THREAD_COUNT - 1) {
                            end = contentLength - 1;
                        }
                        //5.下载
                        down(i, start, end);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void down(final int thredId, final long start, final long end) {
        ThreadManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                //定义变量保存当前线程的当前位置
                long curPosition = start;

                //获取保存位置
                long position = (long) SharedPreferencesUtils.getParam(MainActivity.this, thredId + "", 0L);
                if (position != 0) {
                    //断点续传
                    curPosition = position;
                    Log.e("TAG", thredId + "断点续传" + position);
                } else {
                    //非断点续传
                    Log.e("TAG", thredId + "非断点续传下载");
                }


                try {
                    //获取con
                    URL url = new URL(downloadUrl);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestProperty("Range", "bytes=" + curPosition + "-" + end);

                    //获取请求码
                    int responseCode = con.getResponseCode();

                    //执行网络请求，请求部分资源206
                    if (responseCode == HttpURLConnection.HTTP_PARTIAL) {
                        InputStream inputStream = con.getInputStream();
                        RandomAccessFile raf = new RandomAccessFile(path, "rw");
                        raf.seek(curPosition);
                        long count = 0;
                        byte[] bytes = new byte[1024 * 10];
                        int len = -1;
                        final long length = end - curPosition;

                        while ((len = inputStream.read(bytes)) != -1) {
                            raf.write(bytes, 0, len);
                            curPosition += len;
                            count += len;
                            Log.e("TAG", "线程: " + thredId + "总长度:" + length + "下载:" + count);

                            SharedPreferencesUtils.setParam(MainActivity.this, thredId + "", curPosition);
                            SharedPreferencesUtils.setParam(MainActivity.this, thredId + "count", count);

                            final long finalCount = count;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    switch (thredId) {
                                        case 0:
                                            pb1.setMax((int) length);
                                            pb1.setProgress((int) finalCount);
                                            tv1.setText((int) (100f * finalCount / length) + "%");
                                            break;
                                        case 1:
                                            pb2.setMax((int) length);
                                            pb2.setProgress((int) finalCount);
                                            tv2.setText((int) (100f * finalCount / length) + "%");
                                            break;
                                        case 2:
                                            pb3.setMax((int) length);
                                            pb3.setProgress((int) finalCount);
                                            tv3.setText((int) (100f * finalCount / length) + "%");
                                            break;
                                    }
                                }
                            });
                        }
                        inputStream.close();
                        raf.close();

                        Log.d("TAG", "线程: " + thredId + "下载完毕");
                        SharedPreferencesUtils.setParam(MainActivity.this, thredId + "", 0L);
                        SharedPreferencesUtils.setParam(MainActivity.this, thredId + "count", 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void downloadCon() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(downloadUrl);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = con.getInputStream();
                        int contentLength = con.getContentLength();
                        saveFile(inputStream, Environment.getExternalStorageDirectory() + "/con.apk", contentLength);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void downLoadRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<ResponseBody> observable = apiService.getApk();
        observable.subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        InputStream inputStream = responseBody.byteStream();
                        long contentLength = responseBody.contentLength();
                        saveFile(inputStream, Environment.getExternalStorageDirectory() + "/retrofit.apk", contentLength);
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

    private void downLoadOk() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        final Request request = new Request.Builder()
                .get()
                .url(downloadUrl)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "网络错误：" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                saveFile(inputStream, Environment.getExternalStorageDirectory() + "/123.apk", response.body().contentLength());
            }
        });
    }

    private void saveFile(InputStream inputStream, String path, final long contentLength) {
        pb1.setMax((int) contentLength);
        count = 1;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            byte[] bytes = new byte[1024 * 10];
            int lenght = -1;
            while ((lenght = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, lenght);
                count += lenght;
                Log.e("TAG", "下载进度：" + count + " / " + contentLength);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb1.setProgress(count);
                        tv1.setText((int) (100f * count / contentLength) + "%");
                    }
                });
            }
            inputStream.close();
            fileOutputStream.close();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                    InstallUtil.installApk(MainActivity.this, Environment.getExternalStorageDirectory() + "/con.apk");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //授权 可以安装apk的权限后，回调此方法，进行安装
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2019) {
            InstallUtil.installApk(this, Environment.getExternalStorageDirectory() + "/con.apk");//再次执行安装流程，包含权限判等
        }
    }
}
