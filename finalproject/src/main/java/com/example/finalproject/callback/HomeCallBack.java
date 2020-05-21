package com.example.finalproject.callback;

import com.example.finalproject.bean.FoodBean;

public interface HomeCallBack {
    void onSuccess(FoodBean foodBean);

    void onFail(String error);
}
