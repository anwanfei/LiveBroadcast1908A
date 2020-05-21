package com.example.finalproject.presenter;

import com.example.finalproject.bean.SmartBean;
import com.example.finalproject.callback.SmartCallBack;
import com.example.finalproject.model.ImpSmartModel;
import com.example.finalproject.view.SmartView;

public class ImpSmartPresenter implements SmartPresenter, SmartCallBack {

    private SmartView smartView;
    private final ImpSmartModel model;

    public ImpSmartPresenter(SmartView smartView) {
        this.smartView = smartView;
        model = new ImpSmartModel();
    }

    @Override
    public void getSmarts(int page) {
        if (model != null) {
            model.getSmart(page, this);
        }
    }

    @Override
    public void onSuccess(SmartBean smartBean) {
        if (smartView != null) {
            smartView.onSuccess(smartBean);
        }
    }

    @Override
    public void onFail(String error) {
        if (smartView != null) {
            smartView.onFail(error);
        }
    }
}
