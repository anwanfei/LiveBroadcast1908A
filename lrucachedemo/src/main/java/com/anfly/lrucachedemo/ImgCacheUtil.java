package com.anfly.lrucachedemo;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by xts on 2018/10/22.
 * 内存缓存工具类  LruCache<String, Bitmap>   --   LinkedHashMap<K, V>   --    内存空间
 */

public class ImgCacheUtil {

    private static final String TAG = "ImgCacheUtil";
    private LruCache<String, Bitmap> mCache;

    public ImgCacheUtil(){
        //系统分配个app最大的内存
        long max = Runtime.getRuntime().maxMemory();
        int cacheSize = (int)(max/8);
        //图片缓存的最大值
        //返回某张图片的大小
        mCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                //返回某张图片的大小
                return bitmap.getByteCount();
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
                //达到预定的最大内存后走这个方法
                Log.d(TAG, "移除内存中图片: "+key);
            }
        };
    }

    //应该有,存储图片的方法,移除,获取,清空所有图片
    public void clearAll(){
        if (mCache != null){
            if (mCache.size()>0){
                //清除所有缓存
                Log.d(TAG, "clearAll: ");
                mCache.evictAll();
            }
        }
    }

    public void remove(String key){
        if (mCache != null && key != null){
            mCache.remove(key);
            Log.d(TAG, "remove: ");
        }
    }

    public Bitmap getBitmapFromCache(String key){
        if (mCache != null && key != null){
            Bitmap bitmap = mCache.get(key);
            Log.d(TAG, "从内存中获取图片: "+bitmap+","+key);
            return bitmap;
        }
        return null;
    }

    public void addBitmap2Cache(String key,Bitmap bitmap){
        if (mCache != null && key != null && bitmap != null){
            mCache.put(key,bitmap);
            Log.d(TAG, "缓存到内存中: "+key);
        }
    }
}
