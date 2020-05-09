package com.anfly.exercise11.callback;

import com.anfly.exercise11.bean.FoodBean;

public interface HomeCallback {
    void onSuccess(FoodBean foodBean);

    void onFail(String error);
}
