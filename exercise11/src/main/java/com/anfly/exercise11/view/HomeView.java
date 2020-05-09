package com.anfly.exercise11.view;

import com.anfly.exercise11.bean.FoodBean;

public interface HomeView {
    void onSuccess(FoodBean foodBean);

    void onFail(String error);
}
