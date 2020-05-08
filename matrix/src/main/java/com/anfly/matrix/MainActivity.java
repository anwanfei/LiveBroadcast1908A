package com.anfly.matrix;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Bitmap baseBitmap;
    private Paint paint;
    private Button btn_scale;
    private ImageView iv;
    private Button btn_rotate;
    private Button btn_translate;
    private Button btn_skew;
    private Button btn_no_green;
    private Button btn_no_red;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        paint = new Paint();

        baseBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/Pictures/b.jpg");
        iv.setImageBitmap(baseBitmap);
    }

    /**
     * 缩放图片
     */
    protected void bitmapScale(float x, float y) {
        // 因为要将图片放大，所以要根据放大的尺寸重新创建Bitmap,Android不允许对原图进行处理
        Bitmap afterBitmap = Bitmap.createBitmap(
                (int) (baseBitmap.getWidth() * x),
                (int) (baseBitmap.getHeight() * y), baseBitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        // 初始化Matrix对象
        Matrix matrix = new Matrix();
        // 根据传入的参数设置缩放比例
        matrix.setScale(x, y);
        // 根据缩放比例，把图片draw到Canvas上
        canvas.drawBitmap(baseBitmap, matrix, paint);
        iv.setImageBitmap(afterBitmap);
    }

    /**
     * 图片旋转
     */
    protected void bitmapRotate(float degrees) {
        // 创建一个和原图一样大小的图片
        Bitmap afterBitmap = Bitmap.createBitmap(baseBitmap.getWidth(),
                baseBitmap.getHeight(), baseBitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Matrix matrix = new Matrix();
        // 根据原图的中心位置旋转
        matrix.setRotate(degrees, baseBitmap.getWidth() / 2,
                baseBitmap.getHeight() / 2);
        canvas.drawBitmap(baseBitmap, matrix, paint);
        iv.setImageBitmap(afterBitmap);
    }

    /**
     * 图片移动
     */
    protected void bitmapTranslate(float dx, float dy) {
        // 需要根据移动的距离来创建图片的拷贝图大小
        Bitmap afterBitmap = Bitmap.createBitmap(
                (int) (baseBitmap.getWidth() + dx),
                (int) (baseBitmap.getHeight() + dy), baseBitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Matrix matrix = new Matrix();
        // 设置移动的距离
        matrix.setTranslate(dx, dy);
        canvas.drawBitmap(baseBitmap, matrix, paint);
        iv.setImageBitmap(afterBitmap);
    }

    /**
     * 倾斜图片
     */
    protected void bitmapSkew(float dx, float dy) {
        // 根据图片的倾斜比例，计算变换后图片的大小，
        Bitmap afterBitmap = Bitmap.createBitmap(baseBitmap.getWidth()
                + (int) (baseBitmap.getWidth() * dx), baseBitmap.getHeight()
                + (int) (baseBitmap.getHeight() * dy), baseBitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Matrix matrix = new Matrix();
        // 设置图片倾斜的比例
        matrix.setSkew(dx, dy);
        canvas.drawBitmap(baseBitmap, matrix, paint);
        iv.setImageBitmap(afterBitmap);
    }

    public static void bitmapNoRed(Bitmap mBitmap, ImageView mIvNew) {
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), mBitmap.getConfig());
        //去掉红色
        float[] mMatrix = new float[]{
                1, 0, 0, 0, 0,//红色
                0, 0, 0, 0, 0,//绿色
                0, 0, 1, 0, 0,//蓝色
                0, 0, 0, 1, 0,//透明色
        };
        //色彩矩阵
        ColorMatrix colorMatrix = new ColorMatrix(mMatrix);
        //画板
        Canvas canvas = new Canvas(bitmap);
        //画笔
        Paint paint = new Paint();
        //给画笔设置颜色过滤器,里面使用色彩矩阵
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        //将mBitmap临摹到bitmap上,使用含有色彩矩阵的画笔
        canvas.drawBitmap(mBitmap, 0, 0, paint);
        mIvNew.setImageBitmap(bitmap);
    }


    private void initView() {
        btn_scale = (Button) findViewById(R.id.btn_scale);
        iv = (ImageView) findViewById(R.id.iv);

        btn_scale.setOnClickListener(this);
        btn_rotate = (Button) findViewById(R.id.btn_rotate);
        btn_rotate.setOnClickListener(this);
        btn_translate = (Button) findViewById(R.id.btn_translate);
        btn_translate.setOnClickListener(this);
        btn_skew = (Button) findViewById(R.id.btn_skew);
        btn_skew.setOnClickListener(this);
        btn_no_green = (Button) findViewById(R.id.btn_no_green);
        btn_no_green.setOnClickListener(this);
        btn_no_red = (Button) findViewById(R.id.btn_no_red);
        btn_no_red.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scale:
                bitmapScale(0.2f, 0.3f);
                break;
            case R.id.btn_rotate:
                bitmapRotate(30f);
                break;
            case R.id.btn_translate:
                bitmapTranslate(200f, 300f);
                break;
            case R.id.btn_skew:
                bitmapSkew(1f, 5f);
                break;
            case R.id.btn_no_green:
                bitmapNoRed(baseBitmap, iv);
                break;
            case R.id.btn_no_red:
                bitmapNoRed(baseBitmap, iv);
                break;
        }
    }
}
