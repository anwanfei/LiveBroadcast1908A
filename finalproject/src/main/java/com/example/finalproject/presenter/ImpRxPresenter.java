package com.example.finalproject.presenter;

import com.example.finalproject.bean.ComPoseBean;
import com.example.finalproject.callback.RxCallBack;
import com.example.finalproject.model.ImpRxModel;
import com.example.finalproject.view.RxView;

public class ImpRxPresenter implements RxPresenter, RxCallBack {
    private ImpRxModel model;

    private RxView rxView;

    public ImpRxPresenter(RxView rxView) {
        this.rxView = rxView;
        model = new ImpRxModel();
    }

    @Override
    public void getRxData() {
        if (model != null) {
            model.getRxData(this);
        }
    }

    @Override
    public void onSuccess(ComPoseBean comPoseBean) {
        if (rxView != null) {
            rxView.onSuccess(comPoseBean);
        }
    }

    @Override
    public void onFail(String error) {
        if (rxView != null) {
            rxView.onFail(error);
        }
    }
}
