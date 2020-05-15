package com.anfly.lrucachedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xts on 2018/10/19.
 */

public class MyAdatper extends RecyclerView.Adapter {

    private static final String TAG = "MyAdatper";
    private  Context mContext;
    private List<FoodBean.DataBean> mData;
    private final OkHttpClient mClient;
    private final ImgCacheUtil mImgCache;
    private final ImgDiskCacheUtil mImgDiskCache;

    public MyAdatper(Context context, List<FoodBean.DataBean> data) {
        this.mData = data;
        this.mContext = context;
        mClient = new OkHttpClient();
        mImgCache = new ImgCacheUtil();
        mImgDiskCache = new ImgDiskCacheUtil(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null, false);

        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder holder1 = (MyViewHolder) holder;
        FoodBean.DataBean dataBean = mData.get(position);
        String url = dataBean.getPic();
        String title = dataBean.getTitle();
        holder1.mTv.setText(title);
        holder1.mIv.setTag(url);
        holder1.mIv.setImageResource(R.mipmap.ic_launcher);
        //Glide.with(mContext).load(url).placeholder(R.mipmap.ic_launcher).into(holder1.mIv);
        if (mImgCache.getBitmapFromCache(url) != null){//判断内存有没有对应的数据
            holder1.mIv.setImageBitmap(mImgCache.getBitmapFromCache(url));//有    加载内存数据
        }else if (mImgDiskCache.getBitmap(url) != null){//判断硬盘有没有对应的数据
            holder1.mIv.setImageBitmap(mImgDiskCache.getBitmap(url));  //有   加载硬盘数据
            mImgCache.addBitmap2Cache(url,mImgDiskCache.getBitmap(url));//同时  存入内存一份   以防下次直接加载内存数据
        }else {//加载网络数据
            ImageTask imageTask = new ImageTask(holder1.mIv);
            imageTask.execute(url);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<FoodBean.DataBean> obj) {
        this.mData.addAll(obj);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.mData.clear();
    }

    public void clearCache() {
        if (mImgCache != null){
            mImgCache.clearAll();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView mIv;
        private TextView mTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            mIv = itemView.findViewById(R.id.iv);
            mTv = itemView.findViewById(R.id.tv);
        }
    }

    class ImageTask extends AsyncTask<String, Object, Bitmap> {

        private ImageView mIv;
        private String mImgUrl;

        public ImageTask(ImageView iv) {
            this.mIv = iv;
        }


        @Override
        protected void onPreExecute() {
            //主线程中,在做耗时操作之前做一些初始化的工作
        }

        /**
         *
         * @param strings 就是上面execute(mData.get(position));传进来的
         * @return
         */
        @Override
        protected Bitmap doInBackground(String... strings) {
            //子线程,做耗时操作
            //没下载百分之几,可以把下载的进度发到onProgressUpdate
            //publishProgress();

            mImgUrl = strings[0];
            //下载图片
            Bitmap bitmap = downImg(mImgUrl);
            return bitmap;
        }

        /**
         * 下载图片
         * @param imgUrl
         * @return
         */
        private Bitmap downImg(String imgUrl) {
            try {

                Request request = new Request.Builder()
                        .url(mImgUrl)
                        .build();
                Call call = mClient.newCall(request);
                Response execute = call.execute();
                InputStream inputStream = execute.peekBody(1024*1024).byteStream();
                //BitmapFactory可以将一些inputStream/文件路径..变成一个bitmap
                InputStream is2 = execute.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                mImgDiskCache.writeInputStream2Sd(mImgUrl,is2);  //保存到硬盘一份
                mImgCache.addBitmap2Cache(mImgUrl,bitmap);//保存到内存一份
                Log.d(TAG, "网络下载图片: "+imgUrl);
                //将图片写到本地的sd卡中
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            //更新阶段耗时操作结果

        }

        @Override
        protected void onPostExecute(Bitmap o) {
            //主线程中,耗时操作已经完成,结果已经返回了 类似于hander 的handlerMessage()
            if (mIv != null&& o != null && mImgUrl.equals(mIv.getTag())){
                mIv.setImageBitmap(o);
            }
        }
    }
}
