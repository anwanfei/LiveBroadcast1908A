package com.anfly.exercise11.presenter;

import com.anfly.exercise11.bean.FoodBean;
import com.anfly.exercise11.callback.HomeCallback;
import com.anfly.exercise11.model.ImpHomeModel;
import com.anfly.exercise11.view.HomeView;

public class ImpHomePresenter implements HomePresenter, HomeCallback {
    private HomeView homeView;
    private final ImpHomeModel model;

    public ImpHomePresenter(HomeView homeView) {
        this.homeView = homeView;
        model = new ImpHomeModel();
    }

    @Override
    public void getFoodData() {
        if (model != null) {
            model.getFoodData(this);
        }
    }

    @Override
    public void onSuccess(FoodBean foodBean) {
        if (homeView != null) {
            homeView.onSuccess(foodBean);
        }
    }

    @Override
    public void onFail(String error) {
        if (homeView != null) {
            homeView.onFail(error);
        }
    }
}
