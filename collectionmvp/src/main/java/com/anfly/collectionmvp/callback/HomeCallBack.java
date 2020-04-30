package com.anfly.collectionmvp.callback;

import com.anfly.collectionmvp.bean.FoodBean;

public interface HomeCallBack {
    void onSuccess(FoodBean foodBean);

    void onInsertSuccess(String msg);

    void onFail(String error);

    void onInsertFail(String error);
}
