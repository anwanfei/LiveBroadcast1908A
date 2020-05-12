package com.example.upload;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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

    private static final int CAMERA_CODE = 100;
    private Button btn_upload_ok;
    private File file = new File(Environment.getExternalStorageDirectory() + "/Pictures/b.jpg");
    private ImageView iv;
    private Button btn_upload_retrofit;
    private Button btn_upload_con;
    private Button btn_upload_camera;
    private Button btn_upload_album;
    private File cameraFile;
    private Uri imageUri;

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
        btn_upload_con = (Button) findViewById(R.id.btn_upload_con);
        btn_upload_con.setOnClickListener(this);
        btn_upload_camera = (Button) findViewById(R.id.btn_upload_camera);
        btn_upload_camera.setOnClickListener(this);
        btn_upload_album = (Button) findViewById(R.id.btn_upload_album);
        btn_upload_album.setOnClickListener(this);

        addPermission();
    }

    private void addPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upload_ok:
                uploadOk(file);
                break;
            case R.id.btn_upload_retrofit:
                uploadRetrofit();
                break;
            case R.id.btn_upload_con:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("key", "1908A");
                            uploadForm(map, "file", file, file.getName(), "http://yun918.cn/study/public/file_upload.php");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;
            case R.id.btn_upload_camera:
                chepermission();
                break;
            case R.id.btn_upload_album:
                opALbum();
                break;
        }
    }

    private void opALbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 300);
    }

    private void chepermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        }
    }

    private void openCamera() {

        cameraFile = new File(getCacheDir(), System.currentTimeMillis() + ".jpg");
        if (!cameraFile.exists()) {
            try {
                cameraFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //兼容7.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            imageUri = Uri.fromFile(cameraFile);
        } else {
            imageUri = FileProvider.getUriForFile(this, "com.example.upload.fileprivider", cameraFile);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            uploadOk(cameraFile);
        } else if (requestCode == 300) {
            Uri uri = data.getData();
            File file = getFileFromUri(uri);
            if (file.exists()) {
                uploadOk(file);
            }
        }

    }

    private File getFileFromUri(Uri uri) {
        if (uri == null) {
            return null;
        }

        // 处理uri,7.0以后的fileProvider 把URI 以content provider 方式 对外提供的解析方法
        switch (uri.getScheme()) {
            case "content"://7.0及以后
                return getFileFromContentUri(uri);
            case "file"://7.0以前
                return new File(uri.getPath());
            default:
                return null;
        }
    }

    private File getFileFromContentUri(Uri uri) {
        File file = null;
        String filePath = null;
        ContentResolver resolver = getContentResolver();
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        Cursor cursor = resolver.query(uri, filePathColumn, null, null, null);

        if (cursor.moveToNext()) {
            filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
        }

        cursor.close();
        if (!TextUtils.isEmpty(filePath)) {
            file = new File(filePath);
        }
        return file;
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
    private void uploadOk(File file) {
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

    /**
     * HttpUrlConnection　实现文件上传
     *
     * @param params       普通参数
     * @param fileFormName 文件在表单中的键
     * @param uploadFile   上传的文件
     * @param newFileName  文件在表单中的值（服务端获取到的文件名）
     * @param urlStr       url
     * @throws IOException
     */
    public void uploadForm(Map<String, String> params, String fileFormName, File uploadFile, String newFileName, String urlStr) throws IOException {

        if (newFileName == null || newFileName.trim().equals("")) {
            newFileName = uploadFile.getName();
        }
        StringBuilder sb = new StringBuilder();
        /**
         * 普通的表单数据
         */
        if (params != null) {
            for (String key : params.keySet()) {
                sb.append("--" + BOUNDARY + "\r\n");
                sb.append("Content-Disposition: form-data; name=\"" + key + "\"" + "\r\n");
                sb.append("\r\n");
                sb.append(params.get(key) + "\r\n");
            }
        }

        /**
         * 上传文件的头
         */
        sb.append("--" + BOUNDARY + "\r\n");
        sb.append("Content-Disposition: form-data; name=\"" + fileFormName + "\"; filename=\"" + newFileName + "\""
                + "\r\n");
        sb.append("Content-Type: image/jpg" + "\r\n");// 如果服务器端有文件类型的校验，必须明确指定ContentType
        sb.append("\r\n");

        byte[] headerInfo = sb.toString().getBytes("UTF-8");
        byte[] endInfo = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");


        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        // 设置传输内容的格式，以及长度
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        conn.setRequestProperty("Content-Length",
                String.valueOf(headerInfo.length + uploadFile.length() + endInfo.length));
        conn.setDoOutput(true);

        OutputStream out = conn.getOutputStream();
        InputStream in = new FileInputStream(uploadFile);

        //写入的文件长度
        int count = 0;
        //文件的总长度
        int available = in.available();
        // 写入头部 （包含了普通的参数，以及文件的标示等）
        out.write(headerInfo);
        // 写入文件
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
            count += len;
            int progress = count * 100 / available;
        }
        // 写入尾部
        out.write(endInfo);
        in.close();
        out.close();
        if (conn.getResponseCode() == 200) {
            System.out.println("文件上传成功");
            String s = stream2String(conn.getInputStream());
            Log.e("TAG", "MainActivity uploadForm()" + s);

        }
    }

    // 分割符,自己定义即可
    private static final String BOUNDARY = "----WebKitFormBoundaryT1HoybnYeFOGFlBR";

    public String stream2String(InputStream is) {
        int len;
        byte[] bytes = new byte[1024];
        StringBuffer sb = new StringBuffer();
        try {
            while ((len = is.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len));
            }

            is.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
