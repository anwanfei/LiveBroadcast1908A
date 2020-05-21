package com.example.finalproject.presenter;

import com.example.finalproject.callback.HomeCallBack;
import com.example.finalproject.bean.FoodBean;
import com.example.finalproject.model.ImpHomeModel;
import com.example.finalproject.view.HomeView;

public class ImpHomePrensenter implements HomePresenter, HomeCallBack {

    private ImpHomeModel homeModel;
    private HomeView homeView;

    public ImpHomePrensenter(HomeView homeView) {
        this.homeView = homeView;
        homeModel = new ImpHomeModel();
    }

    @Override
    public void getHomeList() {
        homeModel.getHomeList(this);
    }

    @Override
    public void onSuccess(FoodBean foodBean) {
        homeView.onSuccess(foodBean);
    }

    @Override
    public void onFail(String error) {
        homeView.onFail(error);
    }
}
