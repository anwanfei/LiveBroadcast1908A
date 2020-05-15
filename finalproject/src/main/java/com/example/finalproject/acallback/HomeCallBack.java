package com.example.finalproject.acallback;

import com.example.finalproject.bean.FoodBean;

public interface HomeCallBack {
    void onSuccess(FoodBean foodBean);

    void onFail(String error);
}
