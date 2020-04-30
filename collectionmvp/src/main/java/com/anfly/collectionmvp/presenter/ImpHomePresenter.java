package com.anfly.collectionmvp.presenter;

import com.anfly.collectionmvp.bean.ConllectionDbBean;
import com.anfly.collectionmvp.bean.FoodBean;
import com.anfly.collectionmvp.callback.HomeCallBack;
import com.anfly.collectionmvp.model.ImpHomeModel;
import com.anfly.collectionmvp.view.HomeView;

public class ImpHomePresenter implements HomePresenter, HomeCallBack {

    private HomeView homeView;
    private ImpHomeModel homeModel;

    public ImpHomePresenter(HomeView homeView) {
        this.homeView = homeView;
        homeModel = new ImpHomeModel();
    }

    @Override
    public void homeData() {
        homeModel.homeData(this);
    }

    @Override
    public void insert(ConllectionDbBean conllectionDbBean) {
        homeModel.insert(conllectionDbBean, this);
    }

    @Override
    public void onSuccess(FoodBean foodBean) {
        homeView.onSuccess(foodBean);
    }

    @Override
    public void onInsertSuccess(String msg) {
        homeView.onInsertSuccess(msg);
    }

    @Override
    public void onFail(String error) {
        homeView.onFail(error);
    }

    @Override
    public void onInsertFail(String error) {
        homeView.onInsertFail(error);
    }
}
