package com.anfly.collectionmvp.view;

import com.anfly.collectionmvp.bean.FoodBean;

public interface HomeView {
    void onSuccess(FoodBean foodBean);

    void onInsertSuccess(String msg);

    void onFail(String error);

    void onInsertFail(String error);
}
