package com.anfly.insamplesize;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn;
    private ImageView iv;
    String pathName = Environment.getExternalStorageDirectory() + "/Pictures/b.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn = (Button) findViewById(R.id.btn);
        iv = (ImageView) findViewById(R.id.iv);

        btn.setOnClickListener(this);
        Glide.with(this).load(pathName).into(iv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                inSampleSize();
                break;
        }
    }

    private void inSampleSize() {

        //width: 263,ImageView实际比较小
        int width = iv.getWidth();
        int height = iv.getHeight();
        Log.d("TAG", "imageView width: " + width);

        //不采样时图片的大小:9216000
        Bitmap bitmap = BitmapFactory.decodeFile(pathName);
        Log.d("TAG", "不二次采样bitmap大小: " + bitmap.getAllocationByteCount());

        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true,加载图片时不会获取到bitmap对象,但是可以拿到图片的宽高
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        //计算采样率,对图片进行相应的缩放
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        Log.d("TAG", "outWidth: " + outWidth + ",outHeight:" + outHeight);

        float widthRatio = outWidth * 1.0f / width;
        float heightRatio = outHeight * 1.0f / height;

        Log.d("TAG", "widthRatio: " + widthRatio + ",heightRatio:" + heightRatio);

        float max = Math.max(widthRatio, heightRatio);
        //向上舍入
        int inSampleSize = (int) Math.ceil(max);

        Log.d("TAG", "inSampleSize: " + inSampleSize);
        //改为false,因为要获取采样后的图片了
        options.inJustDecodeBounds = false;
        //8
        options.inSampleSize = inSampleSize;

        Bitmap bitmap1 = BitmapFactory.decodeFile(pathName, options);
        //采样后图片大小:144000,是采样前图片的inSampleSize*inSampleSize分之一(1/64)
        Log.d("TAG", "二次采样bitmap大小: " + bitmap1.getAllocationByteCount());
        iv.setImageBitmap(bitmap1);

    }
}
