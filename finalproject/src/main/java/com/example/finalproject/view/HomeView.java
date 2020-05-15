package com.example.finalproject.view;

import com.example.finalproject.bean.FoodBean;

public interface HomeView {
    void onSuccess(FoodBean foodBean);

    void onFail(String error);
}
